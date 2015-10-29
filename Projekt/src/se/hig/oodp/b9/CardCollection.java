package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardCollection extends UUIDInstance
{
    public final Player owner;

    List<Card> cards = new ArrayList<Card>();

    public CardCollection()
    {
        id = UUID.randomUUID();
        this.owner = null;
    }

    public CardCollection(Player owner)
    {
        id = UUID.randomUUID();
        this.owner = owner;
    }

    public CardCollection(UUID id, Player owner)
    {
        this.id = id;
        this.owner = owner;
    }

    public Card getFirstCard()
    {
        return cards.isEmpty() ? null : cards.get(0);
    }

    public void add(Card card)
    {
        cards.add(card);
    }

    public Card get(int index)
    {
        return cards.get(index);
    }

    public Card[] getAll()
    {
        return cards.toArray(new Card[0]);
    }

    public void remove(Card card)
    {
        cards.remove(card);
    }

    public void clear()
    {
        cards.clear();
    }

    public boolean contains(Card card)
    {
        return cards.contains(card);
    }

    public boolean contains(UUID id)
    {
        for (Card card : cards)
            if (card.getId().equals(id))
                return true;

        return false;
    }

    public int size()
    {
        return cards.size();
    }

    @Override
    public boolean equals(Object other)
    {
        return other != null && other instanceof CardCollection && ((CardCollection) other).id.equals(id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode() + 46435;
    }
}
