package se.hig.oodp.b9.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.PServerInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Rules.Move;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Package;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.Two;

;

public class ServerNetworkerSocket extends ServerNetworker
{
    // http://www.oracle.com/technetwork/java/socket-140484.html#server
    ServerSocket server;

    List<Thread> threads = new ArrayList<Thread>();

    public void kill()
    {
        try
        {
            server.close();
        }
        catch (IOException e)
        {

        }
    }

    public ServerNetworkerSocket(int port) throws IOException
    {
        server = new ServerSocket(port);

        new Thread(() ->
        {
            while (true)
            {
                try
                {
                    Socket socket = server.accept();

                    System.out.println("Server: Found client!");

                    new Thread(() ->
                    {
                        String playerName = null;

                        System.out.println("Server: Listening for client greeting!");

                        try (ObjectInputStream objectInStream = new ObjectInputStream(socket.getInputStream()))
                        {
                            Player socketPlayer = (Player) objectInStream.readObject();

                            playerName = socketPlayer.getName();

                            System.out.println("Server: New client is: [" + socketPlayer + "]");

                            ServerNetworkerClient client = new ServerNetworkerClient()
                            {
                                ObjectOutputStream objectOutStream = new ObjectOutputStream(socket.getOutputStream());

                                {
                                    this.player = socketPlayer;
                                }

                                // Just in case
                                @Override
                                public void finalize() throws Throwable
                                {
                                    close();
                                    super.finalize();
                                }

                                public boolean isClosed = false;

                                public void close()
                                {
                                    if (isClosed)
                                        return;

                                    try
                                    {
                                        socket.close();
                                    }
                                    catch (IOException e)
                                    {
                                    }

                                    isClosed = true;
                                }

                                public boolean sendObject(Object obj)
                                {
                                    try
                                    {
                                        objectOutStream.writeObject(obj);
                                    }
                                    catch (IOException e)
                                    {
                                        System.out.println("Server: Can't send object!");
                                        System.exit(1);

                                        return false;
                                    }
                                    return true;
                                }

                                @Override
                                public void sendTable(Table table)
                                {
                                    sendObject(new Package<Table>(table, Package.Type.Table));
                                }

                                @Override
                                public void sendPlayerAdded(Player player)
                                {
                                    sendObject(new Package<Player>(player, Package.Type.PlayerAdded));
                                }

                                @Override
                                public void sendNewCards(UUID[] cardIds)
                                {
                                    sendObject(new Package<UUID[]>(cardIds, Package.Type.Cards));
                                }

                                @Override
                                public void sendMoveCard(Card card, CardCollection collection)
                                {
                                    sendObject(new Package<PCardMovement>(new PCardMovement(card.getId(), collection.getId()), Package.Type.Move));
                                }

                                @Override
                                public void sendMessage(Player source, String message)
                                {
                                    sendObject(new Package<PMessage>(new PMessage(source, message), Package.Type.Message));
                                }

                                @Override
                                public void sendGetMove()
                                {
                                    sendObject(new Package<Boolean>(true, Package.Type.RequestMove));
                                }

                                @Override
                                public void sendEndgame()
                                {
                                    sendObject(new Package<Boolean>(true, Package.Type.Close));
                                }

                                @Override
                                public void sendCardInfo(Card card)
                                {
                                    sendObject(new Package<Two<UUID, CardInfo>>(new Two<UUID, CardInfo>(card.getId(), card.getCardInfo()), Package.Type.CardInfo));
                                }
                                
                                @Override
                                public void sendGreeting(PServerInfo info)
                                {
                                    sendObject(new Package<PServerInfo>(info, Package.Type.ServerInfo));
                                }

                                @Override
                                public void closeConnection(String reason)
                                {
                                    sendObject(new Package<String>(reason, Package.Type.Close));

                                    close();
                                }
                            };

                            addClient(client);

                            while (true)
                            {
                                Package pkg = (Package) objectInStream.readObject();
                                switch (pkg.type)
                                {
                                case Close:
                                    // String reason = ((Package<String>)
                                    // pkg).value;
                                    socket.close();
                                    break;
                                case Message:
                                    PMessage msg = ((Package<PMessage>) pkg).value;
                                    onNewMessage.invoke(new Two<Player, String>(socketPlayer, msg.getMessage()));
                                    break;
                                case Move:
                                    onNewMove.invoke(new Two<Player, Move>(socketPlayer, ((Package<Move>) pkg).value));
                                    break;
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println("Server: Error for client" + (playerName != null ? ("[" + playerName + "]") : "") + "!\n\t" + e.getMessage());
                        }
                    }).start();
                }
                catch (Exception e)
                {
                    System.out.println("Server: Can't accept client!\n\n" + e.getMessage());
                }
            }
        }).start();
    }
}
