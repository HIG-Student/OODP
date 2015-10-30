package se.hig.oodp.b9.model;

import java.util.UUID;

/**
 * The class representing a game card
 */
@SuppressWarnings("serial")
public class Card extends UUIDInstance
{
    /**
     * The information about the card (value and type) <br>
     * <br>
     * Marked transient so that it does not get sent from the server to the
     * client, as a client then could peek at it <br>
     * <br>
     * <a href=
     * "https://en.wikibooks.org/wiki/Java_Programming/Keywords/transient">See
     * wikipedia about the transient keyword</a>
     * 
     * @see CardInfo
     */
    transient CardInfo cardInfo = CardInfo.UNKNOWN;

    /**
     * Get the cardinfo
     * 
     * @return the cardinfo
     */
    public CardInfo getCardInfo()
    {
        return cardInfo;
    }

    /**
     * Create card from cardinfo
     * 
     * @param info
     *            the cardinfo representing this card
     */
    public Card(CardInfo info)
    {
        this.id = UUID.randomUUID();

        cardInfo = info;
    }

    /**
     * Create card from id
     * 
     * @param id
     *            the id of the card
     */
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
