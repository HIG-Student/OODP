package se.hig.oodp.b9;

import java.util.UUID;

/**
 * The class representing a game card
 */
public class Card
{
    /**
     * A random and unique id to represent this card
     */
    public UUID id = UUID.randomUUID();

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

    /**
     * The type of the card
     */
    public Type type;

    /**
     * The value of the card
     */
    public Value value;

    /**
     * Construct a card
     * 
     * @param type
     *            the type of the card
     * @param value
     *            the value of the card
     */
    public Card(Type type, Value value)
    {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Card: " + type + " " + value;
    }
}
