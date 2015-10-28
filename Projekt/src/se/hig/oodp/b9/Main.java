package se.hig.oodp.b9;

import java.io.IOException;

import se.hig.oodp.b9.client.ClientGame;
import se.hig.oodp.b9.client.ClientNetworkerSocket;
import se.hig.oodp.b9.client.GameWindow;
import se.hig.oodp.b9.server.ServerGame;
import se.hig.oodp.b9.server.ServerNetworkerSocket;

public class Main
{
    public static void main(String[] args)
    {
        windows();
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

        ServerGame serverGame = new ServerGame(server);

        // Client setup

        for (int i = 0; i < 4; i++)
        {
            Player me = new Player("Player " + i);

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

        serverGame.rules = new Rules();
        serverGame.newGame();
    }

    public static void pseudos()
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

        ServerGame serverGame = new ServerGame(server);

        // Client setup

        Player me = new Player("MrGNU");

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

        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e1)
        {
        }

        for (int i = 0; i < 3; i++)
            try
            {
                ClientNetworkerSocket psuedoClientNetworker = new ClientNetworkerSocket("127.0.0.1", port);
                psuedoClientNetworker.sendGreeting(new Player("Pseudo" + i));
            }
            catch (IOException e)
            {
                System.out.println("Can't listen to server!\n\t" + e.getMessage());
                System.exit(1);
            }

        serverGame.rules = new Rules();
        serverGame.newGame();
    }
}
