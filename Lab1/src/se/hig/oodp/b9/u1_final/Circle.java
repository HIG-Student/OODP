package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;

public class Circle extends Ellipse
{
    public Circle(Vertex2D position, double size)
    {
        super(position,size,size);
    }

    /**
     * @return the size
     */
    public double getSize()
    {
        return getWidth();
    }

    @Override
    public String toString()
    {
        return "Circle " + getCenter() + " r: " + getSize();
    }
}
