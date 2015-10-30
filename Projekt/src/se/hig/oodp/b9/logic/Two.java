package se.hig.oodp.b9.logic;

import java.io.Serializable;

/**
 * Container for two generic data values <br>
 * <br>
 * Yes, i know, this name is really bad...
 * 
 * @param <T1>
 *            the first data balue type
 * @param <T2>
 *            the second data balue type
 */
@SuppressWarnings("serial")
public class Two<T1, T2> implements Serializable
{
    /**
     * Create new container
     * 
     * @param one
     *            the first data value
     * @param two
     *            the second data value
     */
    public Two(T1 one, T2 two)
    {
        this.one = one;
        this.two = two;
    }

    /**
     * The first data value
     */
    T1 one;
    /**
     * The second data value
     */
    T2 two;

    /**
     * Get the first data value
     * 
     * @return the first data value
     */
    public T1 getOne()
    {
        return one;
    }

    /**
     * Get the second data value
     * 
     * @return the second data value
     */
    public T2 getTwo()
    {
        return two;
    }
}
