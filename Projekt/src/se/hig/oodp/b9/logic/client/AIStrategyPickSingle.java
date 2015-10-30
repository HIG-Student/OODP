package se.hig.oodp.b9.logic.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

/**
 * AI picks up one card from the pool if the AI have the same card on the hand
 */
public class AIStrategyPickSingle extends AIStrategy
{
    public Move makeMove(ClientGame client, Player me, Table table)
    {
        for (UUID id : table.getPoolIds())
        {
            CardInfo info = client.getCardInfo(id);
            for (UUID id2 : table.getCardCollection(table.getIdOfPlayerHand(me)))
                if (info.getValue().equals(client.getCardInfo(id2).getValue()))
                {
                    List<List<UUID>> list = new ArrayList<List<UUID>>();
                    List<UUID> list2 = new ArrayList<UUID>();
                    list2.add(id);
                    list.add(list2);
                    return new Move(id2, list);
                }
        }

        return new Move(table.getCardCollection(table.getIdOfPlayerHand(me))[0]);
    }
}
