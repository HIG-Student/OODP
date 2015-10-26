package se.hig.oodp.b9.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;

/**
 * A collection of cards
 */
public class CardDeck
{
    HashMap<UUID, Card> idToCard;
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

    public List<Card> getCards()
    {
        return cards;
    }

    public UUID[] getDeckUUIDs()
    {
        List<UUID> result = new ArrayList<UUID>();
        for (Card card : cards)
            result.add(card.getId());
        return result.toArray(new UUID[0]);
    }

    public Card getCard(UUID id)
    {
        return idToCard.containsKey(id) ? idToCard.get(id) : null;
    }
}
