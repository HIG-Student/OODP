package se.hig.oodp.b9.client;

import java.util.ArrayList;
import java.util.List;

import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Trigger;
import se.hig.oodp.b9.CardInfo;

public class ClientGame
{
    public Player me;
    public List<Player> players = new ArrayList<Player>();
    public Table table;

    public ClientNetworker networker;

    public Trigger onChange = new Trigger();

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
            table.addDeck(cards);
        });
        
        networker.sendGreeting(me);
    }
}
