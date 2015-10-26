package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;

public class Trigger
{
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
    }

    public interface Action
    {
        public void doAction();
    }
}