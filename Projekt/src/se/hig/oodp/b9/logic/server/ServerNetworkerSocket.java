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
 * {@link ServerNetworker} implementation relying on <a
 * href="https://docs.oracle.com/javase/tutorial/networking/sockets/"
 * >Sockets</a> <br>
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
    public void kill(String reason)
    {
        killed = true;

        for (ServerNetworkerClient client : clients)
            try
            {
                client.closeConnection(reason != null ? reason : "Server closed");
                Thread.sleep(10);
            }
            catch (Exception e)
            {

            }

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
                                        if (!killed)
                                            onLog.invoke("Can't send object!");

                                        return false;
                                    }
                                    return true;
                                }

                                @Override
                                public boolean sendTable(Table table)
                                {
                                    return sendObject(new Package<Table>(table, Package.Type.Table));
                                }

                                @Override
                                public boolean sendPlayerAdded(Player player)
                                {
                                    return sendObject(new Package<Player>(player, Package.Type.PlayerAdded));
                                }

                                @Override
                                public boolean sendMoveCard(UUID card, UUID collection)
                                {
                                    return sendObject(new Package<PCardMovement>(new PCardMovement(card, collection), Package.Type.Move));
                                }

                                @Override
                                public boolean sendMessage(Player source, String message)
                                {
                                    return sendObject(new Package<PMessage>(new PMessage(source, message), Package.Type.Message));
                                }

                                @Override
                                public boolean sendPlayerTurn(Player player)
                                {
                                    return sendObject(new Package<Player>(player, Package.Type.PlayerTurn));
                                }

                                @Override
                                public boolean sendEndgame(HashMap<Player, Integer> scores, HashMap<Player, Integer> totalScores)
                                {
                                    return sendObject(new Package<Two<HashMap<Player, Integer>, HashMap<Player, Integer>>>(new Two<HashMap<Player, Integer>, HashMap<Player, Integer>>(scores, totalScores), Package.Type.EndGame));
                                }

                                @Override
                                public boolean sendCardInfo(UUID card, CardInfo info)
                                {
                                    return sendObject(new Package<Two<UUID, CardInfo>>(new Two<UUID, CardInfo>(card, info), Package.Type.CardInfo));
                                }

                                @Override
                                public boolean sendGreeting(PServerInfo info)
                                {
                                    return sendObject(new Package<PServerInfo>(info, Package.Type.ServerInfo));
                                }

                                @Override
                                public boolean closeConnection(String reason)
                                {
                                    boolean ok = sendObject(new Package<String>(reason, Package.Type.Close));

                                    close();

                                    return ok;
                                }

                                @Override
                                public boolean sendMoveResult(Boolean bool)
                                {
                                    return sendObject(new Package<Boolean>(bool, Package.Type.MoveResult));
                                }
                            };

                            addClient(client);

                            while (true)
                            {
                                Package<?> pkg = (Package<?>) objectInStream.readObject();
                                switch (pkg.getType())
                                {
                                case Close:
                                    String reason = ((Package<String>) pkg).getValue();
                                    onLog.invoke("Close: " + reason);
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
                            if (!killed)
                                onLog.invoke("Error for client" + (playerName != null ? ("[" + playerName + "]") : "") + "!\n\t" + e.getMessage());
                        }
                    }).start();
                }
                catch (Exception e)
                {
                    if (!killed)
                        onLog.invoke("Can't accept client!\n\n" + e.getMessage());
                }
            }
        }).start();
    }
}
