package se.hig.oodp.b9.d2;

import se.hig.oodp.Vertex2D;

public class Point
{
    Vertex2D position;

    public Point(Vertex2D position)
    {
        this.position = position;
    }

    public Vertex2D getCenter()
    {
        return position;
    }

    public void moveTo(Vertex2D position)
    {
        this.position = position;
    }

    public void moveBy(double x, double y)
    {
        position = position.moveBy(x, y);
    }
}
