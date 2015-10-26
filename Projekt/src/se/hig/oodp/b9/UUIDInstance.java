package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.UUID;

public abstract class UUIDInstance implements Serializable
{
    UUID id;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object other)
    {
        return other != null && this.getClass().isInstance(other) && ((UUIDInstance) other).id.equals(id);
    }
    
    @Override
    public int hashCode()
    {
        return id.hashCode() + 46435;
    }
}
