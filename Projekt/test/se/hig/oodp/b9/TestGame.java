package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.b9.client.ClientGame;
import se.hig.oodp.b9.client.ClientNetworkerSocket;
import se.hig.oodp.b9.client.GameWindow;
import se.hig.oodp.b9.server.ServerGame;
import se.hig.oodp.b9.server.ServerNetworkerSocket;

public class TestGame
{
    public ServerGame serverGame;
    public List<ClientGame> clientGame;

    @Before
    public void setUp() throws Exception
    {
        int port = 59440;

        serverGame = new ServerGame(new ServerNetworkerSocket(port));

        clientGame = new ArrayList<ClientGame>();
        for (int i = 0; i < 4; i++)
            clientGame.add(new ClientGame(new Player("Player " + i), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting());

        for (int i = 0; i < 4; i++)
            serverGame.playerAdded.waitFor();

        serverGame.rules = new Rules();
        serverGame.newGame();
    }

    @Test
    public void testFoldPlay()
    {
        new GameWindow(clientGame.get(0));
    }
}
