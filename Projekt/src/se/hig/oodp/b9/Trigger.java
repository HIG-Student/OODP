package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;


public class Trigger
{
    Object waitHandle = new Object();

    List<Action> actions = new ArrayList<Action>();

    public void add(Action action)
    {
        actions.add(action);
    }

    public void remove(Action action)
    {
        actions.remove(action);
    }

    public void clear()
    {
        actions.clear();

        synchronized (waitHandle)
        {
            waitHandle.notifyAll();
        }
    }

    public void invoke()
    {
        for (Action action : actions)
            action.doAction();

        synchronized (waitHandle)
        {
            waitHandle.notifyAll();
        }
    }

    public void waitFor()
    {
        synchronized (waitHandle)
        {
            try
            {
                waitHandle.wait();
            }
            catch (InterruptedException e)
            {
                return;
            }
        }
    }

    public interface Action
    {
        public void doAction();
    }
}