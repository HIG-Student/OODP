package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.UUID;

/**
 * The representation of a player
 */
public class Player extends UUIDInstance implements Serializable
{
    public final UUID handUUID = UUID.randomUUID();
    public final UUID pointsUUID = UUID.randomUUID();

    /**
     * Name of the player
     */
    String name;

    public String getName()
    {
        return name;
    }

    public Player(String name)
    {
        this.name = name;
        id = UUID.randomUUID();
    }

    public Player(String name, UUID id)
    {
        this.name = name;
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return getName() + " [" + id.toString() + "]";
    }
}
