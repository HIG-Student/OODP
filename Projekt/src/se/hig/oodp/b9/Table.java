package se.hig.oodp.b9;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Table implements Serializable
{
    List<Player> players;

    int nextPlayerIndex = 0;

    public Player getNextPlayer()
    {
        return players.get(nextPlayerIndex);
    }

    HashMap<Player, CardCollection> playerHands = new HashMap<Player, CardCollection>();
    HashMap<Player, CardCollection> playerPoints = new HashMap<Player, CardCollection>();
    CardCollection pool;
    CardCollection deck;

    HashMap<UUID, Card> cards = new HashMap<UUID, Card>();
    HashMap<UUID, CardCollection> collections = new HashMap<UUID, CardCollection>();

    HashMap<Card, CardCollection> cardLocation = new HashMap<Card, CardCollection>();

    public final UUID poolUUID;
    public final UUID deckUUID;

    public transient final Event<Two<Card, CardCollection>> onCardMove = new Event<Two<Card, CardCollection>>();

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

    public CardCollection getCardLocation(Card card)
    {
        return cardLocation.containsKey(card) ? cardLocation.get(card) : null;
    }

    public CardCollection getPool()
    {
        return pool;
    }

    public CardCollection getDeck()
    {
        return deck;
    }

    public CardCollection getPlayerHand(Player player)
    {
        return playerHands.containsKey(player) ? playerHands.get(player) : null;
    }

    public CardCollection getPlayerPoints(Player player)
    {
        return playerPoints.containsKey(player) ? playerPoints.get(player) : null;
    }

    public Player[] getPlayers()
    {
        return players.toArray(new Player[0]);
    }

    public int getPlayerIndex(Player player)
    {
        return players.indexOf(player);
    }

    public Card getCard(UUID id)
    {
        return cards.containsKey(id) ? cards.get(id) : null;
    }

    public CardCollection getCardCollection(UUID id)
    {
        return collections.containsKey(id) ? collections.get(id) : null;
    }

    public boolean deckGotCards()
    {
        return deck.size() != 0;
    }

    public Card getCardFromDeck()
    {
        if (deck.size() == 0)
            return null;

        return deck.get(0);
    }

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

    public void nextTurn()
    {
        nextPlayerIndex = (nextPlayerIndex + 1) % players.size();
    }

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

    @SuppressWarnings("all")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        stream.defaultReadObject();

        // Ugly hax to set final field (however, it is run before anything tries
        // to access it, so we should be fine)
        // Inspiration from:
        //
        // http://thecodersbreakfast.net/index.php?post/2012/03/06/Setting-final-fields-like-a-boss
        // https://dzone.com/articles/understanding-sunmiscunsafe

        Field field = Table.class.getDeclaredField("onCardMove");
        field.setAccessible(true);
        field.set(this, new Event<Two<Card, CardCollection>>());
    }
}
