package se.hig.oodp.b9.model;

import java.io.Serializable;

/**
 * A message
 */
@SuppressWarnings("serial")
public class PMessage implements Serializable
{
    /**
     * Speaking player (or null if server)
     */
    Player source;

    /**
     * Message
     */
    String message;

    /**
     * Create message
     * 
     * @param source
     *            the speaking player
     * @param message
     *            the message
     */
    public PMessage(Player source, String message)
    {
        this.source = source;
        this.message = message;
    }

    /**
     * Create message
     * 
     * @param message
     *            the message
     */
    public PMessage(String message)
    {
        this.source = null;
        this.message = message;
    }

    /**
     * Get the speaking player
     * 
     * @return the speaking player (or null)
     */
    public Player getSource()
    {
        return source;
    }

    /**
     * Get the message
     * 
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }
}
