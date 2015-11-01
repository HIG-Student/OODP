package se.hig.oodp.b9.logic.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.communication.PServerInfo;
import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.Rules;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

/**
 * Server-side game
 */
public class ServerGame
{
    /**
     * The server's networker
     */
    private ServerNetworker networker;

    /**
     * The player in this game
     */
    private List<Player> players = new ArrayList<Player>();

    /**
     * Mapping for all players' points (numerical)
     */
    private HashMap<Player, Integer> points = new HashMap<Player, Integer>();

    /**
     * Mapping for all players' total points (numerical)
     */
    private HashMap<Player, Integer> totalPoints = new HashMap<Player, Integer>();

    /**
     * The table
     */
    private Table table;

    /**
     * Get the table
     * 
     * @return the table
     */
    public Table getTable()
    {
        return table;
    }

    /**
     * The rules
     */
    private Rules rules;

    /**
     * Are we running?
     */
    private boolean running = false;

    /**
     * Event invoked on player added
     */
    public final Event<Player> playerAdded = new Event<Player>();

    /**
     * Get player's points
     * 
     * @param player
     *            the player
     * @return the points
     */
    public int getPoints(Player player)
    {
        return points.containsKey(player) ? points.get(player) : 0;
    }

    /**
     * Get player's total points
     * 
     * @param player
     *            the player
     * @return the points
     */
    public int getTotalPoints(Player player)
    {
        return totalPoints.containsKey(player) ? totalPoints.get(player) : 0;
    }

    /**
     * Add points to player
     * 
     * @param player
     *            the player
     * @param toAdd
     *            points to add
     */
    public void addPoints(Player player, int toAdd)
    {
        totalPoints.put(player, getTotalPoints(player) + toAdd);
        points.put(player, getPoints(player) + toAdd);
    }

    /**
     * Create server game
     * 
     * @param networker
     *            communication handler
     * @param rules
     *            the rules to follow
     */
    public ServerGame(ServerNetworker networker, Rules rules) // rules?
    {
        this.networker = networker;
        this.rules = rules;

        setUp();
    }

    /**
     * Sets up the stuff
     */
    protected void setUp()
    {
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
            if (rules.canPlayMove(two.getOne(), table, two.getTwo()))
            {
                UUID activeCard = two.getTwo().getActiveCard();
                UUID[][] takeCards = two.getTwo().getTakeCards();

                if (takeCards.length == 0)
                {
                    moveCard(activeCard, table.poolUUID);
                }
                else
                {
                    for (UUID[] cardList : takeCards)
                        for (UUID card : cardList)
                        {
                            moveCard(card, two.getOne().pointsUUID);

                            if (table.getCardInfo(card).getValue() == CardInfo.Value.Ess)
                                addPoints(two.getOne(), 1);

                            if (table.getCardInfo(card).getValue() == CardInfo.Value.Två && table.getCardInfo(card).getType() == CardInfo.Type.Spader)
                                addPoints(two.getOne(), 1);

                            if (table.getCardInfo(card).getValue() == CardInfo.Value.Tio && table.getCardInfo(card).getType() == CardInfo.Type.Ruter)
                                addPoints(two.getOne(), 2);
                        }

                    if (table.getPoolIds().length == 0)
                        addPoints(two.getOne(), 1);

                    moveCard(activeCard, two.getOne().pointsUUID);

                    if (table.getCardInfo(activeCard).getValue() == CardInfo.Value.Ess)
                        addPoints(two.getOne(), 1);

                    if (table.getCardInfo(activeCard).getValue() == CardInfo.Value.Två && table.getCardInfo(activeCard).getType() == CardInfo.Type.Spader)
                        addPoints(two.getOne(), 1);

                    if (table.getCardInfo(activeCard).getValue() == CardInfo.Value.Tio && table.getCardInfo(activeCard).getType() == CardInfo.Type.Ruter)
                        addPoints(two.getOne(), 2);
                }

                networker.sendMoveResult(two.getOne(), true);

                table.nextTurn();

                System.out.println("Next turn");
                System.out.println("\t" + table.getNextPlayer());

                if (table.getPlayerHandIds(table.getNextPlayer()).length == 0)
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
                            if (table.getPlayerPointIds(player).length > topCardCount)
                            {
                                topCardCount = table.getPlayerPointIds(player).length;
                                topCards.clear();
                                topCards.add(player);
                            }
                            else
                                if (table.getPlayerPointIds(player).length == topCardCount)
                                    topCards.add(player);

                        for (Player player : topCards)
                            addPoints(player, 1);

                        // Most spades taken
                        List<Player> topSpades = new ArrayList<Player>();
                        int topSpadeCount = 0;

                        for (Player player : players)
                            if (table.getPlayerPointIds(player).length > topSpadeCount)
                            {
                                topSpadeCount = table.getPlayerPointIds(player).length;
                                topSpades.clear();
                                topSpades.add(player);
                            }
                            else
                                if (table.getPlayerPointIds(player).length == topSpadeCount)
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
     * Start a new game with the old rules
     */
    public void newGame()
    {
        newGame(null);
    }

    /**
     * Start a new game with new rules
     * 
     * @param newRules
     *            the rules to follow
     */
    public void newGame(Rules newRules)
    {
        points.clear();

        if (newRules != null)
            rules = newRules;

        table = new Table(players, UUID.randomUUID(), UUID.randomUUID());

        rules.setUp(table);

        networker.sendTable(table);

        table.onCardMove.add(two ->
        {
            networker.sendMoveCard(two.getOne(), two.getTwo());

            if (table.getOwnerOfCard(two.getOne()) != null)
                networker.sendCardInfo(table.getOwnerOfCard(two.getOne()), two.getOne(), table.getCardInfo(two.getOne()));

            if (two.getTwo() == table.poolUUID)
                for (Player player : players)
                    networker.sendCardInfo(player, two.getOne(), table.getCardInfo(two.getOne()));
        });

        for (Player player : players)
            for (UUID card : table.getPlayerHandIds(player))
                networker.sendCardInfo(player, card, table.getCardInfo(card));

        for (Player player : players)
            for (UUID card : table.getCardCollection(table.poolUUID))
                networker.sendCardInfo(player, card, table.getCardInfo(card));

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
     * @param cardId
     *            the card to move
     * @param collectionId
     *            the destination
     */
    public void moveCard(UUID cardId, UUID collectionId)
    {
        table.moveCard(cardId, collectionId);
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
        System.out.println("Server kill invoked!");
        networker.kill();
    }
}
