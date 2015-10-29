package se.hig.oodp.b9.client;

import java.util.HashMap;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.PServerInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Move;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Two;

public abstract class ClientNetworker
{
    public abstract void close(String reason);
    
    public void close()
    {
        close(null);
    }
    
    // Send
    public abstract void sendMove(Move move);

    public void sendMessage(String message)
    {
        sendMessageTo(null, message);
    }

    public abstract void sendMessageTo(Player target, String message);

    public abstract void sendGreeting(Player target);

    // Get

    public Event<PCardMovement> onMove = new Event<PCardMovement>();

    public Event<Player> onPlayerTurn = new Event<Player>();

    public Event<String> onClose = new Event<String>();
    
    public Event<Boolean> onMoveResult = new Event<Boolean>();
    
    public Event<HashMap<Player,Integer>> onEndGame = new Event<HashMap<Player,Integer>>();

    public Event<PMessage> onMessage = new Event<PMessage>();

    public Event<Table> onTable = new Event<Table>();

    public Event<Card[]> onCards = new Event<Card[]>();

    public Event<Two<UUID,CardInfo>> onCardInfo = new Event<Two<UUID,CardInfo>>();

    public Event<Player> onPlayerAdded = new Event<Player>();
    
    public Event<PServerInfo> onServerGreeting = new Event<PServerInfo>();
}
