package se.hig.oodp.b9.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.*;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.model.Player;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class TestRules
{
    Rules rules;

    @Before
    public void setUp()
    {
        rules = new Rules();
    }

    @Test
    public void testGive()
    {
        @SuppressWarnings("serial")
        List<Player> players = new ArrayList<Player>()
        {
            {
                for (int i = 0; i < 3; i++)
                    add(new Player("Test player " + i));
            }
        };

        Table table = new Table(players, UUID.randomUUID(), UUID.randomUUID());

        rules.give(table);

        for (Player player : players)
            assertEquals("Player does not have correct amount of cards!", 4, table.getPlayerHandIds(player).length);

        assertEquals("Cards in the deck are not moved!", 52 - 4 * 3, table.getDeckIds().length);
    }

    @Test
    public void testSetUp()
    {
        @SuppressWarnings("serial")
        List<Player> players = new ArrayList<Player>()
        {
            {
                for (int i = 0; i < 3; i++)
                    add(new Player("Test player " + i));
            }
        };

        Table table = new Table(players, UUID.randomUUID(), UUID.randomUUID());

        rules.setUp(table);

        for (Player player : players)
            assertEquals("Player does not have correct amount of cards!", 4, table.getPlayerHandIds(player).length);

        assertEquals("Incorrect pool!", 4, table.getPoolIds().length);

        assertEquals("Cards in the deck are not moved!", 52 - 4 * 4, table.getDeckIds().length);
    }

    @Test
    public void testCardMove()
    {
        @SuppressWarnings("serial")
        List<Player> players = new ArrayList<Player>()
        {
            {
                for (int i = 0; i < 3; i++)
                    add(new Player("Test player " + i));
            }
        };

        Table table = new Table(players, UUID.randomUUID(), UUID.randomUUID());

        rules.setUp(table);

        Move invalidMove = new Move(UUID.randomUUID());

        assertFalse("Invalid move should be invalid!", rules.canPlayMove(players.get(0), table, invalidMove));

        Move validMove = new Move(table.getPlayerHandIds(players.get(0))[0]);

        assertTrue("Valid move should be valid!", rules.canPlayMove(players.get(0), table, validMove));

        assertFalse("Invalid move should be invalid!", rules.canPlayMove(players.get(1), table, validMove));

        table.nextTurn();

        assertFalse("Must be correct turn!", rules.canPlayMove(players.get(0), table, validMove));

        assertFalse("Invalid move should be invalid!", rules.canPlayMove(players.get(1), table, validMove));
    }
}
