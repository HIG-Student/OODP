package se.hig.oodp.b9;

import java.util.ArrayList;
import java.util.List;

public class MultipleExceptions extends Exception
{
    private List<Exception> exceptions;;

    public MultipleExceptions()
    {
        this.exceptions = new ArrayList<Exception>();
    }

    public MultipleExceptions(List<Exception> exceptions)
    {
        this.exceptions = exceptions;
    }

    public MultipleExceptions(Exception[] exceptions)
    {
        this.exceptions = new ArrayList<Exception>();
        for (Exception e : exceptions)
            this.exceptions.add(e);
    }

    public Exception[] getExceptions()
    {
        return exceptions.toArray(new Exception[0]);
    }

    public MultipleExceptions add(Exception e)
    {
        exceptions.add(e);
        return this;
    }

    public MultipleExceptions remove(Exception e)
    {
        exceptions.remove(e);
        return this;
    }
}