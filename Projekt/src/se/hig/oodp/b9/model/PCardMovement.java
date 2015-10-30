package se.hig.oodp.b9.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Data about a card movement
 */
@SuppressWarnings("serial")
public class PCardMovement implements Serializable
{
    /**
     * Id of the moving card
     */
    UUID cardId;

    /**
     * ID of the destination
     */
    UUID cardCollectionId;

    /**
     * Create movement
     * 
     * @param cardId
     *            the id of the moving card
     * @param cardCollectionId
     *            the if of the destination
     */
    public PCardMovement(UUID cardId, UUID cardCollectionId)
    {
        this.cardId = cardId;
        this.cardCollectionId = cardCollectionId;
    }

    /**
     * Get the id of the moving card
     * 
     * @return the id of the moving card
     */
    public UUID getCardId()
    {
        return cardId;
    }

    /**
     * Get the id of the destination
     * 
     * @return the id of the destination
     */
    public UUID getCardCollcetionId()
    {
        return cardCollectionId;
    }
}
