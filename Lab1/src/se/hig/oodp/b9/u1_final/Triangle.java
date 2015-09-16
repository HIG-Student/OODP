package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;

public class Triangle extends PolyShape
{
    public Triangle(Vertex2D pointA, Vertex2D pointB, Vertex2D pointC)
    {
        super(new Vertex2D[] { pointA, pointB, pointC });
    }
}
