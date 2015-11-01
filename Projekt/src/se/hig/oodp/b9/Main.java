package se.hig.oodp.b9;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.gui.client.GameWindow;
import se.hig.oodp.b9.logic.Rules;
import se.hig.oodp.b9.logic.client.AI;
import se.hig.oodp.b9.logic.client.AIStrategyPickSingleMore;
import se.hig.oodp.b9.logic.client.ClientGame;
import se.hig.oodp.b9.logic.client.ClientNetworkerSocket;
import se.hig.oodp.b9.logic.server.ServerGame;
import se.hig.oodp.b9.logic.server.ServerNetworkerSocket;
import se.hig.oodp.b9.model.Player;

@SuppressWarnings("all")
public class Main
{
    public static void main(String[] args) throws Exception
    {
        pseudos();
    }

    public static void testAI() throws Exception
    {
        ServerGame serverGame;

        int port = 59440;

        serverGame = new ServerGame(new ServerNetworkerSocket(port), new Rules());

        Thread.sleep(1000);

        ClientGame game = new ClientGame(new Player("Player"), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting();
        serverGame.playerAdded.waitFor();

        Thread.sleep(1000);

        new AI(new ClientGame(new Player("AI"), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting(), new AIStrategyPickSingleMore());
        serverGame.playerAdded.waitFor();

        Thread.sleep(1000);

        serverGame.newGame();

        GameWindow.start(game);
    }

    public static void test() throws IOException, InterruptedException
    {
        ServerGame serverGame;
        List<ClientGame> clientGame;

        int port = 59440;

        serverGame = new ServerGame(new ServerNetworkerSocket(port), new Rules());

        clientGame = new ArrayList<ClientGame>();
        for (int i = 0; i < 4; i++)
        {
            clientGame.add(new ClientGame(new Player("Player " + i), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting());
            serverGame.playerAdded.waitFor();
        }

        Thread.sleep(1000);

        serverGame.newGame();

        GameWindow.start(clientGame.get(0));

        for (ClientGame game : clientGame)
        {
            if (game.getTable() == null)
                game.getNetworker().onTable.waitFor();
        }

        Thread.sleep(1000);

        for (int giv = 0; giv < 3; giv++)
        {
            for (int turn = 0; turn < 4; turn++)
            {
                for (ClientGame game : clientGame)
                {
                    game.makeMoveAndWait(new Move(game.getMyHand()[0]));
                }
            }
            clientGame.get(0).getNetworker().onPlayerTurn.waitFor();
        }

        System.out.println("Client hand size: " + clientGame.get(0).getMyHand().length);

    }

    public static void windows()
    {
        int port = 59440;

        // Server setup

        ServerNetworkerSocket server = null;

        try
        {
            server = new ServerNetworkerSocket(port);
        }
        catch (IOException e)
        {
            System.out.println("Can't create server!\n\t" + e.getMessage());
            System.exit(1);
        }

        ServerGame serverGame = new ServerGame(server, new Rules());

        // Client setup

        for (int i = 0; i < 4; i++)
        {
            Player me = new Player("Player " + (i + 1));

            ClientNetworkerSocket clientNetworker = null;

            try
            {
                clientNetworker = new ClientNetworkerSocket("127.0.0.1", port);
                clientNetworker.onMessage.add(msg ->
                {
                    System.out.println("Client: " + msg.getMessage() + (msg.getSource() != null ? (" (from: " + msg.getSource() + ")") : ""));
                });
            }
            catch (IOException e)
            {
                System.out.println("Can't listen to server!\n\t" + e.getMessage());
                System.exit(1);
            }

            GameWindow.start(new ClientGame(me, clientNetworker));
        }

        for (int i = 0; i < 4; i++)
            serverGame.playerAdded.waitFor();

        serverGame.newGame();
    }

    public static void pseudos() throws Exception
    {
        int port = 59440;
        
        ServerGame serverGame = new ServerGame(new ServerNetworkerSocket(port), new Rules());

        GameWindow.start(new ClientGame(new Player("ME!"), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting());
        
        for (int i = 0; i < 3; i++)
        {
            new ClientGame(new Player("Player " + i), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting();
        }
        
        Thread.sleep(1000);

        serverGame.newGame();
    }
}
