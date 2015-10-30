package se.hig.oodp.b9.logic.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.CardInfo;

/**
 * A collection of cards
 */
public class CardDeck
{
    /**
     * HashMap that maps the card-id to the card
     */
    HashMap<UUID, Card> idToCard;

    /**
     * The cards in this deck
     */
    @SuppressWarnings("serial")
    final List<Card> cards = new ArrayList<Card>()
    {
        {
            idToCard = new HashMap<UUID, Card>();
            Card card;

            for (CardInfo.Type type : CardInfo.Type.values())
                for (CardInfo.Value value : CardInfo.Value.values())
                {
                    add(card = new Card(new CardInfo(type, value)));
                    idToCard.put(card.getId(), card);
                }
        }
    };

    /**
     * Construct a deck
     */
    public CardDeck()
    {
        shuffle();
    }

    /**
     * Shuffles the cards in the collection
     */
    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    /**
     * Get the cards
     * 
     * @return the cards as an array
     */
    public Card[] getCards()
    {
        return cards.toArray(new Card[0]);
    }

    /**
     * Get card from id
     * 
     * @param id
     *            of the card
     * @return the card
     */
    public Card getCard(UUID id)
    {
        return idToCard.containsKey(id) ? idToCard.get(id) : null;
    }
}
