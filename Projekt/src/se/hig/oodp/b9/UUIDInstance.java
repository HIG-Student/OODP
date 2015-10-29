package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.UUID;

/**
 * A base with a (theoretically) guaranteed unique id
 */
public abstract class UUIDInstance implements Serializable
{
    /**
     * (theoretically) guaranteed unique id
     */
    UUID id;

    /**
     * Get the id
     * 
     * @return the (theoretically) guaranteed unique id of this instance
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * Set the id
     * 
     * @param id
     *            the (theoretically) guaranteed unique id of this instance
     */
    public void setId(UUID id)
    {
        this.id = id;
    }

    /**
     * Equals if the id of both UUIDInstance are the same
     */
    @Override
    public boolean equals(Object other)
    {
        return other != null && this.getClass().isInstance(other) && ((UUIDInstance) other).id.equals(id);
    }

    /**
     * returns id.hashCode() + 46435;
     */
    @Override
    public int hashCode()
    {
        return id.hashCode() + 46435;
    }
}
