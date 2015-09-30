package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Point extends Shape
{
    /**
     * Makes a point
     * <br>
     * Extends {@link Shape}
     * 
     * @param position the position
     */
    public Point(Vertex2D position)
    {
        super(new Vertex2D[]{position});
    }
}
