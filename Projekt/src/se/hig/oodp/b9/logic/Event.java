package se.hig.oodp.b9.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Representing an event <br>
 * <br>
 * Can be invoked and thus alerting subscribing actions
 * 
 * @param <T>
 *            The type of value sent to the subscribed actions
 */
public class Event<T>
{
    /**
     * The list containing return values and a wait-lock for the "WaitFor"
     * method
     */
    HashMap<Object, T> waitForList = new HashMap<Object, T>();

    /**
     * The list containing exceptions for the "throwingWaitFor"
     */
    HashMap<Object, MultipleExceptions> throwingWaitForList = new HashMap<Object, MultipleExceptions>();

    /**
     * The list with the subscribed actions
     */
    List<Action<T>> actions = new ArrayList<Action<T>>();

    /**
     * Add a new action to the subscription list
     * 
     * @param action
     *            to run when this event is invoked
     */
    public void add(Action<T> action)
    {
        actions.add(action);
    }

    /**
     * Remove an added action
     * 
     * @param action
     *            to remove
     */
    public void remove(Action<T> action)
    {
        actions.remove(action);
    }

    /**
     * Clear all subscribed actions
     * 
     * OBSERVE: this will make all "WaitFor" actions return null
     */
    public void clear()
    {
        actions.clear();

        synchronized (waitForList)
        {
            for (Object obj : waitForList.keySet())
            {
                waitForList.put(obj, null);

                synchronized (obj)
                {
                    obj.notifyAll();
                }
            }

            waitForList.clear();
            throwingWaitForList.clear();
        }
    }

    /**
     * Invokes all the subscribed actions
     * 
     * @param arg
     *            the argument to pass to the actions
     */
    public void invoke(T arg)
    {
        MultipleExceptions exception = new MultipleExceptions();

        for (Action<T> action : actions)
            try
            {
                action.doAction(arg);
            }
            catch (Exception e)
            {
                exception.add(e);
            }

        synchronized (waitForList)
        {
            for (Object obj : waitForList.keySet())
            {
                waitForList.put(obj, arg);

                if (exception.getExceptions().length > 0)
                    throwingWaitForList.put(obj, exception);

                synchronized (obj)
                {
                    obj.notifyAll();
                }
            }
        }
    }

    /**
     * Waits for the event to be invoked, and returning the value of the
     * invoktion
     * 
     * OBSERVE: Return can be null if the event is cleared or any exception
     * (Interrupted) occurred while waiting
     * 
     * @return the value given in the invoke call or null
     */
    public T waitFor()
    {
        Object key = new Object();
        synchronized (waitForList)
        {
            waitForList.put(key, null);
        }
        synchronized (key)
        {
            try
            {
                key.wait();
            }
            catch (InterruptedException e)
            {
                return null;
            }
        }
        synchronized (waitForList)
        {
            T result = waitForList.get(key);
            waitForList.remove(key);
            return result;
        }
    }

    /**
     * Waits for the event to be invoked, and returning the value of the
     * invoktion
     * 
     * OBSERVE: Return can be null if the event is cleared or any exception
     * (Interrupted) occurred while waiting
     * 
     * @return the value given in the invoke call or null
     * @throws MultipleExceptions
     *             if any exception occurs on an action
     */
    public T throwingWaitFor() throws MultipleExceptions
    {
        Object key = new Object();
        synchronized (waitForList)
        {
            waitForList.put(key, null);
        }

        synchronized (key)
        {
            try
            {
                key.wait();
            }
            catch (InterruptedException e)
            {
                return null;
            }
        }

        synchronized (waitForList)
        {
            if (throwingWaitForList.containsKey(key))
                throw throwingWaitForList.get(key);

            T result = waitForList.get(key);
            waitForList.remove(key);
            return result;
        }
    }

    /**
     * An interface for the actions that can be added to the event
     * 
     * @param <T>
     *            the type of variables that can be passed to the action on
     *            event invoktion
     */
    public interface Action<T>
    {
        /**
         * Do stuff!
         * 
         * @param arg
         *            passed from the event invoktion
         */
        public void doAction(T arg);
    }

}