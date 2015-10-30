package se.hig.oodp.b9.logic.client;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.Player;

/**
 * Strategy to use when playing
 */
public abstract class AIStrategy
{
    /**
     * Get a move
     * 
     * @param client
     *            the client to act on
     * @param me
     *            the AI's player
     * @param table
     *            the table to look at
     * @return the move
     */
    public abstract Move makeMove(ClientGame client, Player me, Table table);
}
