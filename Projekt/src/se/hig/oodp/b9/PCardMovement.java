package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.UUID;

public class PCardMovement implements Serializable
{
    UUID cardId;
    UUID cardCollectionId;
    
    public PCardMovement(UUID cardId,UUID cardCollectionId)
    {
        this.cardId = cardId;
        this.cardCollectionId = cardCollectionId;
    }
    
    public UUID getCardId()
    {
        return cardId;
    }
    
    public UUID getCardCollcetionId()
    {
        return cardCollectionId;
    }
}
