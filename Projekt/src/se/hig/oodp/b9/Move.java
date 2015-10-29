package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Move implements Serializable
{
    private UUID activeCardId;
    private List<UUID> takeCardIds = new ArrayList<UUID>();

    private transient Card activeCard;
    private transient List<Card> takeCards = new ArrayList<Card>();

    public Card getActiveCard()
    {
        return activeCard;
    }

    public Card[] getTakeCards()
    {
        return takeCards.toArray(new Card[0]);
    }

    public Move(Card activeCard, ArrayList<Card> takeCards)
    {
        setActiveCard(activeCard);

        this.takeCards = takeCards;
        this.takeCards.forEach(card -> takeCardIds.add(card.getId()));
    }

    
    public Move(Card activeCard)
    {
        setActiveCard(activeCard);
    }

    public void setActiveCard(Card card)
    {
        activeCard = card;
        activeCardId = activeCard == null ? null : activeCard.getId();

        takeCards.clear();
        takeCardIds.clear();
    }

    public void toggleActive(Card card)
    {
        if (activeCard == card)
            setActiveCard(null);
        else
            setActiveCard(card);
    }

    private void updateTakeIds()
    {
        takeCardIds.clear();
        takeCards.forEach(card -> takeCardIds.add(card.getId()));
    }

    public void clearTake()
    {
        takeCards.clear();
        updateTakeIds();
    }

    public void addTake(Card card)
    {
        if (!takeCards.contains(card))
        {
            takeCards.add(card);
            updateTakeIds();
        }
    }

    public void removeTake(Card card)
    {
        if (takeCards.contains(card))
        {
            takeCards.remove(card);
            updateTakeIds();
        }
    }

    public void toggleTake(Card card)
    {
        if (takeCards.contains(card))
            removeTake(card);
        else
            addTake(card);
    }

    public boolean takeContains(Card card)
    {
        return takeCards.contains(card);
    }

    public void populate(Table table)
    {
        if (activeCardId != null)
            activeCard = table.getCard(activeCardId);

        takeCards = new ArrayList<Card>();
        takeCardIds.forEach(cardId -> takeCards.add(table.getCard(cardId)));
    }
}