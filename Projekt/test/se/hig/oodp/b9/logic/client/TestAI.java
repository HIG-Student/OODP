package se.hig.oodp.b9.logic.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.*;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Table;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class TestAI
{
    HashMap<UUID, CardInfo> cards;
    ClientGame game;
    Table table;

    List<UUID> hand;
    List<UUID> pool;

    public UUID addCard(List<UUID> list, CardInfo info)
    {
        UUID id = UUID.randomUUID();
        cards.put(id, info);
        list.add(id);
        return id;
    }

    @SuppressWarnings("serial")
    @Before
    public void setUp()
    {
        hand = new ArrayList<UUID>();
        pool = new ArrayList<UUID>();

        cards = new HashMap<UUID, CardInfo>();

        game = new ClientGame(null, null)
        {
            @Override
            protected void setUp()
            {
            }

            @Override
            public CardInfo getCardInfo(UUID id)
            {
                return cards.containsKey(id) ? cards.get(id) : CardInfo.UNKNOWN;
            }
        };

        table = new Table(null, null, null)
        {
            @Override
            protected void setUp()
            {
            }

            @Override
            public UUID[] getPoolIds()
            {
                return pool.toArray(new UUID[0]);
            }

            @Override
            public UUID[] getCardCollection(UUID id)
            {
                return hand.toArray(new UUID[0]);
            }
        };
    }

    @Test
    public void testPickSingle()
    {
        AIStrategy strategy = new AIStrategyPickSingle();

        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Två));
        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Tre));
        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Fyra));

        UUID onHand_Sex = addCard(hand, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Sex));
        UUID onHand_Fem = addCard(hand, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Fem));

        Move move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Sex, move.getActiveCard());
        assertEquals("Take cards is incorrect", 0, move.getTakeCards().length);

        UUID match_Fem = addCard(pool, new CardInfo(CardInfo.Type.Spader, CardInfo.Value.Fem));

        move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Fem, move.getActiveCard());
        assertEquals("Take cards is incorrect", 1, move.getTakeCards().length);
        assertEquals("Take cards is incorrect", 1, move.getTakeCards()[0].length);
        assertEquals("Take cards is incorrect", match_Fem, move.getTakeCards()[0][0]);

        addCard(pool, new CardInfo(CardInfo.Type.Ruter, CardInfo.Value.Fem));

        move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Fem, move.getActiveCard());
        assertEquals("Take cards is incorrect", 1, move.getTakeCards().length);
        assertEquals("Take cards is incorrect", 1, move.getTakeCards()[0].length);
        assertEquals("Take cards is incorrect", match_Fem, move.getTakeCards()[0][0]);
    }

    @Test
    public void testPickSingleMore()
    {
        AIStrategy strategy = new AIStrategyPickSingleMore();

        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Två));
        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Tre));
        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Fyra));

        UUID onHand_Sex = addCard(hand, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Sex));
        UUID onHand_Fem = addCard(hand, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Fem));

        Move move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Sex, move.getActiveCard());
        assertEquals("Take cards is incorrect", 0, move.getTakeCards().length);

        UUID match_Fem = addCard(pool, new CardInfo(CardInfo.Type.Spader, CardInfo.Value.Fem));

        move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Fem, move.getActiveCard());
        assertEquals("Take cards is incorrect", 1, move.getTakeCards().length);
        assertEquals("Take cards is incorrect", 1, move.getTakeCards()[0].length);
        assertEquals("Take cards is incorrect", match_Fem, move.getTakeCards()[0][0]);

        UUID match_Fem2 = addCard(pool, new CardInfo(CardInfo.Type.Ruter, CardInfo.Value.Fem));

        move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Fem, move.getActiveCard());
        assertEquals("Take cards is incorrect", 2, move.getTakeCards().length);
        assertEquals("Take cards is incorrect", 1, move.getTakeCards()[0].length);
        assertEquals("Take cards is incorrect", 1, move.getTakeCards()[1].length);
        assertEquals("Take cards is incorrect", match_Fem, move.getTakeCards()[0][0]);
        assertEquals("Take cards is incorrect", match_Fem2, move.getTakeCards()[1][0]);
    }

    @Test
    public void testThrowAll()
    {
        AIStrategy strategy = new AIStrategyThrowAll();

        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Två));
        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Tre));
        addCard(pool, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Fyra));

        UUID onHand_Sex = addCard(hand, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Sex));
        addCard(hand, new CardInfo(CardInfo.Type.Hjärter, CardInfo.Value.Fem));

        Move move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Sex, move.getActiveCard());
        assertEquals("Take cards is incorrect", 0, move.getTakeCards().length);

        addCard(pool, new CardInfo(CardInfo.Type.Spader, CardInfo.Value.Fem));

        move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Sex, move.getActiveCard());
        assertEquals("Take cards is incorrect", 0, move.getTakeCards().length);

        addCard(pool, new CardInfo(CardInfo.Type.Ruter, CardInfo.Value.Fem));

        move = strategy.makeMove(game, new Player(""), table);

        assertEquals("Active card is incorrect", onHand_Sex, move.getActiveCard());
        assertEquals("Take cards is incorrect", 0, move.getTakeCards().length);
    }
}
