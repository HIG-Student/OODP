package se.hig.oodp.b9.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.Player;

public class ClientGame
{
    public List<Player> players = new ArrayList<Player>();
    public HashMap<UUID, Card> cardList = new HashMap<UUID, Card>();
    public HashMap<UUID, Player> cardHolderList = new HashMap<UUID, Player>();

    public void attachCardInfo(UUID id, CardInfo info)
    {
        cardList.get(id).setCardInfo(info);
        // update drawing
    }

    public void updateCardHolder(UUID id, Player player)
    {
        cardHolderList.put(id, player);
        // update drawing
    }
}
