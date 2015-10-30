package se.hig.oodp.b9.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.Player;

/**
 * The table containing all info related to the cards and players
 */
@SuppressWarnings("serial")
public class Table implements Serializable
{
    /**
     * The players at this table
     */
    List<Player> players;

    /**
     * The index of the player that is next to make a move
     */
    int nextPlayerIndex = 0;

    /**
     * Get the next player to make a move
     * 
     * @return the next player
     */
    public Player getNextPlayer()
    {
        return players.get(nextPlayerIndex);
    }

    /**
     * Mapping to the players' hands
     */
    HashMap<Player, CardCollection> playerHands = new HashMap<Player, CardCollection>();
    /**
     * Mapping to the players' points (the cards the player can get points for)
     */
    HashMap<Player, CardCollection> playerPoints = new HashMap<Player, CardCollection>();
    /**
     * The pool with cards on the table
     */
    CardCollection pool;
    /**
     * The deck with yet unplayed cards
     */
    CardCollection deck;

    /**
     * Mapping with all cards
     */
    HashMap<UUID, Card> cards = new HashMap<UUID, Card>();
    /**
     * Mapping with all collections
     */
    HashMap<UUID, CardCollection> collections = new HashMap<UUID, CardCollection>();

    /**
     * Mapping for the collection a card is in
     */
    HashMap<Card, CardCollection> cardLocation = new HashMap<Card, CardCollection>();

    /**
     * The id for the pool
     */
    public final UUID poolUUID;
    /**
     * The id for the deck
     */
    public final UUID deckUUID;

    /**
     * Event invoked on a card move
     */
    public transient final Event<Two<Card, CardCollection>> onCardMove = new Event<Two<Card, CardCollection>>();

    /**
     * Create new table
     * 
     * @param arrayList
     *            list with players
     * @param poolUUID
     *            the pool id
     * @param deckUUID
     *            the deck id
     */
    public Table(List<Player> arrayList, UUID poolUUID, UUID deckUUID)
    {
        this.players = arrayList;
        for (Player player : arrayList)
        {
            CardCollection playerHand = new CardCollection(player.handUUID, player);
            playerHands.put(player, playerHand);
            collections.put(player.handUUID, playerHand);

            CardCollection playerPoint = new CardCollection(player.pointsUUID, player);
            playerPoints.put(player, playerPoint);
            collections.put(player.pointsUUID, playerPoint);
        }

        collections.put(this.poolUUID = poolUUID, pool = new CardCollection(poolUUID, null));
        collections.put(this.deckUUID = deckUUID, deck = new CardCollection(deckUUID, null));
    }

    /**
     * Get the card-collection that card is in
     * 
     * @param card
     *            the card to check
     * @return the card-collection the card is in
     */
    public CardCollection getCardLocation(Card card)
    {
        return cardLocation.containsKey(card) ? cardLocation.get(card) : null;
    }

    /**
     * Get the pool
     * 
     * @return the pool
     */
    public CardCollection getPool()
    {
        return pool;
    }

    /**
     * Get the deck
     * 
     * @return the deck
     */
    public CardCollection getDeck()
    {
        return deck;
    }

    /**
     * Get the card-collection that is the player's hand
     * 
     * @param player
     *            the owning player
     * @return the hand of the player
     */
    public CardCollection getPlayerHand(Player player)
    {
        return playerHands.containsKey(player) ? playerHands.get(player) : null;
    }

    /**
     * Get the card-collection that is the player's points (the cards the player
     * can get points for)
     * 
     * @param player
     *            the owning player
     * @return the points of the player (the cards the player can get points
     *         for)
     */
    public CardCollection getPlayerPoints(Player player)
    {
        return playerPoints.containsKey(player) ? playerPoints.get(player) : null;
    }

    /**
     * Get all players
     * 
     * @return the players
     */
    public Player[] getPlayers()
    {
        return players.toArray(new Player[0]);
    }

    /**
     * Get the index of a player
     * 
     * @param player
     *            the player
     * @return the index
     */
    public int getPlayerIndex(Player player)
    {
        return players.indexOf(player);
    }

    /**
     * Get card from id
     * 
     * @param id
     *            the id
     * @return the card
     */
    public Card getCard(UUID id)
    {
        return cards.containsKey(id) ? cards.get(id) : null;
    }

    /**
     * Get card-collection from id
     * 
     * @param id
     *            the id
     * @return the card-collection
     */
    public CardCollection getCardCollection(UUID id)
    {
        return collections.containsKey(id) ? collections.get(id) : null;
    }

    /**
     * Check if the deck got any cards left
     * 
     * @return if the deck got any cards left
     */
    public boolean deckGotCards()
    {
        return deck.size() != 0;
    }

    /**
     * Get the topmost card from the deck
     * 
     * @return the topmost card
     */
    public Card getCardFromDeck()
    {
        if (deck.size() == 0)
            return null;

        return deck.get(0);
    }

    /**
     * Move a card to a new destination
     * 
     * @param card
     *            the card to move
     * @param collection
     *            the destination
     */
    public void moveCard(Card card, CardCollection collection)
    {
        if (card == null || collection == null)
        {
            System.out.println("Trying to move null!");
            return;
        }

        if (cardLocation.containsKey(card))
            cardLocation.get(card).remove(card);
        cardLocation.put(card, collection);
        collection.add(card);

        onCardMove.invoke(new Two<Card, CardCollection>(card, collection));
    }

    /**
     * Swaps the old cards with new ones
     * 
     * @param newCards
     *            the new cards
     */
    public void changeDeck(Card[] newCards)
    {
        clear();

        for (Card card : newCards)
        {
            cards.put(card.getId(), card);
            cardLocation.put(card, deck);
            deck.add(card);
        }
    }

    /**
     * Indicates that it is the next player's turn
     */
    public void nextTurn()
    {
        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
    }

    /**
     * Allows player at {@link #nextPlayerIndex index} 0 to start
     */
    public void resetNextTurn()
    {
        nextPlayerIndex = 0;
    }

    /**
     * Clear information in this table (related the cards)
     */
    public void clear()
    {
        for (Player player : players)
        {
            playerHands.get(player).clear();
            playerPoints.get(player).clear();
        }
        pool.clear();
        deck.clear();
        cards.clear();
        cardLocation.clear();
    }

    /**
     * Method to override the standard functionality of the serialization's
     * deserialization <br>
     * <br>
     * Used to set the {@link #onCardMove final field} to a new value on
     * deserialization <br>
     * <br>
     * 
     * To be considered ugly hax (however, it is run before anything tries to
     * access it, so we should be fine)<br>
     * <br>
     * <br>
     * 
     * Inspiration from:
     * <ul>
     * <li><a href=
     * "http://thecodersbreakfast.net/index.php?post/2012/03/06/Setting-final-fields-like-a-boss"
     * >Olivier Croisier</a></li>
     * <li><a href= "https://dzone.com/articles/understanding-sunmiscunsafe" >
     * Rafael Winterhalter</a></li>
     * </ul>
     */
    @SuppressWarnings("all")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        stream.defaultReadObject();

        Field field = Table.class.getDeclaredField("onCardMove");
        field.setAccessible(true);
        field.set(this, new Event<Two<Card, CardCollection>>());
    }
}
