package se.hig.oodp.b9;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Table implements Serializable
{
    public List<Player> players;

    public HashMap<Player, CardCollection> playerHands = new HashMap<Player, CardCollection>();
    public HashMap<Player, CardCollection> playerPoints = new HashMap<Player, CardCollection>();
    public CardCollection pool;
    public CardCollection deck;

    public HashMap<UUID, Card> cards = new HashMap<UUID, Card>();
    public HashMap<UUID, CardCollection> collections = new HashMap<UUID, CardCollection>();

    public HashMap<Card, CardCollection> cardLocation = new HashMap<Card, CardCollection>();

    public UUID poolUUID;
    public UUID deckUUID;

    public Table(List<Player> arrayList, UUID poolUUID, UUID deckUUID)
    {
        this.players = arrayList;
        for (Player player : arrayList)
        {
            CardCollection playerHand = new CardCollection(player.handUUID);
            playerHands.put(player, playerHand);
            collections.put(player.handUUID, playerHand);
            
            CardCollection playerPoint = new CardCollection(player.pointsUUID);
            playerPoints.put(player, playerPoint);
            collections.put(player.pointsUUID, playerPoint);
        }

        collections.put(this.poolUUID = poolUUID, pool = new CardCollection(poolUUID));
        collections.put(this.deckUUID = deckUUID, deck = new CardCollection(deckUUID));
    }

    public void moveCard(Card card, CardCollection collection)
    {
        if (cardLocation.containsKey(card))
            cardLocation.get(card).remove(card);
        cardLocation.put(card, collection);
        collection.add(card);
    }

    public void changeDeck(UUID[] UUIDs)
    {
        clear();

        for (UUID uuid : UUIDs)
        {
            Card card = new Card(uuid);
            cards.put(uuid, card);
            cardLocation.put(card, deck);
            deck.add(card);
        }
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
}
