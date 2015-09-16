package se.hig.oodp.b9.u1_2;

import se.hig.oodp.Vertex2D;

public class Circle extends Point
{
    double size;

    public Circle(Vertex2D position, double size)
    {
        super(position);
        this.size = size;
    }

    /**
     * @return the size
     */
    public double getSize()
    {
        return size;
    }

    public void scale(double scale)
    {
        size *= scale;
    }

    @Override
    public void draw()
    {

    }

    @Override
    public String toString()
    {
        return "Circle " + getCenter() + " r: " + size;
    }
}
