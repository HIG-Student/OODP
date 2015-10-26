package se.hig.oodp.b9.server;

import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;

public abstract class ServerNetworkerClient
{
    public Player player;

    public abstract void sendTable(Table table);

    public abstract void sendNewCards(UUID[] cardIds);

    public abstract void sendPlayerAdded(Player player);

    public void sendMessage(String message)
    {
        sendMessage(null, message);
    }

    public abstract void sendMessage(Player source, String message);

    public abstract void sendCardInfo(Card card);

    public abstract void sendEndgame(); // , result?

    public abstract void sendMoveCard(Card card, CardCollection collection);

    public abstract void sendGetMove();

    public abstract void closeConnection(String reason);

    // Get

    public Event<PMessage> onNewMessage = new Event<PMessage>();

    public Event<PCardMovement> onNewMove = new Event<PCardMovement>();
}
