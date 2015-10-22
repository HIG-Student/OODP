package se.hig.oodp.b9;

import java.util.UUID;

/**
 * The class representing a game card
 */
public class Card
{
    CardInfo cardInfo;
    
    public CardInfo getCardInfo()
    {
        return cardInfo;
    }
    
    public void setCardInfo(CardInfo info)
    {
        this.cardInfo = info;
    }
    
    /**
     * A random and unique id to represent this card
     */
    UUID id;
    
    public UUID getId()
    {
        return id;
    }

    public Card(UUID id)
    {
        this.id = id;
    }

    /**
     * Construct a card
     * 
     * @param type
     *            the type of the card
     * @param value
     *            the value of the card
     */
    public Card(CardInfo info)
    {
        this.id = UUID.randomUUID();

        setCardInfo(info);
    }

    @Override
    public String toString()
    {
        return "Card: " + ((cardInfo == null) ? "Unknown" : cardInfo);
    }
}
