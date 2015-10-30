package se.hig.oodp.b9.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.logic.Table;

/**
 * Represents an action the player can take
 */
@SuppressWarnings("serial")
public class Move implements Serializable
{
    /**
     * Id for the card the player have in the hand
     */
    private UUID activeCardId;
    /**
     * Ids for the cards the player want to pick up
     */
    private UUID[][] takeCardIds;

    /**
     * The card in the player's hand
     */
    private transient Card activeCard;
    /**
     * The card the player want to pick up
     */
    private transient List<List<Card>> takeCards = new ArrayList<List<Card>>();

    /**
     * The current batch of cards the player want to pick up
     */
    private transient List<Card> currentTake = new ArrayList<Card>();

    /**
     * Get the card in the player's hand
     * 
     * @return the card in the player's hand
     */
    public Card getActiveCard()
    {
        return activeCard;
    }

    /**
     * Get the cards the player want to pick up
     * 
     * @return the cards
     */
    public Card[][] getTakeCards()
    {
        Card[][] cards = new Card[takeCards.size()][];

        for (int i = 0; i < takeCards.size(); i++)
            cards[i] = takeCards.get(i).toArray(new Card[0]);

        return cards;
    }

    /**
     * Get the current batch of cards the player want to pick up
     * 
     * @return the cards
     */
    public Card[] getCurrentTakeCards()
    {
        return currentTake.toArray(new Card[0]);
    }

    /**
     * Create player action
     * 
     * @param activeCard
     *            card in the player's hand
     * @param takeCards
     *            cards on the table
     */
    public Move(Card activeCard, List<List<Card>> takeCards)
    {
        setActiveCard(activeCard);

        this.takeCards = takeCards;
        updateTakeIds();
    }

    /**
     * Create player action
     * 
     * @param activeCard
     *            card in the player's hand
     */
    public Move(Card activeCard)
    {
        setActiveCard(activeCard);
    }

    /**
     * Pick which card in the player's hand to use
     * 
     * @param card
     *            the card
     */
    public void setActiveCard(Card card)
    {
        activeCard = card;
        activeCardId = activeCard == null ? null : activeCard.getId();

        takeCards.clear();
        currentTake.clear();
        updateTakeIds();
    }

    /**
     * Toggles the selection of the card from the player's hand
     * 
     * @param card
     *            the card
     */
    public void toggleActive(Card card)
    {
        if (activeCard == card)
            setActiveCard(null);
        else
            setActiveCard(card);
    }

    /**
     * Updating the arrays with ids for the arrays with cards the player want to
     * take
     */
    private void updateTakeIds()
    {
        takeCardIds = new UUID[takeCards.size()][];

        for (int i = 0; i < takeCardIds.length; i++)
        {
            takeCardIds[i] = new UUID[takeCards.get(i).size()];

            for (int j = 0; j < takeCards.get(i).size(); j++)
            {
                takeCardIds[i][j] = takeCards.get(i).get(j).getId();
            }
        }
    }

    /**
     * Empties the cards the player want to take and the card in the player's
     * hand the player want to play
     */
    public void clearTake()
    {
        takeCards = new ArrayList<List<Card>>();
        currentTake = new ArrayList<Card>();

        updateTakeIds();
    }

    /**
     * Add card to the batch of cards the player intends to take
     * 
     * @param card
     *            the card
     */
    public void addTake(Card card)
    {
        if (!takeCards.contains(card))
        {
            currentTake.add(card);

            updateTakeIds();
        }
    }

    /**
     * Remove the card from the batch of cards the player intends to take
     * 
     * @param card
     *            the card
     */
    public void removeTake(Card card)
    {
        if (currentTake.contains(card))
        {
            currentTake.remove(card);

            updateTakeIds();
        }
    }

    /**
     * Toggles the selection of the card in the current batch of cards the
     * player intends to take
     * 
     * @param card
     *            the card
     */
    public void toggleTake(Card card)
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
    public boolean takeContains(Card card)
    {
        for (List<Card> list : takeCards)
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
    public boolean currentTakeContains(Card card)
    {
        return currentTake.contains(card);
    }

    /**
     * Saves the current batch of targeted cards and start a new one
     */
    public void nextTake()
    {
        takeCards.add(currentTake);
        currentTake = new ArrayList<Card>();

        updateTakeIds();
    }

    /**
     * Relink cards form an array of ids and a table
     * 
     * @param table
     *            the table that contains the cards to link to
     */
    public void populate(Table table)
    {
        if (activeCardId != null)
            activeCard = table.getCard(activeCardId);

        takeCards = new ArrayList<List<Card>>();

        for (int i = 0; i < takeCardIds.length; i++)
        {
            List<Card> list = new ArrayList<Card>();

            for (int j = 0; j < takeCardIds[i].length; j++)
            {
                list.add(table.getCard(takeCardIds[i][j]));
            }

            takeCards.add(list);
        }
    }
}