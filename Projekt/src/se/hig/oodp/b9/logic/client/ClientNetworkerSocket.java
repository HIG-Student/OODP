package se.hig.oodp.b9.logic.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
 * {@link ClientNetworker} implementation relying on <a
 * href="https://docs.oracle.com/javase/tutorial/networking/sockets/"
 * >Sockets</a>
 */
public class ClientNetworkerSocket extends ClientNetworker
{
    /**
     * Socket
     */
    Socket socket;

    /**
     * The object output stream that we {@link #sendObject(Object) write to}
     */
    ObjectOutputStream objectOutStream;

    /**
     * Sending an object to the server <br>
     * <br>
     * Most commonly packaged in a {@link Package}
     * 
     * @param obj
     *            the object to send
     * @return faildure?
     */
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
            System.out.println("Client: Can't send object!");
            System.exit(1);

            return false;
        }
        return true;
    }

    /**
     * Is the communication closed?
     */
    boolean isClosed = false;

    /**
     * Close the communication
     * 
     * @param clientIssued
     *            did we issue this?
     * @param reason
     *            the reason
     */
    @Override
    public void close(boolean clientIssued, String reason)
    {
        if (isClosed)
            return;

        isClosed = true;

        if (socket != null)
        {
            if (clientIssued)
                sendObject(new Package<String>(reason != null ? reason : "Client closed", Package.Type.Close));

            try
            {
                socket.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * Create new socket-based communication
     * 
     * @param host
     *            the host to connect to
     * @param port
     *            the port to connect to
     * @throws UnknownHostException
     *             the host is unknown
     * @throws IOException
     *             something wrong when interacting the the streams
     */
    @SuppressWarnings("unchecked")
    public ClientNetworkerSocket(String host, int port) throws UnknownHostException, IOException
    {
        // http://stackoverflow.com/a/4969788
        
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 2000);

        System.out.println("Client: Creating output stream");
        objectOutStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Client: Output stream created!");

        new Thread(() ->
        {
            try (ObjectInputStream objectOutStream = new ObjectInputStream(socket.getInputStream()))
            {
                while (!isClosed)
                {
                    try
                    {
                        Package<?> pkg = (Package<?>) objectOutStream.readObject();
                        switch (pkg.getType())
                        {
                        case Close:
                            String reason = ((Package<String>) pkg).getValue();
                            System.out.println("Client: Close: NOW");
                            onClose.invoke(reason);
                            close(false, reason);
                            return;
                        case Message:
                            onMessage.invoke(((Package<PMessage>) pkg).getValue());
                            break;
                        case Move:
                            onMove.invoke(((Package<PCardMovement>) pkg).getValue());
                            break;
                        case Table:
                            onTable.invoke(((Package<Table>) pkg).getValue());
                            break;
                        case CardInfo:
                            onCardInfo.invoke(((Package<Two<UUID, CardInfo>>) pkg).getValue());
                            break;
                        case PlayerAdded:
                            onPlayerAdded.invoke(((Package<Player>) pkg).getValue());
                            break;
                        case PlayerTurn:
                            onPlayerTurn.invoke(((Package<Player>) pkg).getValue());
                            break;
                        case ServerInfo:
                            onServerGreeting.invoke(((Package<PServerInfo>) pkg).getValue());
                            break;
                        case MoveResult:
                            onMoveResult.invoke(((Package<Boolean>) pkg).getValue());
                            break;
                        case EndGame:
                            onEndGame.invoke(((Package<Two<HashMap<Player, Integer>, HashMap<Player, Integer>>>) pkg).getValue());
                            break;
                        default:
                            System.out.println("Client: Unknown package!");
                        }
                    }
                    catch (Exception e)
                    {
                        if (!isClosed)
                        {
                            e.printStackTrace();
                            System.out.println("Client: Error on getting package data\n\t" + e.getMessage());
                            System.exit(1);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                if (!isClosed)
                    System.out.println("Client: Error on getting socket data\n\t" + e.getMessage());
            }
        }).start();
    }

    @Override
    public boolean sendMove(Move move)
    {
        return sendObject(new Package<Move>(move, Package.Type.Move));
    }

    @Override
    public boolean sendMessageTo(Player target, String message)
    {
        return sendObject(new Package<PMessage>(new PMessage(target, message), Package.Type.Message));
    }

    @Override
    public boolean sendGreeting(Player target)
    {
        return sendObject(target);
    }
}
