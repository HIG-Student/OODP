/**
 * 
 */
package se.hig.oodp.b9;

import java.util.UUID;

/**
 * The representation of a player
 */
public abstract class AbstractPlayer
{
    /**
     * A random and unique id to represent this card
     */
    public UUID id = UUID.randomUUID();

    /**
     * Name of the player
     */
    public String name;

    /**
     * Counts this player's cards
     * 
     * @return the amount of cards this player have
     */
    public abstract int getCardCount();
}
