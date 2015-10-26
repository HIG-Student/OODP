package se.hig.b9;

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

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Table;
import se.hig.oodp.b9.server.CardDeck;

public class TestTable
{
    public Table table;

    @Before
    public void setUp()
    {
        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i < 3; i++)
            players.add(new Player("TestPlayer" + i));

        table = new Table(players, UUID.randomUUID(), UUID.randomUUID());
        table.addDeck(new CardDeck().getDeckUUIDs());
    }

    @Test
    public void serializationTest() throws IOException, ClassNotFoundException
    {
        // http://www.tutorialspoint.com/java/java_serialization.htm

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

        assertEquals("DeckUUID is not same", table.deckUUID, newTable.deckUUID);
        assertEquals("PoolUUID is not same", table.poolUUID, newTable.poolUUID);

        assertEquals("Not same amount of players", table.players.size(), newTable.players.size());

        for (Player player : newTable.players)
        {
            assertTrue("Player hash differs", newTable.playerHands.containsKey(player));
        }

        assertTrue("Wrong amount of cards", (newTable.cards.size() == newTable.deck.size()) && (newTable.deck.size() == 52));

        CardInfo info = new CardInfo(CardInfo.Type.Ruter, CardInfo.Value.Ess);

        for (Card card : newTable.cards.values())
            card.setCardInfo(info);

        for (Card card : newTable.cards.values())
            assertEquals("Reference is broken", card.getCardInfo(), info);
    }
}
