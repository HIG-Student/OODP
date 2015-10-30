package se.hig.oodp.b9.logic.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public Socket socket;

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
     */
    public void close(boolean clientIssued)
    {
        if (isClosed)
            return;

        if (objectOutStream != null)
            try
            {
                objectOutStream.close();
            }
            catch (IOException e)
            {
            }

        if (socket != null)
        {
            if (clientIssued)
                sendObject(new Package<Boolean>(true, Package.Type.Close));

            try
            {
                socket.close();
            }
            catch (IOException e)
            {
            }
        }
        isClosed = true;
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
        socket = new Socket(host, port);

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
                            onClose.invoke(((Package<String>) pkg).getValue());
                            close(false);
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
                        System.out.println("Client: Error on getting package data\n\t" + e.getMessage());
                        System.exit(1);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Client: Error on getting socket data\n\t" + e.getMessage());
            }
        }).start();
    }

    @Override
    public void sendMove(Move move)
    {
        sendObject(new Package<Move>(move, Package.Type.Move));
    }

    @Override
    public void sendMessageTo(Player target, String message)
    {
        sendObject(new Package<PMessage>(new PMessage(target, message), Package.Type.Message));
    }

    @Override
    public void sendGreeting(Player target)
    {
        sendObject(target);
    }

    @Override
    public void close(String reason)
    {
        try
        {
            sendObject(new Package<String>(reason, Package.Type.Close));
        }
        catch (Exception e)
        {

        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {

            }
        }
    }
}
