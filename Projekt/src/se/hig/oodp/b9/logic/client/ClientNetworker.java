package se.hig.oodp.b9.logic.client;

import java.util.HashMap;
import java.util.UUID;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.communication.PCardMovement;
import se.hig.oodp.b9.communication.PMessage;
import se.hig.oodp.b9.communication.PServerInfo;
import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.logic.Two;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

/**
 * The client networker
 */
public abstract class ClientNetworker
{
    /**
     * Close the communication
     * 
     * @param clientIssued
     *            issued by the client
     * 
     * @param reason
     *            the reason
     */
    public abstract void close(boolean clientIssued, String reason);

    /**
     * Close the communication
     * 
     * @param reason
     *            the reason
     */
    public void close(String reason)
    {
        close(true, reason);
    }

    /**
     * Close the communication
     */
    public void close()
    {
        close(true, "Client closed");
    }

    /**
     * Send move to server
     * 
     * @param move
     *            the move to send
     * @return success
     */
    public abstract boolean sendMove(Move move);

    /**
     * Send message to server
     * 
     * @param message
     *            the message to send
     */
    public void sendMessage(String message)
    {
        sendMessageTo(null, message);
    }

    /**
     * Send message to player
     * 
     * @param target
     *            the player to send to
     * @param message
     *            the message
     * @return success
     */
    public abstract boolean sendMessageTo(Player target, String message);

    /**
     * Send greeting to server
     * 
     * @param player
     *            our player
     * @return success
     */
    public abstract boolean sendGreeting(Player player);

    // Get

    /**
     * Event invoked on new move
     */
    public Event<PCardMovement> onMove = new Event<PCardMovement>();

    /**
     * Event invoked on change of player turn
     */
    public Event<Player> onPlayerTurn = new Event<Player>();

    /**
     * Event invoked on closing
     */
    public Event<String> onClose = new Event<String>();

    /**
     * Event invoked on new move result
     */
    public Event<Boolean> onMoveResult = new Event<Boolean>();

    /**
     * Event invoked on end of game
     */
    public Event<Two<HashMap<Player, Integer>, HashMap<Player, Integer>>> onEndGame = new Event<Two<HashMap<Player, Integer>, HashMap<Player, Integer>>>();

    /**
     * Event invoked on new message
     */
    public Event<PMessage> onMessage = new Event<PMessage>();

    /**
     * Event invoked on new table
     */
    public Event<Table> onTable = new Event<Table>();

    /**
     * Event invoked on new card info
     */
    public Event<Two<UUID, CardInfo>> onCardInfo = new Event<Two<UUID, CardInfo>>();

    /**
     * Event invoked on new player added
     */
    public Event<Player> onPlayerAdded = new Event<Player>();

    /**
     * Event invoked on server greeting
     */
    public Event<PServerInfo> onServerGreeting = new Event<PServerInfo>();
}
