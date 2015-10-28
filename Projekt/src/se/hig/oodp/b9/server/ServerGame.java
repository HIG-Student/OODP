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
            System.out.println("Server: Player [" + message.one + "] says: " + message.two);
            networker.sendMessageToAll(message.one, message.two);
        });

        networker.onNewMove.add(two ->
        {
            two.two.makeLocal(table);
            
            if (rules.canPlayMove(two.one, table, two.two))
            {
                if (two.two.takeCards.size() == 0)
                    moveCard(toLocal(two.two.activeCard), table.pool);
                else
                    for (Card card : two.two.takeCards)
                        moveCard(toLocal(card), table.playerPoints.get(two.one));

                networker.sendMoveResult(two.one, true);
                
                table.nextTurn();
                networker.sendGetMove(table.getNextPlayer());
            }
            else
            {
                networker.sendMoveResult(two.one, false);
            }
        });
    }

    public Card toLocal(Card card)
    {
        return table.cards.get(card.getId());
    }

    public void newGame()
    {
        cardDeck = new CardDeck();

        if (table == null)
        {
            table = new Table(players, UUID.randomUUID(), UUID.randomUUID());
        }

        table.changeDeck(cardDeck.getCards());
        rules.setUp(table);

        networker.sendTable(table);

        for (Player player : players)
            for (Card card : table.playerHands.get(player).getAll())
                networker.sendCardInfo(player, card);

        for (Player player : players)
            for (Card card : table.pool.getAll())
                networker.sendCardInfo(player, card);

        networker.sendGetMove(table.getNextPlayer());
    }

    public void moveCard(Card card, CardCollection collection)
    {
        table.moveCard(card, collection);
        networker.sendMoveCard(card, collection);

        if (collection.owner != null)
            networker.sendCardInfo(collection.owner, card);

        if (collection == table.pool)
            for (Player player : players)
                networker.sendCardInfo(player, card);
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
}
