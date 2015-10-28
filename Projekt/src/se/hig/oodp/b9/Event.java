package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Event<T>
{
    private HashMap<Object, T> waitForList = new HashMap<Object, T>();

    private List<Action<T>> actions = new ArrayList<Action<T>>();

    public void add(Action<T> action)
    {
        actions.add(action);
    }

    public void remove(Action<T> action)
    {
        actions.remove(action);
    }

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
            }
        }
        synchronized (waitForList)
        {
            T result = waitForList.get(key);
            waitForList.remove(key);
            return result;
        }
    }

    public interface Action<T>
    {
        public void doAction(T arg);
    }
}