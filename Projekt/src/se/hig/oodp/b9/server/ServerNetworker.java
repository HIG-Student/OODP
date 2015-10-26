package se.hig.oodp.b9.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Two;

public abstract class ServerNetworker
{
    List<ServerNetworkerClient> clients = new ArrayList<ServerNetworkerClient>();

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

    public void sendTable(Table table)
    {
        for (ServerNetworkerClient client : clients)
            client.sendTable(table);
    }

    public void sendNewCards(UUID[] cardIds)
    {
        for (ServerNetworkerClient client : clients)
            client.sendNewCards(cardIds);
    }

    public void sendPlayerAdded(Player player)
    {
        for (ServerNetworkerClient client : clients)
            if (client.player != player)
                client.sendPlayerAdded(player);
    }

    public void sendMessageToAll(String message)
    {
        for (ServerNetworkerClient client : clients)
            client.sendMessage(message);
    }

    public void sendMessageToAll(Player source, String message)
    {
        for (ServerNetworkerClient client : clients)
            client.sendMessage(source, message);
    }

    public void sendMessageTo(Player target, String message)
    {
        sendMessageTo(null, target, message);
    }

    public void sendMessageTo(Player source, Player target, String message)
    {
        ServerNetworkerClient client = getClient(target);
        if (client != null)
            client.sendMessage(target, message);
    }

    public void sendCardInfo(Player target, Card card)
    {
        ServerNetworkerClient client = getClient(target);
        if (client != null)
            client.sendCardInfo(card);
    }

    public void sendEndgame() // , result?
    {
        for (ServerNetworkerClient client : clients)
            client.sendEndgame();
    }

    public void sendMoveCard(Card card, CardCollection collection)
    {
        for (ServerNetworkerClient client : clients)
            client.sendMoveCard(card, collection);
    }

    public void sendGetMove(Player player)
    {
        ServerNetworkerClient client = getClient(player);
        if (client != null)
            client.sendGetMove();
    }

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

    // Get

    public Event<Player> onPlayerConnecting = new Event<Player>();

    public Event<Player> onPlayerDisconecting = new Event<Player>();

    public Event<Two<Player, String>> onNewMessage = new Event<Two<Player, String>>();

    public Event<PCardMovement> onNewMove = new Event<PCardMovement>();
}