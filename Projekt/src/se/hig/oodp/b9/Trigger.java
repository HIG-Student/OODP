package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.hig.oodp.b9.Event.Action;

public class Trigger
{
    Object waitHandle = new Object();

    public List<Action> actions = new ArrayList<Action>();

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

        waitHandle.notifyAll();
    }

    public void waitFor() throws InterruptedException
    {
        waitHandle.wait();
    }

    public interface Action
    {
        public void doAction();
    }
}