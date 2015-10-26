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

    public boolean sendObject(Object obj)
    {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream())
        {
            try (ObjectOutputStream objectOutStream = new ObjectOutputStream(socket.getOutputStream()))
            {
                objectOutStream.writeObject(obj);
            }
        }
        catch (IOException e)
        {
            // TODO: better response
            System.out.println("Can't send object!");
            System.exit(1);

            return false;
        }
        return true;
    }

    public void close(boolean clientIssued)
    {
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
    }

    public ClientNetworkerSocket(String host, int port) throws UnknownHostException, IOException
    {
        socket = new Socket(host, port);

        new Thread(() ->
        {
            try (ObjectInputStream objectOutStream = new ObjectInputStream(socket.getInputStream()))
            {
                while (true)
                {
                    try
                    {
                        Package pkg = (Package) objectOutStream.readObject();
                        switch (pkg.type)
                        {
                        case Close:
                            onClose.invoke();
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
                            onCardInfo.invoke(((Package<Two<UUID,CardInfo>>) pkg).value);
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
