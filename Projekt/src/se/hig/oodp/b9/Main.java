package se.hig.oodp.b9;

import java.io.IOException;

import se.hig.oodp.b9.client.ClientGame;
import se.hig.oodp.b9.client.ClientNetworkerSocket;
import se.hig.oodp.b9.server.ServerGame;
import se.hig.oodp.b9.server.ServerNetworkerSocket;

public class Main
{

    public static void main(String[] args)
    {
        int port = 59439;

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
        
        Player player = new Player("MrGNU");

        ClientNetworkerSocket clientNetworker = null;
        
        try
        {
            clientNetworker = new ClientNetworkerSocket("127.0.0.1", port);
        }
        catch (IOException e)
        {
            System.out.println("Can't listen to server!\n\t" + e.getMessage());
            System.exit(1);
        }

        ClientGame clientGame = new ClientGame(player, clientNetworker);
    }
}
