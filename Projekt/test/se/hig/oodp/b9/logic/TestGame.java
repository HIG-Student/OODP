package se.hig.oodp.b9.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Rules;
import se.hig.oodp.b9.logic.client.ClientGame;
import se.hig.oodp.b9.logic.client.ClientNetworkerSocket;
import se.hig.oodp.b9.logic.server.ServerGame;
import se.hig.oodp.b9.logic.server.ServerNetworkerSocket;
import se.hig.oodp.b9.model.Player;

/**
 * Test for the game
 */
public class TestGame
{
    /**
     * Server game
     */
    public static ServerGame serverGame;
    /**
     * List of clients
     */
    public static List<ClientGame> clientGame;

    /**
     * Set up a server and clients
     */
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

    /**
     * Clean up
     */
    @After
    public void tearDown() throws Exception
    {
        serverGame.kill();
    }

    /**
     * Test a give
     */
    @Test
    // (timeout = 5000)
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

        clientGame.get(0).onTurnStatus.waitFor();

        System.out.println("!");

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().size() == 4);

        System.out.println("!");
    }

    /**
     * Test a game with only throws
     */
    @Test
    // (timeout = 5000)
    public void testThrowGame() throws InterruptedException
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

        clientGame.get(0).onTurnStatus.waitFor();

        System.out.println("!");

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().size() == 4);

        System.out.println("!");
    }
}
