package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Move implements Serializable
{
    private UUID activeCardId;
    private UUID[][] takeCardIds;

    private transient Card activeCard;
    private transient List<List<Card>> takeCards = new ArrayList<List<Card>>();

    private transient List<Card> currentTake = new ArrayList<Card>();

    public Card getActiveCard()
    {
        return activeCard;
    }

    public Card[][] getTakeCards()
    {
        Card[][] cards = new Card[takeCards.size()][];
        
        for(int i = 0;i < takeCards.size();i++)
            cards[i] = takeCards.get(i).toArray(new Card[0]);
        
        return cards;
    }
    
    public Card[] getCurrentTakeCards()
    {
        return currentTake.toArray(new Card[0]);
    }

    public Move(Card activeCard, List<List<Card>> takeCards)
    {
        setActiveCard(activeCard);

        this.takeCards = takeCards;
        updateTakeIds();
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
        currentTake.clear();
        updateTakeIds();
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

    public void clearTake()
    {
        takeCards = new ArrayList<List<Card>>();
        currentTake = new ArrayList<Card>();

        updateTakeIds();
    }

    public void addTake(Card card)
    {
        if (!takeCards.contains(card))
        {
            currentTake.add(card);
            
            updateTakeIds();
        }
    }

    public void removeTake(Card card)
    {
        if (currentTake.contains(card))
        {
            currentTake.remove(card);
            
            updateTakeIds();
        }
    }

    public void toggleTake(Card card)
    {
        if (currentTake.contains(card))
            removeTake(card);
        else
            addTake(card);
    }

    public boolean takeContains(Card card)
    {
        for (List<Card> list : takeCards)
            if (list.contains(card))
                return true;

        return false;
    }
    
    public boolean currentTakeContains(Card card)
    {
        return currentTake.contains(card);
    }

    public void nextTake()
    {
        takeCards.add(currentTake);
        currentTake = new ArrayList<Card>();

        updateTakeIds();
    }

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