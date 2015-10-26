package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.UUID;

public class PMessage implements Serializable
{
    Player source;
    String message;
    
    public PMessage(Player source,String message)
    {
        this.source = source;
        this.message = message;
    }
    
    public PMessage(String message)
    {
        this.source = null;
        this.message = message;
    }
    
    public Player getSource()
    {
        return source;
    }
    
    public String getMessage()
    {
        return message;
    }
}
