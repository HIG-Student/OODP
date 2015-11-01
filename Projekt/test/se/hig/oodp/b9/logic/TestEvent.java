package se.hig.oodp.b9.logic;

import org.junit.*;

import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class TestEvent
{
    public Event<Object> testEvent;

    @Before
    public void setUp()
    {
        testEvent = new Event<Object>();
    }

    @After
    public void tearDown()
    {
        testEvent.clear();
    }

    @Test(timeout = 5000)
    public void testWaitFor()
    {
        Object obj = new Object();

        new Thread(() ->
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e)
            {
                fail("Thread failed!");
            }

            testEvent.invoke(obj);
        }).start();

        assertEquals("Not getting the correct object", obj, testEvent.waitFor());
    }

    @Test(timeout = 5000)
    public void testMultipleWaitFor()
    {
        Object obj = new Object();

        new Thread(() ->
        {
            for (int i = 0; i < 3; i++)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (Exception e)
                {
                    fail("Thread failed!");
                }

                testEvent.invoke(obj);
            }
        }).start();

        assertEquals("Not getting the correct object", obj, testEvent.waitFor());
        assertEquals("Not getting the correct object", obj, testEvent.waitFor());
        assertEquals("Not getting the correct object", obj, testEvent.waitFor());
    }

    @Test(timeout = 5000)
    public void testMultipleValuedWaitFor()
    {
        Object[] objs = new Object[]
        {
                new Object(),
                new Object(),
                new Object()
        };

        new Thread(() ->
        {
            for (Object obj : objs)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (Exception e)
                {
                    fail("Thread failed!");
                }

                testEvent.invoke(obj);
            }
        }).start();

        for (Object obj : objs)
            assertEquals("Not getting the correct object", obj, testEvent.waitFor());
    }

    @Test(timeout = 5000)
    public void testClear()
    {
        testEvent.add((obj) -> fail("Should not be called on clear!"));

        new Thread(() ->
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e)
            {
                fail("Thread failed!");
            }

            testEvent.clear();
            assertTrue("Not cleared", testEvent.actions.size() == 0);
        }).start();

        assertEquals("Not getting the correct object", null, testEvent.waitFor());
    }

    @Test(timeout = 5000, expected = InterruptedException.class)
    public void testClearOnThrow() throws InterruptedException, MultipleExceptions
    {
        testEvent.add((obj) -> fail("Should not be called on clear!"));

        new Thread(() ->
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e)
            {
                fail("Thread failed!");
            }

            testEvent.clear();
            assertTrue("Not cleared", testEvent.actions.size() == 0);
        }).start();

        assertEquals("Not getting the correct object", null, testEvent.throwingWaitFor());
    }

    private class Bool
    {
        public boolean value = false;
    }

    @Test
    public void testInvoke()
    {
        Bool bool = new Bool();
        Bool bool2 = new Bool();

        testEvent.add((args) ->
        {
            assertEquals("Wrong args", bool, args);
            bool.value = true;
            bool2.value = true;
        });

        testEvent.invoke(bool);

        assertTrue("Action not run!", bool.value);
        assertTrue("Action not run!", bool2.value);
    }
}
