package se.hig.oodp.b9;

import java.util.Collections;
import java.util.Stack;

/**
 * A collection of cards
 */
public class CardDeck
{
    /**
     * The stack with cards
     */
    Stack<Card> cards = new Stack<Card>();

    /**
     * Construct a deck
     */
    public CardDeck()
    {
        for (Card.Type type : Card.Type.values())
            for (Card.Value value : Card.Value.values())
                cards.push(new Card(type, value));
    }

    /**
     * Shuffles the cards in the collection
     */
    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    /**
     * Checks if you can draw a card
     * 
     * @return whether the collection have cards or not
     */
    public boolean canDraw()
    {
        return !cards.isEmpty();
    }

    /**
     * Draw the top-most card from the collection
     * 
     * @return the top-most card, or null
     */
    public Card draw()
    {
        return canDraw() ? cards.pop() : null;
    }
}
