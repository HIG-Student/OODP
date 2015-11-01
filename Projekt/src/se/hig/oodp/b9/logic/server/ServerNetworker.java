package se.hig.oodp.b9.logic.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.communication.PServerInfo;
import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.logic.Two;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

/**
 * Communication handler
 */
public abstract class ServerNetworker
{
    /**
     * Connected clients
     */
    List<ServerNetworkerClient> clients = new ArrayList<ServerNetworkerClient>();

    /**
     * Get connected player for player
     * 
     * @param player
     *            the player
     * @return the client
     */
    ServerNetworkerClient getClient(Player player)
    {
        synchronized (clients)
        {
            for (ServerNetworkerClient client : clients)
                if (client.player.equals(player))
                    return client;
        }

        return null;
    }

    /**
     * Add client
     * 
     * @param client
     *            client to add
     */
    void addClient(ServerNetworkerClient client)
    {
        synchronized (clients)
        {
            clients.add(client);
        }
        client.onNewMessage.add(message -> onNewMessage.invoke(new Two<Player, String>(client.player, message.getMessage())));
        client.onNewMove.add(move -> onNewMove.invoke(move));
        onPlayerConnecting.invoke(client.player);
    }

    /**
     * Send table to clients
     * 
     * @param table
     *            table to send
     * @return success
     */
    public boolean sendTable(Table table)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (!client.sendTable(table))
                ok = false;
        return ok;
    }

    /**
     * Alert clients of new player
     * 
     * @param player
     *            the new player
     * @return success
     */
    public boolean sendPlayerAdded(Player player)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (client.player != player)
                if (!client.sendPlayerAdded(player))
                    ok = false;
        return ok;
    }

    /**
     * Send message to all clients
     * 
     * @param message
     *            the message to send
     * @return success
     */
    public boolean sendMessageToAll(String message)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (!client.sendMessage(message))
                ok = false;
        return ok;
    }

    /**
     * Send message from a client to all clients
     * 
     * @param source
     *            the player that are sending this message
     * @param message
     *            the message
     * @return success
     */
    public boolean sendMessageToAll(Player source, String message)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (!client.sendMessage(source, message))
                ok = false;
        return ok;
    }

    /**
     * Send message to player
     * 
     * @param target
     *            player to send message to
     * @param message
     *            the message
     * @return success
     */
    public boolean sendMessageTo(Player target, String message)
    {
        return sendMessageTo(null, target, message);
    }

    /**
     * Send message from player to player
     * 
     * @param source
     *            from player
     * @param target
     *            to player
     * @param message
     *            the message
     * @return success
     */
    public boolean sendMessageTo(Player source, Player target, String message)
    {
        boolean ok = true;
        ServerNetworkerClient client = getClient(target);
        if (client != null)
            if (!client.sendMessage(source, message))
                ok = false;
        return ok;
    }

    /**
     * Send information about a card
     * 
     * @param target
     *            the target player
     * @param card
     *            the card to send info about
     * @param info
     *            the info
     * @return success
     */
    public boolean sendCardInfo(Player target, UUID card, CardInfo info)
    {
        boolean ok = true;
        ServerNetworkerClient client = getClient(target);
        if (client != null)
            if (!client.sendCardInfo(card, info))
                ok = false;
        return ok;
    }

    /**
     * Send alert that it is the den of the game
     * 
     * @param scores
     *            the scores of the players for the last game
     * @param totalScores
     *            the scores for all games
     * @return success
     */
    public boolean sendEndGame(HashMap<Player, Integer> scores, HashMap<Player, Integer> totalScores)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (!client.sendEndgame(scores, totalScores))
                ok = false;
        return ok;
    }

    /**
     * Send move card update
     * 
     * @param card
     *            the card that moved
     * @param collection
     *            the destination
     * @return success
     */
    public boolean sendMoveCard(UUID card, UUID collection)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (!client.sendMoveCard(card, collection))
                ok = false;
        return ok;
    }

    /**
     * Send player turn update
     * 
     * @param player
     *            the new player
     * @return success
     */
    public boolean sendPlayerTurn(Player player)
    {
        boolean ok = true;
        for (ServerNetworkerClient client : clients)
            if (!client.sendPlayerTurn(player))
                ok = false;
        return ok;
    }

    /**
     * Send greeting to player
     * 
     * @param player
     *            the player
     * @param info
     *            server info
     * @return success
     */
    public boolean sendGreeting(Player player, PServerInfo info)
    {
        boolean ok = true;
        ServerNetworkerClient client = getClient(player);
        if (client != null)
            if (!client.sendGreeting(info))
                ok = false;
        return ok;
    }

    /**
     * Close the connection
     * 
     * @param player
     *            the player to drop
     * @param reason
     *            the reason
     * @return success
     */
    public boolean closeConnection(Player player, String reason)
    {
        boolean ok = true;
        ServerNetworkerClient client = getClient(player);
        if (client != null)
        {
            if (!client.closeConnection(reason))
                ok = false;
            synchronized (clients)
            {
                clients.remove(client);
            }
            onPlayerDisconecting.invoke(player);
        }
        return ok;
    }

    /**
     * Send move result
     * 
     * @param player
     *            the player
     * @param bool
     *            was the move allowed?
     * @return success
     */
    public boolean sendMoveResult(Player player, boolean bool)
    {
        boolean ok = true;
        ServerNetworkerClient client = getClient(player);
        if (client != null)
            if (!client.sendMoveResult(bool))
                ok = false;
        return ok;
    }

    /**
     * Kill connection
     */
    public void kill()
    {
        kill(null);
    }

    /**
     * Kill connection
     * 
     * @param reason
     *            the reason
     */
    public abstract void kill(String reason);

    // Get

    /**
     * Event invoked on player connecting
     */
    public final Event<Player> onPlayerConnecting = new Event<Player>();

    /**
     * Event invoked on new log entry
     */
    public final Event<String> onLog = new Event<String>();

    /**
     * Event invoked on player disconnecting
     */
    public final Event<Player> onPlayerDisconecting = new Event<Player>();

    /**
     * Event invoked on new message
     */
    public final Event<Two<Player, String>> onNewMessage = new Event<Two<Player, String>>();

    /**
     * Event invoked on new move
     */
    public final Event<Two<Player, Move>> onNewMove = new Event<Two<Player, Move>>();

    /**
     * Event invoked on killing the connection
     */
    public final Event<Boolean> onKill = new Event<Boolean>();
}
