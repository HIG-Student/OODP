package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.hig.oodp.b9.Event.Action;

public class Trigger
{
    private Object waitHandle = new Object();

    private List<Action> actions = new ArrayList<Action>();

    public void add(Action action)
    {
        actions.add(action);
    }

    public void remove(Action action)
    {
        actions.remove(action);
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
            }
        }
    }

    public interface Action
    {
        public void doAction();
    }
}