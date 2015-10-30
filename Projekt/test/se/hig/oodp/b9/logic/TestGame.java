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
     * @throws Exception if fails
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
            clientGame.get(i).getNetworker().onMove.add(action -> System.out.println("onMove: " + action));
            clientGame.get(i).getNetworker().onMoveResult.add(action -> System.out.println("onResult: " + action));
        }

        serverGame.rules = new Rules();
        serverGame.newGame();
    }

    /**
     * Clean up
     * @throws Exception if stuff fails
     */
    @After
    public void tearDown() throws Exception
    {
        serverGame.kill();
    }

    /**
     * Test a give
     * @throws InterruptedException if interrupted
     */
    @Test
    // (timeout = 5000)
    public void testGive() throws InterruptedException
    {
        for (int turn = 0; turn < 4; turn++)
        {
            for (ClientGame game : clientGame)
            {
                game.makeMoveAndWait(new Move(game.getMyHand()[0]));
            }
        }

        assertTrue("Hand not empty!", clientGame.get(0).getMyHand().length == 0);

        clientGame.get(0).onTurnStatus.waitFor();

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().length == 4);
    }

    /**
     * Test a game with only throws
     * @throws InterruptedException if interrupted
     */
    @Test
    // (timeout = 5000)
    public void testThrowGame() throws InterruptedException
    {
        for (int turn = 0; turn < 4; turn++)
        {
            for (ClientGame game : clientGame)
            {
                game.makeMoveAndWait(new Move(game.getMyHand()[0]));
            }
        }

        assertTrue("Hand not empty!", clientGame.get(0).getMyHand().length == 0);

        clientGame.get(0).onTurnStatus.waitFor();
        
        assertTrue("Hand not full!", clientGame.get(0).getMyHand().length == 4);
    }
}
