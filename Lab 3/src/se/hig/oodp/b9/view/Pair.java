/**
 * 
 */
package se.hig.oodp.b9.view;

public class Pair<T>
{
    public enum PairDataTypes
    {
        INTEGER, DOUBLE, VERTEX2D
    }

    public enum PairConstraintTypes
    {
        NONE, POSITIVE, POSITIVE_NOT_ZERO
    }

    public Pair(String name, PairDataTypes pairDataType)
    {
        this.name = name;
        this.pairDataType = pairDataType;
    }

    public Pair(String name, PairDataTypes pairDataType, PairConstraintTypes pairConstraintType)
    {
        this.name = name;
        this.pairDataType = pairDataType;
        this.pairConstraintType = pairConstraintType;
    }

    public String name;
    public PairDataTypes pairDataType;
    public PairConstraintTypes pairConstraintType = PairConstraintTypes.NONE;
    public T value;
}