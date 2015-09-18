package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;

public class Point implements Shape
{
    /**
     * The position of this point
     */
    Vertex2D position;

    /**
     * Makes a point
     * 
     * @param position the position
     */
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
