package se.hig.oodp.b9.logic;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.logic.client.ClientNetworkerSocket;
import se.hig.oodp.b9.logic.server.ServerGame;
import se.hig.oodp.b9.logic.server.ServerNetworkerSocket;
import se.hig.oodp.b9.model.Player;

/**
 * Test the table
 */
public class TestTable
{
    /**
     * The table
     */
    public Table table;

    /**
     * Set up
     */
    @Before
    public void setUp()
    {
        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i < 3; i++)
            players.add(new Player("TestPlayer" + i));

        table = new Table(players, UUID.randomUUID(), UUID.randomUUID());
    }

    /**
     * Test serialization <br>
     * <br>
     * Inspiration from
     * {@link <a href="http://www.tutorialspoint.com/java/java_serialization.htm">tutorialspoint</a>}
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void serializationTest() throws IOException, ClassNotFoundException
    {
        byte[] data;

        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream())
        {
            try (ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream))
            {
                objectOutStream.writeObject(table);
                data = outStream.toByteArray();
            }
        }

        Table newTable;

        try (ByteArrayInputStream inStream = new ByteArrayInputStream(data))
        {
            try (ObjectInputStream objectOutStream = new ObjectInputStream(inStream))
            {
                newTable = (Table) objectOutStream.readObject();
            }
        }

        assertNotNull("Can't read table", newTable);

        assertNotNull("Can't get onCardMove event", newTable.onCardMove);

        checkTables(table, newTable);
    }

    /**
     * Check if tables and be considered equal <br>
     * 
     * @param a
     *            first table
     * @param b
     *            second table
     * @return equals?
     */
    public boolean checkTables(Table a, Table b)
    {
        assertEquals("DeckUUID is not same", a.deckUUID, b.deckUUID);
        assertEquals("PoolUUID is not same", a.poolUUID, b.poolUUID);

        assertEquals("Not same amount of players", a.getPlayers().length, b.getPlayers().length);

        for (Player player : a.getPlayers())
        {
            assertTrue("Players differs", b.getPlayerIndex(player) != -1);
        }

        assertTrue("Wrong amount of cards", (a.cards.size() == b.deck.size()) && (b.deck.size() == 52));

        return true;
    }

    /**
     * Test to send table over socket
     */
    @Test
    // (timeout = 5000)
    // 5000 ms = 5 s
    public void overSocketTest() throws IOException, InterruptedException
    {
        int port = 34234;

        ServerNetworkerSocket server;

        new ServerGame(server = new ServerNetworkerSocket(port));

        ClientNetworkerSocket client = new ClientNetworkerSocket("127.0.0.1", port);

        client.sendGreeting(new Player("Mr GNU"));
        client.onServerGreeting.waitFor();

        server.sendTable(table);

        Table clientTable = client.onTable.waitFor();

        assertTrue("Tables are not equals", checkTables(table, clientTable));
    }
}
