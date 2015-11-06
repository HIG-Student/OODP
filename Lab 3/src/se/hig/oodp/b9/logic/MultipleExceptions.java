package se.hig.oodp.b9.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * An exception that contains multiple exceptions
 */
@SuppressWarnings("serial")
public class MultipleExceptions extends Exception
{
    /**
     * List of exceptions
     */
    private List<Exception> exceptions;

    /**
     * Create this exception
     */
    public MultipleExceptions()
    {
        this.exceptions = new ArrayList<Exception>();
    }

    /**
     * Create this exception and populate the inner exceptions
     * 
     * @param exceptions
     *            the exceptions to add
     */
    public MultipleExceptions(List<Exception> exceptions)
    {
        this.exceptions = exceptions;
    }

    /**
     * Create this exception and populate the inner exceptions
     * 
     * @param exceptions
     *            the exceptions to add
     */
    public MultipleExceptions(Exception[] exceptions)
    {
        this.exceptions = new ArrayList<Exception>();
        for (Exception e : exceptions)
            this.exceptions.add(e);
    }

    /**
     * Get the inner exceptions
     * 
     * @return the inner exceptions
     */
    public Exception[] getExceptions()
    {
        return exceptions.toArray(new Exception[0]);
    }

    /**
     * Add exception
     * 
     * @param e
     *            exception to add
     * @return this object (for chaining)
     */
    public MultipleExceptions add(Exception e)
    {
        exceptions.add(e);
        return this;
    }

    /**
     * Remove exceptions
     * 
     * @param e
     *            exception to remove
     * @return this object (for chaining)
     */
    public MultipleExceptions remove(Exception e)
    {
        exceptions.remove(e);
        return this;
    }
}