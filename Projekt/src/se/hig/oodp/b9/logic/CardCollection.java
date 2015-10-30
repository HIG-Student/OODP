package se.hig.oodp.b9.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.Player;
import se.hig.oodp.b9.model.UUIDInstance;

/**
 * Collection of cards
 */
@SuppressWarnings("serial")
public class CardCollection extends UUIDInstance
{
    /**
     * The player that owns this card collection (or null)
     */
    public final Player owner;

    /**
     * The cards in this card collection
     */
    List<Card> cards = new ArrayList<Card>();

    /**
     * Create a new card collection
     */
    public CardCollection()
    {
        id = UUID.randomUUID();
        this.owner = null;
    }

    /**
     * Create a new card collection
     * 
     * @param owner
     *            the player that owns this collection
     */
    public CardCollection(Player owner)
    {
        id = UUID.randomUUID();
        this.owner = owner;
    }

    /**
     * Create a new card collection based on an exsisting id
     * 
     * @param id
     *            the id of the collection
     * @param owner
     *            the player that owns this collection
     */
    public CardCollection(UUID id, Player owner)
    {
        this.id = id;
        this.owner = owner;
    }

    /**
     * Get the first card
     * 
     * @return the first card in this collection
     */
    public Card getFirstCard()
    {
        return cards.isEmpty() ? null : cards.get(0);
    }

    /**
     * Add a card to this collection
     * 
     * @param card
     *            the card to be added
     */
    public void add(Card card)
    {
        cards.add(card);
    }

    /**
     * Add cards to this collection
     * 
     * @param cards
     *            the cards to be added
     */
    public void add(Card[] cards)
    {
        this.cards.addAll(Arrays.asList(cards));
    }

    /**
     * Add cards to this collection
     * 
     * @param cards
     *            the cards to be added
     */
    public void add(List<Card> cards)
    {
        this.cards.addAll(cards);
    }

    /**
     * Get a card based on an index
     * 
     * @param index
     *            the index of the card
     * @return the card at the index (or null)
     */
    public Card get(int index)
    {
        return cards.size() > index ? cards.get(index) : null;
    }

    /**
     * Get all cards as an new array
     * 
     * @return the cards
     */
    public Card[] getAll()
    {
        return cards.toArray(new Card[0]);
    }

    /**
     * Get all cards as an new array
     * 
     * @return the cards
     */
    public UUID[] getAllIds()
    {
        List<UUID> ids = new ArrayList<UUID>();
        for (Card card : cards)
            ids.add(card.getId());
        return ids.toArray(new UUID[0]);
    }

    /**
     * Remove a card from this collection
     * 
     * @param card the card to remove
     */
    public void remove(Card card)
    {
        cards.remove(card);
    }

    /**
     * Clear all crads from this collection
     */
    public void clear()
    {
        cards.clear();
    }

    /**
     * Check if the collection contains a specific card
     * 
     * @param card
     *            the card to check
     * @return if card is in this collection
     */
    public boolean contains(Card card)
    {
        return cards.contains(card);
    }

    /**
     * Check if the collection contains a specific card
     * 
     * @param id
     *            the id to check
     * @return if card is in this collection
     */
    public boolean contains(UUID id)
    {
        for (Card card : cards)
            if (card.getId().equals(id))
                return true;

        return false;
    }

    /**
     * Get the amount of cards in this collection
     * 
     * @return amount of cards
     */
    public int size()
    {
        return cards.size();
    }

    /**
     * Equals if id is same
     */
    @Override
    public boolean equals(Object other)
    {
        return other != null && other instanceof CardCollection && ((CardCollection) other).id.equals(id);
    }

    /**
     * id.hashCode() + 46435
     */
    @Override
    public int hashCode()
    {
        return id.hashCode() + 46435;
    }
}
