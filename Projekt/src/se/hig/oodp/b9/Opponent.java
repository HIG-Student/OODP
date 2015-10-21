/**
 * 
 */
package se.hig.oodp.b9;

/**
 * The representation of a player
 */
public class Opponent extends AbstractPlayer
{
    /**
     * The amount of cards this player have
     */
    int possesedCards = 0;

    @Override
    public int getCardCount()
    {
        return possesedCards;
    }
}
