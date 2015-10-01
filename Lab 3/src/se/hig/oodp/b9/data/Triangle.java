package se.hig.oodp.b9.data;

import se.hig.oodp.Vertex2D;

public class Triangle extends PolyShape
{
    /**
     * Makes a triangle <br>
     * Extends {@link PolyShape}
     * 
     * @param pointA
     *            the first point of the triangle
     * @param pointB
     *            the second point of the triangle
     * @param pointC
     *            the third point of the triangle
     */
    public Triangle(Vertex2D pointA, Vertex2D pointB, Vertex2D pointC)
    {
        super(new Vertex2D[] { pointA, pointB, pointC });
    }
}
