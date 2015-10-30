package se.hig.oodp.b9.logic.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.communication.PCardMovement;
import se.hig.oodp.b9.communication.PMessage;
import se.hig.oodp.b9.communication.PServerInfo;
import se.hig.oodp.b9.communication.Package;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.logic.Two;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

/**
 * {@link ServerNetworker} implementation relying on
 * {@link <a href="https://docs.oracle.com/javase/tutorial/networking/sockets/">Sockets</a>}
 * <br>
 * <br>
 */
public class ServerNetworkerSocket extends ServerNetworker
{
    /**
     * The server socket
     */
    ServerSocket server;

    /**
     * Is killed?
     */
    boolean killed = false;

    @Override
    public void kill()
    {
        killed = true;
        try
        {
            server.close();
        }
        catch (IOException e)
        {

        }
        onKill.invoke(true);
    }

    /**
     * Create ServerNetworkerSocket
     * 
     * @param port
     *            port to listen on
     * @throws IOException
     *             thrown if we have sockets errors
     */
    @SuppressWarnings("unchecked")
    public ServerNetworkerSocket(int port) throws IOException
    {
        server = new ServerSocket(port);

        onLog.add(msg -> System.out.println("Server log: " + msg));

        new Thread(() ->
        {
            while (!killed)
            {
                try
                {
                    Socket socket = server.accept();

                    onLog.invoke("Server: Found client!");

                    new Thread(() ->
                    {
                        String playerName = null;

                        onLog.invoke("Listening for client greeting!");

                        try (ObjectInputStream objectInStream = new ObjectInputStream(socket.getInputStream()))
                        {
                            Player socketPlayer = (Player) objectInStream.readObject();

                            playerName = socketPlayer.getName();

                            onLog.invoke("New client is: [" + socketPlayer + "]");

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
                                        objectOutStream.reset();
                                        objectOutStream.writeObject(obj);
                                        objectOutStream.flush();
                                    }
                                    catch (IOException e)
                                    {
                                        onLog.invoke("Can't send object!");
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
                                public void sendMoveCard(UUID card, UUID collection)
                                {
                                    sendObject(new Package<PCardMovement>(new PCardMovement(card, collection), Package.Type.Move));
                                }

                                @Override
                                public void sendMessage(Player source, String message)
                                {
                                    sendObject(new Package<PMessage>(new PMessage(source, message), Package.Type.Message));
                                }

                                @Override
                                public void sendPlayerTurn(Player player)
                                {
                                    sendObject(new Package<Player>(player, Package.Type.PlayerTurn));
                                }

                                @Override
                                public void sendEndgame(HashMap<Player, Integer> scores, HashMap<Player, Integer> totalScores)
                                {
                                    sendObject(new Package<Two<HashMap<Player, Integer>, HashMap<Player, Integer>>>(new Two<HashMap<Player, Integer>, HashMap<Player, Integer>>(scores, totalScores), Package.Type.EndGame));
                                }

                                @Override
                                public void sendCardInfo(UUID card, CardInfo info)
                                {
                                    sendObject(new Package<Two<UUID, CardInfo>>(new Two<UUID, CardInfo>(card, info), Package.Type.CardInfo));
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

                                @Override
                                public void sendMoveResult(Boolean bool)
                                {
                                    sendObject(new Package<Boolean>(bool, Package.Type.MoveResult));
                                }
                            };

                            addClient(client);

                            onKill.add((bool) -> client.closeConnection("Server killed"));

                            while (true)
                            {
                                Package<?> pkg = (Package<?>) objectInStream.readObject();
                                switch (pkg.getType())
                                {
                                case Close:
                                    // String reason = ((Package<String>)
                                    // pkg).value;
                                    socket.close();
                                    break;
                                case Message:
                                    PMessage msg = ((Package<PMessage>) pkg).getValue();
                                    onNewMessage.invoke(new Two<Player, String>(socketPlayer, msg.getMessage()));
                                    break;
                                case Move:
                                    onNewMove.invoke(new Two<Player, Move>(socketPlayer, ((Package<Move>) pkg).getValue()));
                                    break;
                                default:
                                    onLog.invoke("Unknown package!");
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            onLog.invoke("Error for client" + (playerName != null ? ("[" + playerName + "]") : "") + "!\n\t" + e.getMessage());
                        }
                    }).start();
                }
                catch (Exception e)
                {
                    onLog.invoke("Can't accept client!\n\n" + e.getMessage());
                }
            }
        }).start();
    }
}
