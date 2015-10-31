package se.hig.oodp.b9.logic;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

@SuppressWarnings("javadoc")
public class TestTable
{
    List<Player> players;
    public Table table;

    @Before
    public void setUp()
    {
        players = new ArrayList<Player>();
        for (int i = 0; i < 3; i++)
            players.add(new Player("TestPlayer" + i));

        table = new Table(players, UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    public void testSetUp()
    {
        assertEquals("Incorrect player count", 3, table.getPlayers().length);
        assertEquals("Incorrect card count", 52, table.getDeck().length);
    }

    /*
     * Inspiration from:
     * href="http://www.tutorialspoint.com/java/java_serialization.htm
     */
    @Test
    public void testSerialization() throws Exception
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

        assertNotNull("'onCardMove event' not remade", newTable.onCardMove);

        checkTables(table, newTable);
    }

    public void checkTables(Table a, Table b)
    {
        assertEquals("DeckUUID is not same", a.deckUUID, b.deckUUID);
        assertEquals("PoolUUID is not same", a.poolUUID, b.poolUUID);

        assertEquals("Not same amount of players", a.getPlayers().length, b.getPlayers().length);

        for (Player player : a.getPlayers())
        {
            assertTrue("Players differs", b.getPlayerIndex(player) != -1);
        }

        assertTrue("Wrong amount of cards", (a.cards.size() == b.deck.size()) && (b.deck.size() == 52));
    }

    @Test(timeout = 5000)
    public void testOverSocket() throws Exception
    {
        int port = 34234;

        ServerNetworkerSocket server;

        new ServerGame(server = new ServerNetworkerSocket(port));

        ClientNetworkerSocket client = new ClientNetworkerSocket("127.0.0.1", port);

        client.sendGreeting(new Player("Mr GNU"));
        client.onServerGreeting.waitFor();

        server.sendTable(table);

        Table clientTable = client.onTable.waitFor();

        checkTables(table, clientTable);
    }

    @Test
    public void testNextPlayer() throws Exception
    {
        for (int i = 0; i < 20; i++)
        {
            assertEquals("Turns are incorrect!", players.get(i % players.size()), table.getNextPlayer());
            table.nextTurn();
        }
    }

    private class Count
    {
        public int value;
    }

    private class CurrentCard
    {
        public UUID value;
    }

    @Test
    public void testCardMove() throws Exception
    {
        CurrentCard at = new CurrentCard();
        Count count = new Count();

        table.onCardMove.add(two ->
        {
            assertEquals("Event gives wrong card!", at.value, two.getOne());
            assertTrue("Event gives wrong destination!", two.getTwo() == table.poolUUID);
            count.value++;
        });

        for (int i = 0; i < 52; i++)
        {
            UUID id = table.getCardFromDeck();

            assertNotNull("Can't take card", id);

            at.value = id;
            table.moveCard(id, table.poolUUID);
        }

        assertNull("Should not be able to take card", table.getCardFromDeck());

        assertEquals("Pool size is incorrect!", 52, table.getPoolIds().length);

        assertEquals("Event not working!", 52, count.value);
    }
}
