/**
 * 
 */
package se.hig.oodp.b9.view;

public class Pair<T>
{
    public Pair(String name,Class<T> valueClass)
    {
        this.name = name;
        this.valueClass = valueClass;
    }
    public String name;
    public Class<T> valueClass;
    public T value;
}