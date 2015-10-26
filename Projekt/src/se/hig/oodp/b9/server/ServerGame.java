package se.hig.oodp.b9.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;

public class ServerGame
{
    public ServerNetworker networker;

    public List<Player> players = new ArrayList<Player>();

    public CardDeck cardDeck;
    public Table table;

    public boolean running = false;

    public ServerGame(ServerNetworker networker) // rules?
    {
        this.networker = networker;

        networker.onPlayerConnecting.add(newPlayer ->
        {
            if (running)
            {
                networker.closeConnection(newPlayer, "The game have already begun!");
                return;
            }

            if (players.size() >= 4)
            {
                networker.closeConnection(newPlayer, "Max players reached!");
                return;
            }

            for (Player exsistingPlayer : players)
                if (newPlayer.getName().toLowerCase().equals(exsistingPlayer.getName().toLowerCase()))
                {
                    networker.closeConnection(newPlayer, "Name taken!");
                    return;
                }

            players.add(newPlayer);

            for (Player player : players)
                if (player != newPlayer)
                    networker.sendMessageTo(player, "Player [" + player.getName() + "] have joined the game!");

            networker.sendMessageTo(newPlayer, "You have joined a game!");
            networker.sendMessageTo(newPlayer, "Players in the game:");
            for (Player player : players)
                if (player != newPlayer)
                    networker.sendMessageTo(newPlayer, "\t" + player.getName());
        });

        networker.onNewMessage.add(message ->
        {
            System.out.println("Player [" + message.one + "] says: " + message.two);
            networker.sendMessageToAll(message.one, message.two);
        });
    }

    public void newGame()
    {
        cardDeck = new CardDeck();
        table = new Table(players, UUID.randomUUID(), UUID.randomUUID())
        {
            @Override
            public void moveCard(Card card, CardCollection collection)
            {
                super.moveCard(card, collection);
                networker.sendMoveCard(card, collection);
            }
        };
        table.addDeck(cardDeck.getDeckUUIDs());

        networker.sendTable(table);
        networker.sendNewCards(cardDeck.getDeckUUIDs());
    }

    public void startGame()
    {
        if (running)
        {
            // clear?
        }
        running = true;

        networker.sendMessageToAll("The game is starting!");
        newGame();
    }
}
