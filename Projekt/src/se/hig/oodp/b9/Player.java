package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The representation of a player
 */
public class Player
{
    public List<Card> hand = new ArrayList<Card>();

    /**
     * A random and unique id to represent this player
     */
    UUID id;

    public UUID getId()
    {
        return id;
    }

    /**
     * Name of the player
     */
    String name;

    public String getName()
    {
        return name;
    }

    /**
     * Counts this player's cards
     * 
     * @return the amount of cards this player have
     */
    public int getCardCount()
    {
        return hand.size();
    }
    
    public Player(String name)
    {
        id = UUID.randomUUID();
        this.name = name;
    }
}
