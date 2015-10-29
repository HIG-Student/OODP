package se.hig.oodp.b9;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.b9.client.ClientGame;
import se.hig.oodp.b9.client.ClientNetworkerSocket;
import se.hig.oodp.b9.server.ServerGame;
import se.hig.oodp.b9.server.ServerNetworkerSocket;

public class TestGame
{
    public static ServerGame serverGame;
    public static List<ClientGame> clientGame;

    @Before
    public void setUp() throws Exception
    {
        int port = 59440;

        serverGame = new ServerGame(new ServerNetworkerSocket(port));

        clientGame = new ArrayList<ClientGame>();
        for (int i = 0; i < 4; i++)
        {
            clientGame.add(new ClientGame(new Player("Player " + i), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting());
            serverGame.playerAdded.waitFor();
        }

        serverGame.rules = new Rules();
        serverGame.newGame();
    }
    
    @After
    public void tearDown() throws Exception
    {
        serverGame.kill();
    }
    

    @Test//(timeout = 5000)
    public void testGive() throws InterruptedException
    {
        for (int turn = 0; turn < 4; turn++)
        {
            for (ClientGame game : clientGame)
            {
                game.makeMoveAndWait(new Move(game.getMyHand().getFirstCard()));
            }
        }
        
        System.out.println("!");

        assertTrue("Hand not empty!", clientGame.get(0).getMyHand().size() == 0);

        clientGame.get(0).turnStatus.waitFor();
        
        System.out.println("!");

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().size() == 4);
        
        System.out.println("!");
    }

    @Test//(timeout = 5000)
    public void testFoldGame() throws InterruptedException
    {
        for (int turn = 0; turn < 4; turn++)
        {
            for (ClientGame game : clientGame)
            {
                game.makeMoveAndWait(new Move(game.getMyHand().getFirstCard()));
            }
        }
        
        System.out.println("!");

        assertTrue("Hand not empty!", clientGame.get(0).getMyHand().size() == 0);

        clientGame.get(0).turnStatus.waitFor();
        
        System.out.println("!");

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().size() == 4);
        
        System.out.println("!");
    }
}
