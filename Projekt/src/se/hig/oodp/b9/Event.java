package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;

public class Event<T>
{
    public List<Action<T>> actions = new ArrayList<Action<T>>();

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
    }

    public interface Action<T>
    {
        public void doAction(T arg);
    }
}