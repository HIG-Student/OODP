package se.hig.oodp.b9;

public class CardInfo
{
    public static final CardInfo UNKNOWN = null;

    /**
     * The type of the card
     */
    public enum Type
    {
        Kl�ver, Ruter, Hj�rter, Spader
    }

    /**
     * The value of the card
     */
    public enum Value
    {
        Ess, Tv�, Tre, Fyra, Fem, Sex, Sju, �tta, Nio, Tio, Knekt, Dam, Kung
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
