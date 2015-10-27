package se.hig.oodp.b9;

import java.io.Serializable;

public class Rules implements Serializable
{
    public void setUp(Table table)
    {
        for (Player player : table.players)
            for (int i = 0; i < 4; i++)
                table.moveCard(table.getCardFromDeck(), table.playerHands.get(player));
    }

    public boolean tryPlayMove(Player player, Table table, Move move)
    {
        if (player != table.nextPlayer)
            return false;

        if (!table.playerHands.get(player).contains(move.activeCard))
            return false;

        if (move.takeCards == null)
        {
            table.moveCard(move.activeCard, table.pool);
            return true;
        }
        else
        {
            for (Card card : move.takeCards)
                if (!table.pool.contains(card))
                    return false;
            
            //TODO: count
            
            return false;
        }
    }

    public class Move implements Serializable
    {
        public Card activeCard;
        public Card[] takeCards;

        public Move(Card activeCard, Card[] takeCards)
        {
            this.activeCard = activeCard;
            this.takeCards = takeCards;
        }
    }
}
