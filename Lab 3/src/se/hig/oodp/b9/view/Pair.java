package se.hig.oodp.b9.view;

public class Pair<T extends Number>
{
    public Pair()
    {
        this.name = "";
        this.start = 0;
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.step = 1;
    }

    public Pair(String name)
    {
        this.name = name;
        this.start = 0;
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.step = 1;
    }

    public Pair(String name, T start, T max, T min, T step)
    {
        this.name = name;
        this.start = start;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public String name;
    public T value;

    public Number start;
    public Number max;
    public Number min;
    public Number step;
}