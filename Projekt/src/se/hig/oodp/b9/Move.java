package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Move implements Serializable
{
    public Card activeCard;
    public List<Card> takeCards;

    public Move(Card activeCard, ArrayList<Card> takeCards)
    {
        this.activeCard = activeCard;
        this.takeCards = takeCards;
    }

    public void makeLocal(Table table)
    {
        activeCard = table.cards.get(activeCard.getId());
        takeCards.replaceAll(card -> table.cards.get(card.getId()));
    }
}