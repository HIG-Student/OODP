package se.hig.oodp.b9.logic.client;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.Player;

public abstract class AIStrategy
{
    public abstract Move makeMove(ClientGame client, Player me, Table table);
}
