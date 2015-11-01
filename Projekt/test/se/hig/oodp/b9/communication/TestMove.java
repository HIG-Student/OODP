package se.hig.oodp.b9.communication;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.b9.T;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class TestMove
{
    public Move move;

    UUID activeCardId;

    UUID[][] cardsToTake;

    @Before
    public void setUp()
    {
        activeCardId = UUID.randomUUID();
        move = new Move(activeCardId);

        cardsToTake = new UUID[][]
        {
                new UUID[]
                {
                        UUID.randomUUID()
                },
                new UUID[]
                {
                        UUID.randomUUID(),
                        UUID.randomUUID()
                },
                new UUID[]
                {
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID()
                },
                new UUID[]
                {
                        UUID.randomUUID(),
                        UUID.randomUUID()
                }
        };

        for (UUID[] ids : cardsToTake)
        {
            move.nextTake();
            for (UUID id : ids)
            {
                move.addTake(id);
            }
        }
    }

    @Test
    public void testSerialization()
    {
        Move serializatedMove = T.serialization(move);

        assertEquals("Active card does not survive serialization!", move.getActiveCard(), serializatedMove.getActiveCard());

        assertTrue("Taken cards length is not correct!", serializatedMove.getTakeCards().length == cardsToTake.length - 1);

        for (int i = 0; i < cardsToTake.length - 1; i++)
        {
            UUID[] cardList = cardsToTake[i];

            for (int j = 0; j < cardList.length; j++)
            {
                assertEquals("Take " + i + ":" + j + " is not equal!", serializatedMove.getTakeCards()[i][j], cardList[j]);
            }
        }
    }

    @Test
    public void testCurrentTake()
    {
        assertTrue("CurrentTaken cards is in taken cards!", move.getTakeCards().length < cardsToTake.length);

        for (UUID id : cardsToTake[cardsToTake.length - 1])
            assertFalse("CurrentTaken is treated as in taken cards!", move.takeContains(id));

        for (UUID id : cardsToTake[cardsToTake.length - 1])
            assertTrue("CurrentTaken is not treated as in CurrentTaken!", move.currentTakeContains(id));

        for (int i = 0; i < cardsToTake.length - 1; i++)
        {
            UUID[] cardList = cardsToTake[i];

            for (int j = 0; j < cardList.length; j++)
            {
                assertTrue("Taken cards not treated as in taken cards!", move.takeContains(cardList[j]));
                assertFalse("Taken cards is treated as in CurrentTaken!", move.currentTakeContains(cardList[j]));
            }
        }

        move.nextTake();

        assertTrue("CurrentTaken not added!", move.getTakeCards().length == cardsToTake.length);

        assertTrue("CurrentTaken not empty!", move.getCurrentTakeCards().length == 0);

        for (int i = 0; i < cardsToTake.length; i++)
        {
            UUID[] cardList = cardsToTake[i];

            for (int j = 0; j < cardList.length; j++)
            {
                assertTrue("Taken cards not treated as in taken cards!", move.takeContains(cardList[j]));
                assertFalse("Taken cards is treated as in CurrentTaken!", move.currentTakeContains(cardList[j]));
            }
        }
    }

    @Test
    public void testClearTake()
    {
        move.clearTake();

        assertTrue("Active card is cleared!", move.getActiveCard() == activeCardId);
        assertTrue("Not cleared!", move.getTakeCards().length == 0);
        assertTrue("Not cleared!", move.getCurrentTakeCards().length == 0);
    }

    @Test
    public void testToggleCurrentTake()
    {
        {
            UUID id = cardsToTake[cardsToTake.length - 1][0];

            move.toggleTake(id);

            assertFalse("CurrentTake can't toggle!", move.currentTakeContains(id));

            for (int i = 1; i < cardsToTake[cardsToTake.length - 1].length; i++)
                assertTrue("Curent take is corrupted!", move.currentTakeContains(cardsToTake[cardsToTake.length - 1][i]));

            assertTrue("ActiveCard is corrupted!", move.getActiveCard() == activeCardId);
        }

        // Ensure not possible to toggle non-current takes
        {
            UUID id = cardsToTake[cardsToTake.length - 2][0];

            assertFalse("CurrentTake is incorrect!", move.currentTakeContains(id));

            assertTrue("TakenCards is incorrect!", move.takeContains(id));

            move.toggleTake(id); // No change should occur

            assertFalse("CurrentTake is incorrect!", move.currentTakeContains(id));

            assertTrue("TakenCards is incorrect!", move.takeContains(id));

            for (int i = 1; i < cardsToTake[cardsToTake.length - 1].length; i++)
                assertTrue("Curent take is corrupted!", move.currentTakeContains(cardsToTake[cardsToTake.length - 1][i]));

            assertTrue("ActiveCard is corrupted!", move.getActiveCard() == activeCardId);
        }
    }

    @Test
    public void testToggleActiveCard()
    {
        move.toggleActive(activeCardId);

        assertTrue("ActiveCard not removed!", move.getActiveCard() == null);

        assertTrue("TakenCards not cleared!", move.getTakeCards().length == 0);

        assertTrue("CurrentTakenCards not cleared!", move.getCurrentTakeCards().length == 0);

        move.addTake(activeCardId);

        assertFalse("Should not be able to add stuff witout active card!", move.currentTakeContains(activeCardId));

        move.toggleActive(activeCardId);

        assertTrue("ActiveCard not added!", move.getActiveCard() == activeCardId);

        assertTrue("TakenCards not still cleared!", move.getTakeCards().length == 0);

        assertTrue("CurrentTakenCards not still cleared!", move.getCurrentTakeCards().length == 0);
    }

    @Test
    public void testTakeActive()
    {
        move.addTake(activeCardId);

        assertFalse("Should not be able to take active card!", move.currentTakeContains(activeCardId));

        move.toggleTake(activeCardId);

        assertFalse("Should not be able to take active card!", move.currentTakeContains(activeCardId));

        assertTrue("Should not remove activecard!", move.getActiveCard() == activeCardId);

        assertFalse("Should not clear taken cards!", move.getTakeCards().length == 0);
    }

    @Test
    public void testTakeSame()
    {
        UUID newCard = UUID.randomUUID();

        move.addTake(newCard);

        assertTrue("Can't take card!", move.currentTakeContains(newCard));

        UUID[] before = move.getCurrentTakeCards();

        move.addTake(newCard);

        assertTrue("Card removed!", move.currentTakeContains(newCard));

        assertTrue("CurrentTaken corrupted!", before.length == move.getCurrentTakeCards().length);
    }
}
