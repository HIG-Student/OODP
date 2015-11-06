package se.hig.oodp.b9.logic;

import java.util.ArrayList;
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
     * Occurrence of this exception will throw an InterruptdException instead of
     * the MultipleExceptions
     */
    private static final MultipleExceptions INTERRUPTED = new MultipleExceptions();

    /**
     * The list containing wait results for the "WaitFor" method
     */
    List<WaitResult> waitForList = new ArrayList<WaitResult>();

    /**
     * Results to be sent to those waiting for an event
     */
    private class WaitResult
    {
        /**
         * The data to return
         */
        public T returnData = null;
        /**
         * Exceptions that have occurred
         */
        public MultipleExceptions exceptions = null;
    }

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
        synchronized (actions)
        {
            actions.add(action);
        }
    }

    /**
     * Remove an added action
     * 
     * @param action
     *            to remove
     */
    public void remove(Action<T> action)
    {
        synchronized (actions)
        {
            actions.remove(action);
        }
    }

    /**
     * Clear all subscribed actions
     * 
     * OBSERVE: this will make all "WaitFor" actions return null
     */
    public void clear()
    {
        synchronized (actions)
        {
            actions.clear();

            synchronized (waitForList)
            {
                for (WaitResult obj : waitForList)
                {
                    obj.exceptions = INTERRUPTED;

                    synchronized (obj)
                    {
                        obj.notifyAll();
                    }
                }

                waitForList.clear();
            }
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

        synchronized (actions)
        {
            for (Action<T> action : actions)
                try
                {
                    action.doAction(arg);
                }
                catch (Exception e)
                {
                    exception.add(e);
                }
        }

        synchronized (waitForList)
        {
            for (WaitResult obj : waitForList)
            {
                synchronized (obj)
                {
                    obj.returnData = arg;

                    if (exception.getExceptions().length > 0)
                        obj.exceptions = exception;

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
        WaitResult result = new WaitResult();

        synchronized (result)
        {
            synchronized (waitForList)
            {
                waitForList.add(result);
            }

            try
            {
                result.wait();
            }
            catch (InterruptedException e)
            {
                return null;
            }

            return result.returnData;
        }
    }

    /**
     * Waits for the event to be invoked, and returning the value of the
     * invoktion
     * 
     * @return the value given in the invoke call or null
     * @throws MultipleExceptions
     *             if any exception occurs on an action
     * @throws InterruptedException
     *             thrown if 'clear' was called
     */
    public T throwingWaitFor() throws MultipleExceptions, InterruptedException
    {
        WaitResult result = new WaitResult();

        synchronized (result)
        {
            synchronized (waitForList)
            {
                waitForList.add(result);
            }

            try
            {
                result.wait();
            }
            catch (InterruptedException e)
            {
                throw e;
            }

            if (result.exceptions != null)
                if (result.exceptions == INTERRUPTED)
                    throw new InterruptedException();
                else
                    throw result.exceptions;

            return result.returnData;
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