package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Representing an event
 * 
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
        for (Action<T> action : actions)
            action.doAction(arg);

        synchronized (waitForList)
        {
            for (Object obj : waitForList.keySet())
            {
                waitForList.put(obj, arg);

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