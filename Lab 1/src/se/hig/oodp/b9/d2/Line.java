package se.hig.oodp.b9.d2;

import se.hig.oodp.Vertex2D;

public class Line extends PolyShape
{
    public Line(Vertex2D pointA, Vertex2D pointB)
    {
        super(new Vertex2D[] { pointA, pointB });
    }
}
