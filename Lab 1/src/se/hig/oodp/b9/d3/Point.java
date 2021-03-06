package se.hig.oodp.b9.d3;

import se.hig.oodp.Vertex2D;

public class Point implements Shape
{
    Vertex2D position;

    public Point(Vertex2D position)
    {
        this.position = position;
    }

    @Override
    public Vertex2D getCenter()
    {
        return position;
    }

    @Override
    public void moveTo(Vertex2D position)
    {
        this.position = position;
    }

    @Override
    public void moveBy(double x, double y)
    {
        position = position.moveBy(x, y);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " " + position;
    }
}
