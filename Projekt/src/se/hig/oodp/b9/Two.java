package se.hig.oodp.b9;

import java.io.Serializable;

public class Two<T1, T2> implements Serializable
{
    public Two(T1 one, T2 two)
    {
        this.one = one;
        this.two = two;
    }

    public T1 one;
    public T2 two;
}
