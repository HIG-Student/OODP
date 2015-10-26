package se.hig.oodp.b9.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.PCardMovement;
import se.hig.oodp.b9.PMessage;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Package;
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
            objectOutStream.writeObject(obj);
        }
        catch (IOException e)
        {
            System.out.println("Can't send object!");
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

        System.out.println("Creating output stream");
        objectOutStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Output stream created!");

        System.out.println("1");

        new Thread(() ->
        {
            System.out.println("2");

            try (ObjectInputStream objectOutStream = new ObjectInputStream(socket.getInputStream()))
            {
                System.out.println("3");
                while (true)
                {
                    System.out.println("4");
                    try
                    {
                        System.out.println("Waiting for package");
                        Package pkg = (Package) objectOutStream.readObject();
                        System.out.println("Got package: " + pkg.type);
                        switch (pkg.type)
                        {
                        case Close:
                            onClose.invoke(((Package<String>) pkg).value);
                            close(false);
                            return;
                        case Message:
                            onMessage.invoke(((Package<PMessage>) pkg).value);
                            break;
                        case Move:
                            onMove.invoke(((Package<PCardMovement>) pkg).value);
                        case Table:
                            onTable.invoke(((Package<Table>) pkg).value);
                            break;
                        case CardInfo:
                            onCardInfo.invoke(((Package<Two<UUID, CardInfo>>) pkg).value);
                            break;
                        case Cards:
                            onCards.invoke(((Package<UUID[]>) pkg).value);
                            break;
                        case PlayerAdded:
                            onPlayer.invoke(((Package<Player>) pkg).value);
                            break;
                        case RequestMove:
                            onMoveRequest.invoke();
                            break;
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error on getting package data\n\t" + e.getMessage());
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Error on getting socket data\n\t" + e.getMessage());
            }
        }).start();
    }

    @Override
    public void sendMove(PCardMovement move)
    {
        sendObject(new Package<PCardMovement>(move, Package.Type.Move));
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
}
