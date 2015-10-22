package se.hig.oodp.b9;

public class CardInfo
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

    @Override
    public String toString()
    {
        return type + " " + value;
    }
}
