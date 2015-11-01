package se.hig.oodp.b9.logic;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

@SuppressWarnings("javadoc")
public class TestCardCollection
{
    UUID id;
    Player owner;
    CardCollection collection;

    Card[] cards = new Card[]
    {
            new Card(CardInfo.getRandom()),
            new Card(CardInfo.getRandom()),
            new Card(CardInfo.getRandom()),
            new Card(CardInfo.getRandom())
    };

    @Before
    public void setUp()
    {
        id = UUID.randomUUID();
        owner = new Player("Test");
        collection = new CardCollection(id, owner);
        collection.add(cards);
    }

    @Test
    public void testInit()
    {
        assertTrue("Owner not set", collection.owner == owner);
        assertTrue("Id not set", collection.getId() == id);

        for (Card card : cards)
            assertTrue("Card not added", collection.contains(card));

        for (Card card : cards)
            assertTrue("Card-id not mapped", collection.contains(card.getId()));
    }

    @Test
    public void testRemove()
    {
        Card r = cards[0];

        collection.remove(r);

        assertFalse("Card not removed", collection.contains(r));

        assertFalse("Card not removed", collection.contains(r.getId()));
    }

    @Test
    public void testClear()
    {
        collection.clear();

        assertTrue("Cards not cleared", collection.size() == 0);
    }
}
