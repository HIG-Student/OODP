package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardCollection extends UUIDInstance
{
    List<Card> cards = new ArrayList<Card>();

    public CardCollection()
    {
        id = UUID.randomUUID();
    }

    public CardCollection(UUID id)
    {
        this.id = id;
    }

    public void add(Card card)
    {
        cards.add(card);
    }
    
    public Card get(int index)
    {
        return cards.get(index);
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
