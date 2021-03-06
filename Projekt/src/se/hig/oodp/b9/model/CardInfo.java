package se.hig.oodp.b9.model;

import java.io.Serializable;

/**
 * Information about a card
 */
@SuppressWarnings("serial")
public class CardInfo implements Serializable
{
    /**
     * Constant pointing to null
     */
    public static final CardInfo UNKNOWN = null;

    /**
     * The type of the card
     */
    public enum Type
    {
        /**
         * Kl�ver
         */
        Kl�ver,
        /**
         * Ruter
         */
        Ruter,
        /**
         * Hj�rter
         */
        Hj�rter,
        /**
         * Spader
         */
        Spader
    }

    /**
     * The value of the card
     */
    public enum Value
    {
        /**
         * Ess
         */
        Ess,
        /**
         * Tv�
         */
        Tv�,
        /**
         * Tre
         */
        Tre,
        /**
         * Fyra
         */
        Fyra,
        /**
         * Fem
         */
        Fem,
        /**
         * Sex
         */
        Sex,
        /**
         * Sju
         */
        Sju,
        /**
         * �tta
         */
        �tta,
        /**
         * Nio
         */
        Nio,
        /**
         * Tio
         */
        Tio,
        /**
         * Knekt
         */
        Knekt,
        /**
         * Dam
         */
        Dam,
        /**
         * Kung
         */
        Kung
    }

    /**
     * The type of the card
     */
    final Type type;

    /**
     * Get the type of the card
     * 
     * @return the type
     */
    public Type getType()
    {
        return type;
    }

    /**
     * The value of the card
     */
    final Value value;

    /**
     * Get the value of the card
     * 
     * @return the value
     */
    public Value getValue()
    {
        return value;
    }

    /**
     * Create a new cardinfo
     * 
     * @param type
     *            the type of the card
     * @param value
     *            the value of the card
     */
    public CardInfo(Type type, Value value)
    {
        this.type = type;
        this.value = value;
    }

    /**
     * Get a random card-info
     * 
     * @return a random card-info
     */
    public static CardInfo getRandom()
    {
        return new CardInfo(Type.values()[(int) (Math.random() * Type.values().length)], Value.values()[(int) (Math.random() * Value.values().length)]);
    }

    /**
     * type + " " + value
     */
    @Override
    public String toString()
    {
        return type + " " + value;
    }

    /**
     * Equals if type and value are same
     */
    @Override
    public boolean equals(Object other)
    {
        if (other == null || !(other instanceof CardInfo))
            return false;

        return (type == ((CardInfo) other).type) && (value == ((CardInfo) other).value);
    }

    /**
     * type.hashCode() + 23 * value.hashCode();
     */
    @Override
    public int hashCode()
    {
        return type.hashCode() + 23 * value.hashCode();
    }
}
