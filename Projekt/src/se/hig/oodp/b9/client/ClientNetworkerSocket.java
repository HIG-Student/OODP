package se.hig.oodp.b9.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.PServerInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Package;
import se.hig.oodp.b9.Move;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.Two;

public class ClientNetworkerSocket extends ClientNetworker
{
    public Socket socket;

    // Just in case
    @Override
    public void finalize() throws Throwable
    {
        close(false);
        super.finalize();
    }

    ObjectOutputStream objectOutStream;

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

    boolean isClosed = false;

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
                    Package p;
                    try
                    {
                        Package pkg = (Package) objectOutStream.readObject();
                        p = pkg;
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
                        case Cards:
                            onCards.invoke(((Package<Card[]>) pkg).getValue());
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
                            onEndGame.invoke(((Package<HashMap<Player,Integer>>) pkg).getValue());
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
        System.out.println("C: " + move.getActiveCard().getCardInfo() + " (" + move.getActiveCard().getId() + ")");
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
