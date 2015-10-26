package se.hig.oodp.b9;

import java.io.Serializable;

public class CardInfo implements Serializable
{
    public static final CardInfo UNKNOWN = null;

    /**
     * The type of the card
     */
    public enum Type
    {
        Klöver, Ruter, Hjärter, Spader
    }

    /**
     * The value of the card
     */
    public enum Value
    {
        Ess, Två, Tre, Fyra, Fem, Sex, Sju, Åtta, Nio, Tio, Knekt, Dam, Kung
    }

    Type type;

    public Type getType()
    {
        return type;
    }

    Value value;

    public Value getValue()
    {
        return value;
    }

    public CardInfo(Type type, Value value)
    {
        this.type = type;
        this.value = value;
    }

    public static CardInfo getRandom()
    {
        return new CardInfo(Type.values()[(int) (Math.random() * Type.values().length)], Value.values()[(int) (Math.random() * Value.values().length)]);
    }

    @Override
    public String toString()
    {
        return type + " " + value;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null || !(other instanceof CardInfo))
            return false;

        return (type == ((CardInfo) other).type) && (value == ((CardInfo) other).value);
    }

    @Override
    public int hashCode()
    {
        return type.hashCode() + 23 * value.hashCode();
    }
}
