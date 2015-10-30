package se.hig.oodp.b9.logic.client;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.Player;

public class AIStrategyThrowAll extends AIStrategy
{
    public Move makeMove(ClientGame client, Player me, Table table)
    {
        return new Move(table.getCardCollection(table.getIdOfPlayerHand(me))[0]);
    }
}
