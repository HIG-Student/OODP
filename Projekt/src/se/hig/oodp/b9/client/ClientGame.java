package se.hig.oodp.b9.client;

import java.util.ArrayList;
import java.util.List;

import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Move;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Trigger;

public class ClientGame
{
    public Player me;
    public Table table;

    ClientNetworker networker;

    public Trigger onChange = new Trigger();

    public Event<Boolean> turnStatus = new Event<Boolean>();

    boolean myTurn = false;

    public ClientGame(Player me, ClientNetworker networker)
    {
        this.me = me;
        this.networker = networker;

        networker.onMove.add(move ->
        {
            table.moveCard(table.cards.get(move.getCardId()), table.collections.get(move.getCardCollcetionId()));
            onChange.invoke();
        });

        networker.onCardInfo.add(two ->
        {
            table.cards.get(two.one).setCardInfo(two.two);
            onChange.invoke();
        });

        networker.onTable.add(table ->
        {
            this.table = table;
            onChange.invoke();
        });

        networker.onCards.add(cards ->
        {
            table.changeDeck(cards);
        });

        networker.onMoveRequest.add(() ->
        {
            turnStatus.invoke(myTurn = true);
        });

        networker.onMoveResult.add(result ->
        {
            turnStatus.invoke(myTurn = !result);
        });
    }

    boolean greetingSent = false;

    public ClientGame sendGreeting()
    {
        if (greetingSent)
            return this;
        greetingSent = true;
        
        networker.sendGreeting(me);
        return this;
    }

    public void end(String reason)
    {
        networker.close(reason);
    }

    public void end()
    {
        networker.close();
    }

    public ClientGame makeMove(Move move)
    {
        networker.sendMove(move);
        return this;
    }

    public boolean makeMoveAndWait(Move move)
    {
        networker.sendMove(move);
        return networker.onMoveResult.waitFor();
    }
}
