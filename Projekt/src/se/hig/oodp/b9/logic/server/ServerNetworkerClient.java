package se.hig.oodp.b9.logic.server;

import java.util.HashMap;
import java.util.UUID;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.communication.PMessage;
import se.hig.oodp.b9.communication.PServerInfo;
import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.logic.Two;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

/**
 * Client part of the networking communication
 */
public abstract class ServerNetworkerClient
{
    /**
     * This client's player
     */
    Player player;

    /**
     * Send table
     * 
     * @param table
     *            the table to send
     * @return success
     */
    public abstract boolean sendTable(Table table);

    /**
     * Alert of new player added
     * 
     * @param player
     *            the new player
     * @return success
     */
    public abstract boolean sendPlayerAdded(Player player);

    /**
     * Send new message
     * 
     * @param message
     *            the message
     * @return success
     */
    public boolean sendMessage(String message)
    {
        return sendMessage(null, message);
    }

    /**
     * Send new message
     * 
     * @param source
     *            the sender
     * @param message
     *            the message
     * @return success
     */
    public abstract boolean sendMessage(Player source, String message);

    /**
     * Send card information
     * 
     * @param card
     *            the card the information is about
     * @param info
     *            the info
     * @return success
     */
    public abstract boolean sendCardInfo(UUID card, CardInfo info);

    /**
     * Send end-of-game alert and information
     * 
     * @param scores
     *            the scores for the players in this game
     * @param highScores
     *            the scored for the players for all games
     * @return success
     */
    public abstract boolean sendEndgame(HashMap<Player, Integer> scores, HashMap<Player, Integer> highScores);

    /**
     * Send card movement
     * 
     * @param card
     *            the moved card
     * @param collection
     *            the destination
     * @return success
     */
    public abstract boolean sendMoveCard(UUID card, UUID collection);

    /**
     * Send new player turn
     * 
     * @param player
     *            the player
     * @return success
     */
    public abstract boolean sendPlayerTurn(Player player);

    /**
     * Send greeting
     * 
     * @param info
     *            information about the server
     * @return success
     */
    public abstract boolean sendGreeting(PServerInfo info);

    /**
     * Close the connection
     * 
     * @param reason
     *            the reason
     * @return success
     */
    public abstract boolean closeConnection(String reason);

    /**
     * Send move result
     * 
     * @param bool
     *            the result
     * @return success
     */
    public abstract boolean sendMoveResult(Boolean bool);

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
