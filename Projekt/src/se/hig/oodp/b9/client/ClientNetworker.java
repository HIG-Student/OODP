package se.hig.oodp.b9.client;

import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Trigger;
import se.hig.oodp.b9.Two;

public abstract class ClientNetworker
{
    // Send
    public abstract void sendMove(PCardMovement move);

    public void sendMessage(String message)
    {
        sendMessageTo(null, message);
    }

    public abstract void sendMessageTo(Player target, String message);

    public abstract void sendGreeting(Player target);

    // Get

    public Event<PCardMovement> onMove = new Event<PCardMovement>();

    public Trigger onMoveRequest = new Trigger();

    public Event<String> onClose = new Event<String>();

    public Event<PMessage> onMessage = new Event<PMessage>();

    public Event<Table> onTable = new Event<Table>();

    public Event<UUID[]> onCards = new Event<UUID[]>();

    public Event<Two<UUID,CardInfo>> onCardInfo = new Event<Two<UUID,CardInfo>>();

    public Event<Player> onPlayer = new Event<Player>();
}
