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
import se.hig.oodp.b9.logic.server.CardDeck;
import se.hig.oodp.b9.logic.server.ServerGame;
import se.hig.oodp.b9.logic.server.ServerNetworkerSocket;
import se.hig.oodp.b9.model.Card;
import se.hig.oodp.b9.model.CardInfo;
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
        table.changeDeck(new CardDeck().getCards());
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

        invalidatingCheckTables(table, newTable);
    }

    /**
     * Check if tables and be considered equal <br>
     * <br>
     * OBSERVE: This test messes with the tables
     * 
     * @param a
     *            first table
     * @param b
     *            second table
     * @return equals?
     */
    public boolean invalidatingCheckTables(Table a, Table b)
    {
        assertEquals("DeckUUID is not same", a.deckUUID, b.deckUUID);
        assertEquals("PoolUUID is not same", a.poolUUID, b.poolUUID);

        assertEquals("Not same amount of players", a.players.size(), b.players.size());

        for (Player player : a.players)
        {
            assertTrue("Players differs", b.playerHands.containsKey(player));
        }

        assertTrue("Wrong amount of cards", (a.cards.size() == b.deck.size()) && (b.deck.size() == 52));

        CardInfo info = new CardInfo(CardInfo.Type.Ruter, CardInfo.Value.Ess);

        for (Card card : b.cards.values())
            card.setCardInfo(info);

        for (Card card : b.pool.getAll())
            assertEquals("Reference is broken", card.getCardInfo(), info);

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

        assertTrue("Tables are not equals", invalidatingCheckTables(table, clientTable));
    }
}
