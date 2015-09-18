package se.hig.oodp.b9.u1_final;

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
    //move it from point a to a given  point b
    @Override
    public void moveTo(Vertex2D position)
    {
        this.position = position;
    }

    //Move the point  x,y "steps" from it's position by a given x,y
    @Override
    public void moveBy(double x, double y)
    {
        position = position.moveBy(x, y);
    }

    @Override
    public void remove()
    {
        // TODO To be implemented
    }

    @Override
    public void draw()
    {
        // TODO To be implemented
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " " + position;
    }
}
