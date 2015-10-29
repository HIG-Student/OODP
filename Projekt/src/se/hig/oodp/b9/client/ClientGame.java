package se.hig.oodp.b9.client;

import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Event;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Move;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Trigger;

public class ClientGame
{
    Player me;
    Table table;

    ClientNetworker networker;

    public final Trigger onChange = new Trigger();

    public final Event<Boolean> turnStatus = new Event<Boolean>();

    boolean myTurn = false;

    public ClientGame(Player me, ClientNetworker networker)
    {
        this.me = me;
        this.networker = networker;

        networker.onMove.add(move ->
        {
            table.moveCard(table.getCard(move.getCardId()), table.getCardCollection(move.getCardCollcetionId()));
            onChange.invoke();
        });

        networker.onCardInfo.add(two ->
        {
            table.getCard(two.getOne()).setCardInfo(two.getTwo());
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

        networker.onPlayerTurn.add((player) ->
        {
            turnStatus.invoke(myTurn = me.equals(player));
        });

        networker.onMoveResult.add(result ->
        {
            turnStatus.invoke(myTurn = !result);
        });
    }

    public ClientNetworker getNetworker()
    {
        return networker;
    }

    public CardCollection getMyHand()
    {
        return table.getPlayerHand(me);
    }

    public CardCollection getMyPoints()
    {
        return table.getPlayerPoints(me);
    }

    public boolean isMyTurn()
    {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn)
    {
        this.myTurn = myTurn;
    }

    public Player getMe()
    {
        return me;
    }

    public Table getTable()
    {
        return table;
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
