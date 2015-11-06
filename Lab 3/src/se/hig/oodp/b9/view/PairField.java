package se.hig.oodp.b9.view;

public class PairField<T extends Number>
{
    public String name;
    public Pair<T>[] pairs;

    @SafeVarargs
    public PairField(String name, Pair<T>... pairs)
    {
        this.name = name;
        this.pairs = pairs;
    }

    private int index = 0;

    public T get()
    {
        return pairs[index++].value;
    }
}