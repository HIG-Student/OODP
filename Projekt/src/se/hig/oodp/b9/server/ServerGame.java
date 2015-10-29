package se.hig.oodp.b9.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PServerInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Rules;
import se.hig.oodp.b9.Table;

/**
 * Server-side game
 */
public class ServerGame
{
    /**
     * The server's networker
     */
    public ServerNetworker networker;

    /**
     * The player in this game
     */
    public List<Player> players = new ArrayList<Player>();

    /**
     * Mapping for all players' points (numerical)
     */
    public HashMap<Player, Integer> points = new HashMap<Player, Integer>();

    /**
     * Mapping for all players' total points (numerical)
     */
    public HashMap<Player, Integer> totalPoints = new HashMap<Player, Integer>();

    /**
     * The deck of cards
     */
    public CardDeck cardDeck;
    /**
     * The table
     */
    public Table table;
    /**
     * The rules
     */
    public Rules rules;

    /**
     * Are we running?
     */
    public boolean running = false;

    /**
     * Event invoked on player added
     */
    public Event<Player> playerAdded = new Event<Player>();

    /**
     * Get player's points
     * 
     * @param playerthe
     *            player
     * @return the points
     */
    public int getPoints(Player player)
    {
        return points.containsKey(player) ? points.get(player) : 0;
    }

    /**
     * Add points to player
     * 
     * @param playerthe
     *            player
     * @param toAdd
     *            points to add
     */
    public void addPoints(Player player, int toAdd)
    {
        totalPoints.put(player, getPoints(player) + toAdd);
        points.put(player, getPoints(player) + toAdd);
    }

    /**
     * Create server game
     * 
     * @param networker
     *            communication handler
     */
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
                Card[][] takeCards = two.getTwo().getTakeCards();

                if (takeCards.length == 0)
                {
                    moveCard(activeCard, table.getPool());
                }
                else
                {
                    for (Card[] cardList : takeCards)
                        for (Card card : cardList)
                        {
                            moveCard(card, table.getPlayerPoints(two.getOne()));

                            if (card.getCardInfo().getValue() == CardInfo.Value.Ess)
                                addPoints(two.getOne(), 1);

                            if (card.getCardInfo().getValue() == CardInfo.Value.Två && card.getCardInfo().getType() == CardInfo.Type.Spader)
                                addPoints(two.getOne(), 1);

                            if (card.getCardInfo().getValue() == CardInfo.Value.Tio && card.getCardInfo().getType() == CardInfo.Type.Ruter)
                                addPoints(two.getOne(), 2);
                        }

                    if (table.getPool().size() == 0)
                        addPoints(two.getOne(), 1);

                    moveCard(activeCard, table.getPlayerPoints(two.getOne()));

                    if (activeCard.getCardInfo().getValue() == CardInfo.Value.Ess)
                        addPoints(two.getOne(), 1);

                    if (activeCard.getCardInfo().getValue() == CardInfo.Value.Två && activeCard.getCardInfo().getType() == CardInfo.Type.Spader)
                        addPoints(two.getOne(), 1);

                    if (activeCard.getCardInfo().getValue() == CardInfo.Value.Tio && activeCard.getCardInfo().getType() == CardInfo.Type.Ruter)
                        addPoints(two.getOne(), 2);
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

                        // Most card taken
                        List<Player> topCards = new ArrayList<Player>();
                        int topCardCount = 0;

                        for (Player player : players)
                            if (table.getPlayerPoints(player).size() > topCardCount)
                            {
                                topCardCount = table.getPlayerPoints(player).size();
                                topCards.clear();
                                topCards.add(player);
                            }
                            else
                                if (table.getPlayerPoints(player).size() == topCardCount)
                                    topCards.add(player);

                        for (Player player : topCards)
                            addPoints(player, 1);

                        // Most spades taken
                        List<Player> topSpades = new ArrayList<Player>();
                        int topSpadeCount = 0;

                        for (Player player : players)
                            if (table.getPlayerPoints(player).size() > topSpadeCount)
                            {
                                topSpadeCount = table.getPlayerPoints(player).size();
                                topSpades.clear();
                                topSpades.add(player);
                            }
                            else
                                if (table.getPlayerPoints(player).size() == topSpadeCount)
                                    topSpades.add(player);

                        for (Player player : topSpades)
                            addPoints(player, 1);

                        if (topCards.size() == 1 && topSpades.size() == 1 && topCards.get(0).equals(topSpades.get(0)))
                            addPoints(topCards.get(0), 1);

                        networker.sendEndGame(points, totalPoints);
                        newGame();
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

    /**
     * Get communication handler
     * 
     * @return the communication handler
     */
    public ServerNetworker getNetworker()
    {
        return networker;
    }

    /**
     * Start a new game
     */
    public void newGame()
    {
        cardDeck = new CardDeck();

        points.clear();

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
            networker.sendTable(table);

            rules.setUp(table);
        }

        table.resetNextTurn();
        networker.sendPlayerTurn(table.getNextPlayer());
    }

    /**
     * Get players in this game
     * 
     * @return the players
     */
    public Player[] getPlayers()
    {
        return players.toArray(new Player[0]);
    }

    /**
     * Move a card to a new destination
     * 
     * @param card
     *            the card to move
     * @param collection
     *            the destination
     */
    public void moveCard(Card card, CardCollection collection)
    {
        table.moveCard(card, collection);
    }

    /**
     * Start a new game
     */
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

    /**
     * KIll this game
     */
    public void kill()
    {
        networker.kill();
    }
}
