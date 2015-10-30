package se.hig.oodp.b9.logic;

import java.io.Serializable;

import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Move;
import se.hig.oodp.b9.model.Player;

/**
 * Rules for the game
 */
@SuppressWarnings("serial")
public class Rules implements Serializable
{
    /**
     * Rules for how to set up the game
     * 
     * @param table
     *            the table this rule is affecting
     */
    public void setUp(Table table)
    {
        give(table);

        for (int i = 0; i < 4; i++)
            if (table.deckGotCards())
                table.moveCard(table.getCardFromDeck(), table.getPool());
    }

    /**
     * Rules for how to do a give
     * 
     * @param table
     *            the table this rule is affecting
     */
    public void give(Table table)
    {
        for (Player player : table.getPlayers())
            for (int i = 0; i < 4; i++)
                if (table.deckGotCards())
                    table.moveCard(table.getCardFromDeck(), table.getPlayerHand(player));
    }

    /**
     * Rules for how you can move cards
     * 
     * @param player
     *            the player trying to do a move
     * @param table
     *            the table this rule is affecting
     * @param move
     *            the move the player is trying to do
     * @return if this move is valid
     * @see Move
     */
    public boolean canPlayMove(Player player, Table table, Move move)
    {
        if (player != table.getNextPlayer())
            return false;

        if (!table.getPlayerHand(player).contains(move.getActiveCard()))
            return false;

        Card activeCard = move.getActiveCard();
        Card[][] takeCards = new Card[0][0];

        takeCards = move.getTakeCards();

        if (takeCards.length == 0)
        {
            return true;
        }
        else
        {
            for (Card[] cardList : takeCards)
                for (Card card : cardList)
                    if (!table.getPool().contains(card))
                        return false;

            for (Card[] cardList : takeCards)
            {
                if (activeCard.getCardInfo().getValue() == CardInfo.Value.Ess && cardList.length == 1 && cardList[0].getCardInfo().getValue() == CardInfo.Value.Ess)
                    continue;

                // Hand can only be ESS : 14 now!
                // Table can only have ESS : 1

                int handValue = activeCard.getCardInfo().getValue() == CardInfo.Value.Ess ? 14 : activeCard.getCardInfo().getValue().ordinal() + 1;
                int takeValue = 0;
                for (Card card : cardList)
                    takeValue += card.getCardInfo().getValue().ordinal() + 1;

                if (handValue != takeValue)
                    return false;
            }

            return true;
        }
    }
}
