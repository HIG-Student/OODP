package se.hig.oodp.b9;

import java.util.UUID;

/**
 * The class representing a game card
 */
public class Card extends UUIDInstance
{
    // https://en.wikibooks.org/wiki/Java_Programming/Keywords/transient
    // this is to not send this info to the clients (then they could cheat)
    transient CardInfo cardInfo = CardInfo.UNKNOWN;

    public CardInfo getCardInfo()
    {
        return cardInfo;
    }

    public void setCardInfo(CardInfo info)
    {
        this.cardInfo = info;
    }

    public Card(CardInfo info)
    {
        this.id = UUID.randomUUID();

        setCardInfo(info);
    }
    
    public Card(UUID id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Card: " + ((cardInfo == null) ? "Unknown" : cardInfo);
    }
}
