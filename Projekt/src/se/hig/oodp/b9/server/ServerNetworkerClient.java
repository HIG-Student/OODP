package se.hig.oodp.b9.server;

import java.util.HashMap;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.PServerInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Two;
import se.hig.oodp.b9.Move;

/**
 * Client part of the networking communication
 */
public abstract class ServerNetworkerClient
{
    /**
     * This client's player
     */
    public Player player;

    /**
     * Send table
     * 
     * @param table
     *            the table to send
     */
    public abstract void sendTable(Table table);

    /**
     * Send new cards
     * 
     * @param cardIds
     *            the cards
     */
    public abstract void sendNewCards(UUID[] cardIds);

    /**
     * Alert of new player added
     * 
     * @param player
     *            the new player
     */
    public abstract void sendPlayerAdded(Player player);

    /**
     * Send new message
     * 
     * @param message
     *            the message
     */
    public void sendMessage(String message)
    {
        sendMessage(null, message);
    }

    /**
     * Send new message
     * 
     * @param source
     *            the sender
     * @param message
     *            the message
     */
    public abstract void sendMessage(Player source, String message);

    /**
     * Send card information
     * 
     * @param card
     *            the card the information is about
     */
    public abstract void sendCardInfo(Card card);

    /**
     * Send end-of-game alert and information
     * 
     * @param scores
     *            the scores for the players in this game
     * @param highScores
     *            the scored for the players for all games
     */
    public abstract void sendEndgame(HashMap<Player, Integer> scores, HashMap<Player, Integer> highScores);

    /**
     * Send card movement
     * 
     * @param card
     *            the moved card
     * @param collection
     *            the destination
     */
    public abstract void sendMoveCard(Card card, CardCollection collection);

    /**
     * Send new player turn
     * 
     * @param player
     *            the player
     */
    public abstract void sendPlayerTurn(Player player);

    /**
     * Send greeting
     * 
     * @param info
     *            information about the server
     */
    public abstract void sendGreeting(PServerInfo info);

    /**
     * Close the connection
     * 
     * @param reason
     *            the reason
     */
    public abstract void closeConnection(String reason);

    /**
     * Send move result
     * 
     * @param bool
     *            the result
     */
    public abstract void sendMoveResult(Boolean bool);

    // Get

    /**
     * Event invoked on new message
     */
    public Event<PMessage> onNewMessage = new Event<PMessage>();

    /**
     * Event invoked on new move
     */
    public Event<Two<Player, Move>> onNewMove = new Event<Two<Player, Move>>();
}
