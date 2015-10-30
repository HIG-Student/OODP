package se.hig.oodp.b9.communication;

import java.io.Serializable;

/**
 * Class containing information that is sent between the client and server
 * 
 * @param <T>
 *            the data type this package contains
 */
@SuppressWarnings("serial")
public class Package<T extends Serializable> implements Serializable
{
    /**
     * Enum representing what a package is intended for
     */
    public enum Type
    {
        /**
         * Sending a new table
         */
        Table,
        /**
         * Sending information about a card
         */
        CardInfo,
        /**
         * Sending a move
         * 
         * @see Move
         */
        Move,
        /**
         * Player added
         */
        PlayerAdded,
        /**
         * Message
         * 
         * @see PMessage
         */
        Message,
        /**
         * The player whose turn it is
         */
        PlayerTurn,
        /**
         * Close connection
         */
        Close,
        /**
         * Information about the server
         */
        ServerInfo,
        /**
         * Result for requested move
         */
        MoveResult,
        /**
         * Result for requested move
         */
        EndGame
    }

    /**
     * The intention of this package
     */
    Type type;

    /**
     * The value this packaged contains (payload)
     */
    T value;

    /**
     * Create package
     * 
     * @param value
     *            payload
     * @param type
     *            intention
     */
    public Package(T value, Type type)
    {
        this.value = value;
        this.type = type;
    }

    /**
     * Get the intention
     * 
     * @return the intention
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Get the payload
     * 
     * @return the payload
     */
    public T getValue()
    {
        return value;
    }
}
