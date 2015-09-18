package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;

public class Line extends PolyShape
{
    /**
     * Makes a line
     * <br>
     * Extends {@link PolyShape}
     * 
     * @param pointA start point
     * @param pointB end point
     */
    public Line(Vertex2D pointA, Vertex2D pointB)
    {
        super(new Vertex2D[] { pointA, pointB });
    }
}