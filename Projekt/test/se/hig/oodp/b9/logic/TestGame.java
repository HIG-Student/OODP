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

@SuppressWarnings("javadoc")
public class TestGame
{

    public static ServerGame serverGame;

    public static List<ClientGame> clientGame;

    @Before
    public void setUp() throws Exception
    {
        int port = 59440;

        serverGame = new ServerGame(new ServerNetworkerSocket(port), new Rules());

        clientGame = new ArrayList<ClientGame>();
        for (int i = 0; i < 4; i++)
        {
            clientGame.add(new ClientGame(new Player("Player " + i), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting());
            serverGame.playerAdded.waitFor();
            clientGame.get(i).getNetworker().onMove.add(action -> System.out.println("onMove: " + action));
            clientGame.get(i).getNetworker().onMoveResult.add(action -> System.out.println("onResult: " + action));
        }

        serverGame.newGame();
    }

    @After
    public void tearDown() throws Exception
    {
        serverGame.kill();
    }

    /*
     * @Test(timeout = 5000) public void testGive() throws InterruptedException
     * { for (int turn = 0; turn < 4; turn++) { for (ClientGame game :
     * clientGame) { game.makeMoveAndWait(new Move(game.getMyHand()[0])); } }
     * 
     * assertTrue("Hand not empty!", clientGame.get(0).getMyHand().length == 0);
     * 
     * clientGame.get(0).onTurnStatus.waitFor();
     * 
     * assertTrue("Hand not full!", clientGame.get(0).getMyHand().length == 4);
     * }
     */

    @Test
    public void testThrowGame() throws InterruptedException
    {
        clientGame.get(0).onEndGame.add(res ->
        {
            System.out.println("!");
        });

        for (int turn = 0; turn < 4; turn++)
        {
            for (ClientGame game : clientGame)
            {
                game.makeMoveAndWait(new Move(game.getMyHand()[0]));
                assertEquals("Incorrect hand!", 4 - (turn + 1), game.getMyHand().length);
            }
        }

        clientGame.get(0).onTurnStatus.waitFor();

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().length == 4);

        for (int turn = 0; turn < 4; turn++)
        {
            for (ClientGame game : clientGame)
            {
                game.makeMoveAndWait(new Move(game.getMyHand()[0]));
                assertEquals("Incorrect hand!", 4 - (turn + 1), game.getMyHand().length);
            }
        }

        clientGame.get(0).onTurnStatus.waitFor();

        assertTrue("Hand not full!", clientGame.get(0).getMyHand().length == 4);

        for (int turn = 0; turn < 4; turn++)
        {
            for (int clientIndex = 0; clientIndex < clientGame.size(); clientIndex++)
            {
                if (turn == 3 && clientIndex == clientGame.size() - 1)
                    continue;

                ClientGame game = clientGame.get(clientIndex);
                game.makeMoveAndWait(new Move(game.getMyHand()[0]));
                assertEquals("Incorrect hand!", 4 - (turn + 1), game.getMyHand().length);
            }
        }

        ClientGame game = clientGame.get(clientGame.size() - 1);
        game.makeMove(new Move(game.getMyHand()[0]));
        clientGame.get(0).onEndGame.waitFor();
    }
}
