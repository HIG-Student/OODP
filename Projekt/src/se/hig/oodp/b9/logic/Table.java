package se.hig.oodp.b9.logic;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.CardInfo;
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
    public transient final Event<Two<UUID, UUID>> onCardMove = new Event<Two<UUID, UUID>>();

    /**
     * Create new table
     * 
     * @param players
     *            list with players
     * @param poolUUID
     *            the pool id
     * @param deckUUID
     *            the deck id
     */
    public Table(List<Player> players, UUID poolUUID, UUID deckUUID)
    {
        this.players = players;
        this.poolUUID = poolUUID;
        this.deckUUID = deckUUID;

        setUp();
    }

    /**
     * Sets up this table
     */
    protected void setUp()
    {
        for (Player player : players)
        {
            CardCollection playerHand = new CardCollection(player.handUUID, player);
            playerHands.put(player, playerHand);
            collections.put(player.handUUID, playerHand);

            CardCollection playerPoint = new CardCollection(player.pointsUUID, player);
            playerPoints.put(player, playerPoint);
            collections.put(player.pointsUUID, playerPoint);
        }

        collections.put(poolUUID, pool = new CardCollection(poolUUID, null));
        collections.put(deckUUID, deck = new CardCollection(deckUUID, null));

        List<Card> cardList = new ArrayList<Card>();

        for (CardInfo.Type type : CardInfo.Type.values())
            for (CardInfo.Value value : CardInfo.Value.values())
            {
                Card card = new Card(new CardInfo(type, value));
                cards.put(card.getId(), card);
                cardList.add(card);
            }

        Collections.shuffle(cardList);

        for (Card card : cardList)
        {
            cardLocation.put(card, deck);
            deck.add(card);
        }
    }

    /**
     * Get the card-collection that card is in
     * 
     * @param card
     *            the card to check
     * @return the card-collection the card is in
     */
    CardCollection getCardLocation(Card card)
    {
        return cardLocation.containsKey(card) ? cardLocation.get(card) : null;
    }

    /**
     * Get the card-collection that card is in
     * 
     * @param card
     *            the card to check
     * @return the card-collection the card is in
     */
    public UUID getCardLocation(UUID card)
    {
        if (!cards.containsKey(card))
            return null;

        if (!cardLocation.containsKey(cards.get(card)))
            return null;

        return cardLocation.get(cards.get(card)).getId();
    }

    /**
     * Get the pool
     * 
     * @return the pool
     */
    Card[] getPool()
    {
        return pool.getAll();
    }

    /**
     * Get the pool
     * 
     * @return the pool
     */
    public UUID[] getPoolIds()
    {
        return pool.getAllIds();
    }

    /**
     * Get card info
     * 
     * @param card
     *            the card
     * @return the info
     */
    public CardInfo getCardInfo(UUID card)
    {
        return cards.containsKey(card) ? cards.get(card).getCardInfo() : null;
    }

    /**
     * Get the deck
     * 
     * @return the deck
     */
    Card[] getDeck()
    {
        return deck.getAll();
    }

    /**
     * Get the deck
     * 
     * @return the deck
     */
    public UUID[] getDeckIds()
    {
        return deck.getAllIds();
    }

    /**
     * Get the card-collection that is the player's hand
     * 
     * @param player
     *            the owning player
     * @return the hand of the player
     */
    Card[] getPlayerHand(Player player)
    {
        return playerHands.containsKey(player) ? playerHands.get(player).getAll() : new Card[0];
    }

    /**
     * Get the card-collection that is the player's hand
     * 
     * @param player
     *            the owning player
     * @return the hand of the player
     */
    public UUID[] getPlayerHandIds(Player player)
    {
        return playerHands.containsKey(player) ? playerHands.get(player).getAllIds() : new UUID[0];
    }

    /**
     * Get id of player hand
     * 
     * @param player
     *            the player
     * @return the hand
     */
    public UUID getIdOfPlayerHand(Player player)
    {
        return playerHands.containsKey(player) ? playerHands.get(player).getId() : null;
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
    Card[] getPlayerPoints(Player player)
    {
        return playerPoints.containsKey(player) ? playerPoints.get(player).getAll() : new Card[0];
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
    public UUID[] getPlayerPointIds(Player player)
    {
        return playerPoints.containsKey(player) ? playerPoints.get(player).getAllIds() : new UUID[0];
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
    Card getCard(UUID id)
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
    public UUID[] getCardCollection(UUID id)
    {
        return collections.containsKey(id) ? collections.get(id).getAllIds() : new UUID[0];
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
    public UUID getCardFromDeck()
    {
        if (deck.size() == 0)
            return null;

        return deck.get(0).getId();
    }

    /**
     * Get owner of id
     * 
     * @param collectionId
     *            id of collection
     * @return the owner
     */
    public Player getOwnerOfCollectionId(UUID collectionId)
    {
        if (!collections.containsKey(collectionId))
            return null;

        return collections.get(collectionId).owner;
    }

    /**
     * Move a card to a new destination
     * 
     * @param card
     *            the card to move
     * @param collection
     *            the destination
     */
    void moveCard(Card card, CardCollection collection)
    {
        if (card == null || collection == null || !cards.containsValue(card) || !collections.containsValue(collection))
        {
            System.out.println("Trying to move null!");
            return;
        }

        if (cardLocation.containsKey(card))
            cardLocation.get(card).remove(card);
        cardLocation.put(card, collection);
        collection.add(card);

        onCardMove.invoke(new Two<UUID, UUID>(card.getId(), collection.getId()));
    }

    /**
     * Move a card to a new destination
     * 
     * @param cardId
     *            the card to move
     * @param collectionId
     *            the destination
     */
    public void moveCard(UUID cardId, UUID collectionId)
    {
        if (!cards.containsKey(cardId))
            return;

        if (!collections.containsKey(collectionId))
            return;

        moveCard(cards.get(cardId), collections.get(collectionId));
    }

    /**
     * Get owner of card
     * 
     * @param cardId
     *            the card
     * @return the owner
     */
    public Player getOwnerOfCard(UUID cardId)
    {
        if (!cards.containsKey(cardId))
            return null;

        if (!cardLocation.containsKey(cards.get(cardId)))
            return null;

        return cardLocation.get(cards.get(cardId)).owner;
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
     * 
     * @param stream
     *            the stream
     * @throws Exception
     *             if fails
     */
    private void readObject(ObjectInputStream stream) throws Exception
    {
        stream.defaultReadObject();

        Field field = Table.class.getDeclaredField("onCardMove");
        field.setAccessible(true);
        field.set(this, new Event<Two<Card, CardCollection>>());
    }
}
