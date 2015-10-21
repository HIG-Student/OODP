/**
 * 
 */
package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a player
 */
public class Player extends AbstractPlayer
{
    /**
     * The cards possessed by this player
     */
    public List<Card> hand = new ArrayList<Card>();

    @Override
    public int getCardCount()
    {
        return hand.size();
    }
}
