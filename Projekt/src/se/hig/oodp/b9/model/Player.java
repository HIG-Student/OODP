package se.hig.oodp.b9.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * The representation of a player
 */
@SuppressWarnings("serial")
public class Player extends UUIDInstance implements Serializable
{
    /**
     * A unique id for this player's hand
     */
    public final UUID handUUID = UUID.randomUUID();
    /**
     * A unique id for this player's points (cards taken)
     */
    public final UUID pointsUUID = UUID.randomUUID();

    /**
     * Name of the player
     */
    String name;

    /**
     * Get the name
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Create player
     * 
     * @param name
     *            of the player
     */
    public Player(String name)
    {
        this.name = name;
        id = UUID.randomUUID();
    }

    @Override
    public String toString()
    {
        return getName() + " [" + id.toString() + "]";
    }
}
