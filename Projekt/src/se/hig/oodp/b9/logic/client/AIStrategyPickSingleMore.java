package se.hig.oodp.b9.logic.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

public class AIStrategyPickSingleMore extends AIStrategy
{
    public Move makeMove(ClientGame client, Player me, Table table)
    {
        for (UUID id : table.getCardCollection(table.getIdOfPlayerHand(me)))
        {
            List<List<UUID>> list = new ArrayList<List<UUID>>();

            for (UUID id2 : table.getPoolIds())
            {
                CardInfo info = client.getCardInfo(id);
                CardInfo info2 = client.getCardInfo(id2);

                if (info.getValue().equals(info2.getValue()))
                {
                    List<UUID> list2 = new ArrayList<UUID>();
                    list2.add(id2);
                    list.add(list2);
                }
            }
            
            if (!list.isEmpty())
                return new Move(id, list);
        }

        return new Move(table.getCardCollection(table.getIdOfPlayerHand(me))[0]);
    }
}
