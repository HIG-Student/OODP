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
     */
    public void sendTable(Table table)
    {
        for (ServerNetworkerClient client : clients)
            client.sendTable(table);
    }

    /**
     * Alert clients of new player
     * 
     * @param player
     *            the new player
     */
    public void sendPlayerAdded(Player player)
    {
        for (ServerNetworkerClient client : clients)
            if (client.player != player)
                client.sendPlayerAdded(player);
    }

    /**
     * Send message to all clients
     * 
     * @param message
     *            the message to send
     */
    public void sendMessageToAll(String message)
    {
        for (ServerNetworkerClient client : clients)
            client.sendMessage(message);
    }

    /**
     * Send message from a client to all clients
     * 
     * @param source
     *            the player that are sending this message
     * @param message
     *            the message
     */
    public void sendMessageToAll(Player source, String message)
    {
        for (ServerNetworkerClient client : clients)
            client.sendMessage(source, message);
    }

    /**
     * Send message to player
     * 
     * @param target
     *            player to send message to
     * @param message
     *            the message
     */
    public void sendMessageTo(Player target, String message)
    {
        sendMessageTo(null, target, message);
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
     */
    public void sendMessageTo(Player source, Player target, String message)
    {
        ServerNetworkerClient client = getClient(target);
        if (client != null)
            client.sendMessage(source, message);
    }

    /**
     * Send information about a card
     * 
     * @param target
     *            the target player
     * @param card
     *            the card to send info about
     */
    public void sendCardInfo(Player target, UUID card, CardInfo info)
    {
        ServerNetworkerClient client = getClient(target);
        if (client != null)
            client.sendCardInfo(card, info);
    }

    /**
     * Send alert that it is the den of the game
     * 
     * @param scores
     *            the scores of the players for the last game
     * @param highScores
     *            the scores for all games
     */
    public void sendEndGame(HashMap<Player, Integer> scores, HashMap<Player, Integer> totalScores)
    {
        for (ServerNetworkerClient client : clients)
            client.sendEndgame(scores, totalScores);
    }

    /**
     * Send move card update
     * 
     * @param card
     *            the card that moved
     * @param collection
     *            the destination
     */
    public void sendMoveCard(UUID card, UUID collection)
    {
        for (ServerNetworkerClient client : clients)
            client.sendMoveCard(card, collection);
    }

    /**
     * Send player turn update
     * 
     * @param player
     *            the new player
     */
    public void sendPlayerTurn(Player player)
    {
        for (ServerNetworkerClient client : clients)
            client.sendPlayerTurn(player);
    }

    /**
     * Send greeting to player
     * 
     * @param player
     *            the player
     * @param info
     *            server info
     */
    public void sendGreeting(Player player, PServerInfo info)
    {
        ServerNetworkerClient client = getClient(player);
        if (client != null)
            client.sendGreeting(info);
    }

    /**
     * Close the connection
     * 
     * @param player
     *            the player to drop
     * @param reason
     *            the reason
     */
    public void closeConnection(Player player, String reason)
    {
        ServerNetworkerClient client = getClient(player);
        if (client != null)
        {
            client.closeConnection(reason);
            synchronized (clients)
            {
                clients.remove(client);
            }
            onPlayerDisconecting.invoke(player);
        }
    }

    /**
     * Send move result
     * 
     * @param player
     *            the player
     * @param bool
     *            was the move allowed?
     */
    public void sendMoveResult(Player player, boolean bool)
    {
        ServerNetworkerClient client = getClient(player);
        if (client != null)
            client.sendMoveResult(bool);
    }

    /**
     * Kill connection
     */
    public abstract void kill();

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
