package se.hig.oodp.b9.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an action the player can take
 */
@SuppressWarnings("serial")
public class Move implements Serializable
{
    /**
     * The card in the player's hand
     */
    private UUID activeCard;
    /**
     * The card the player want to pick up
     */
    private List<List<UUID>> takeCard = new ArrayList<List<UUID>>();

    /**
     * The current batch of cards the player want to pick up
     */
    private List<UUID> currentTake = new ArrayList<UUID>();

    /**
     * Get the card in the player's hand
     * 
     * @return the card in the player's hand
     */
    public UUID getActiveCard()
    {
        return activeCard;
    }

    /**
     * Get the cards the player want to pick up
     * 
     * @return the cards
     */
    public UUID[][] getTakeCards()
    {
        UUID[][] cards = new UUID[takeCard.size()][];

        for (int i = 0; i < takeCard.size(); i++)
            cards[i] = takeCard.get(i).toArray(new UUID[0]);

        return cards;
    }

    /**
     * Get the current batch of cards the player want to pick up
     * 
     * @return the cards
     */
    public UUID[] getCurrentTakeCards()
    {
        return currentTake.toArray(new UUID[0]);
    }

    /**
     * Create player action
     * 
     * @param activeCard
     *            card in the player's hand
     * @param takeCards
     *            cards on the table
     */
    public Move(UUID activeCard, List<List<UUID>> takeCards)
    {
        setActiveCard(activeCard);

        this.takeCard = takeCards;
    }

    /**
     * Create player action
     * 
     * @param activeCard
     *            card in the player's hand
     */
    public Move(UUID activeCard)
    {
        setActiveCard(activeCard);
    }

    /**
     * Pick which card in the player's hand to use
     * 
     * @param card
     *            the card
     */
    public void setActiveCard(UUID card)
    {
        activeCard = card;

        takeCard.clear();
        currentTake.clear();
    }

    /**
     * Toggles the selection of the card from the player's hand
     * 
     * @param card
     *            the card
     */
    public void toggleActive(UUID card)
    {
        if (activeCard == card)
            setActiveCard(null);
        else
            setActiveCard(card);
    }

    /**
     * Empties the cards the player want to take and the card in the player's
     * hand the player want to play
     */
    public void clearTake()
    {
        takeCard = new ArrayList<List<UUID>>();
        currentTake = new ArrayList<UUID>();
    }

    /**
     * Add card to the batch of cards the player intends to take
     * 
     * @param card
     *            the card
     */
    public void addTake(UUID card)
    {
        if (!takeCard.contains(card))
            currentTake.add(card);
    }

    /**
     * Remove the card from the batch of cards the player intends to take
     * 
     * @param card
     *            the card
     */
    public void removeTake(UUID card)
    {
        if (currentTake.contains(card))
            currentTake.remove(card);
    }

    /**
     * Toggles the selection of the card in the current batch of cards the
     * player intends to take
     * 
     * @param card
     *            the card
     */
    public void toggleTake(UUID card)
    {
        if (currentTake.contains(card))
            removeTake(card);
        else
            addTake(card);
    }

    /**
     * Check if the card is already marked as targeted
     * 
     * @param card
     *            the card to check
     * @return if the card is marked as targeted or not
     */
    public boolean takeContains(UUID card)
    {
        for (List<UUID> list : takeCard)
            if (list.contains(card))
                return true;

        return false;
    }

    /**
     * Check if the card is in the current batch of targeted cards
     * 
     * @param card
     *            the card to check
     * @return if the card is in the current batch
     */
    public boolean currentTakeContains(UUID card)
    {
        return currentTake.contains(card);
    }

    /**
     * Saves the current batch of targeted cards and start a new one
     */
    public void nextTake()
    {
        takeCard.add(currentTake);
        currentTake = new ArrayList<UUID>();
    }
}