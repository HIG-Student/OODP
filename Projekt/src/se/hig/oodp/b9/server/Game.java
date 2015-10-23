package se.hig.oodp.b9.server;

import java.util.ArrayList;
import java.util.List;

import se.hig.oodp.b9.Player;

public class Game
{
    public List<Player> players = new ArrayList<Player>();

    public Player getPlayer(String playerName) throws Exception
    {
        if (players.size() >= 4)
            throw new Exception("Max players reaced");

        for (Player player : players)
            if (player.getName().toLowerCase().equals(playerName.toLowerCase()))
                throw new Exception("Name taken");

        Player player = new Player(playerName);
        players.add(player);
        return player;
    }
}
