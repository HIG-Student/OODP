package se.hig.oodp.b9.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PServerInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Rules;
import se.hig.oodp.b9.Table;

public class ServerGame
{
    public ServerNetworker networker;

    public List<Player> players = new ArrayList<Player>();

    public CardDeck cardDeck;
    public Table table;
    public Rules rules;

    public boolean running = false;

    public Event<Player> playerAdded = new Event<Player>();

    public ServerGame(ServerNetworker networker) // rules?
    {
        this.networker = networker;

        networker.onPlayerConnecting.add(newPlayer ->
        {
            if (running)
            {
                networker.closeConnection(newPlayer, "The game have already begun!");
                return;
            }

            if (players.size() >= 4)
            {
                networker.closeConnection(newPlayer, "Max players reached!");
                return;
            }

            for (Player exsistingPlayer : players)
                if (newPlayer.getName().toLowerCase().equals(exsistingPlayer.getName().toLowerCase()))
                {
                    networker.closeConnection(newPlayer, "Name taken!");
                    return;
                }

            players.add(newPlayer);

            for (Player player : players)
                if (player != newPlayer)
                    networker.sendMessageTo(player, "Player [" + newPlayer.getName() + "] have joined the game!");

            networker.sendMessageTo(newPlayer, "You have joined a game!");
            String playersInTheGameStr = "Players in the game:";
            for (Player player : players)
                if (player != newPlayer)
                    playersInTheGameStr += "\n\t" + player.getName();
            playersInTheGameStr += "\n\t" + newPlayer + " (you)";
            networker.sendMessageTo(newPlayer, playersInTheGameStr);

            networker.sendGreeting(newPlayer, new PServerInfo());

            playerAdded.invoke(newPlayer);
        });

        networker.onNewMessage.add(message ->
        {
            System.out.println("Server: Player [" + message.getOne() + "] says: " + message.getTwo());
            networker.sendMessageToAll(message.getOne(), message.getTwo());
        });

        networker.onNewMove.add(two ->
        {
            two.getTwo().populate(table);

            if (rules.canPlayMove(two.getOne(), table, two.getTwo()))
            {
                Card activeCard = two.getTwo().getActiveCard();
                Card[] takeCards = two.getTwo().getTakeCards();

                if (takeCards.length == 0)
                    moveCard(activeCard, table.getPool());
                else
                {
                    for (Card card : takeCards)
                        moveCard(card, table.getPlayerPoints(two.getOne()));

                    moveCard(activeCard, table.getPlayerPoints(two.getOne()));
                }

                networker.sendMoveResult(two.getOne(), true);

                table.nextTurn();

                System.out.println("Next turn");
                System.out.println("\t" + table.getNextPlayer());
                System.out.println("\t" + table.getPlayerHand(table.getNextPlayer()).size());

                if (table.getPlayerHand(table.getNextPlayer()).size() == 0)
                {
                    System.out.println("OK!!!!" + table.deckGotCards());
                    if (table.deckGotCards())
                    {
                        rules.give(table);
                    }
                    else
                    {
                        // END GAME
                    }
                }

                networker.sendPlayerTurn(table.getNextPlayer());
            }
            else
            {
                networker.sendMoveResult(two.getOne(), false);
            }
        });
    }

    public void newGame()
    {
        cardDeck = new CardDeck();

        if (table == null)
        {
            table = new Table(players, UUID.randomUUID(), UUID.randomUUID());

            table.changeDeck(cardDeck.getCards());
            
            networker.sendTable(table);
            
            table.onCardMove.add(two ->
            {
                networker.sendMoveCard(two.getOne(), two.getTwo());

                if (two.getTwo().owner != null)
                    networker.sendCardInfo(two.getTwo().owner, two.getOne());

                if (two.getTwo() == table.getPool())
                    for (Player player : players)
                        networker.sendCardInfo(player, two.getOne());
            });
            
            rules.setUp(table);
        }
        else
        {
            table.changeDeck(cardDeck.getCards());
            rules.setUp(table);
        }

        networker.sendPlayerTurn(table.getNextPlayer());
    }

    public void moveCard(Card card, CardCollection collection)
    {
        table.moveCard(card, collection);
    }

    public void startGame()
    {
        if (running)
        {
            // clear?
        }
        running = true;

        networker.sendMessageToAll("The game is starting!");
        newGame();
    }
    
    public void kill()
    {
        networker.kill();
    }
}
