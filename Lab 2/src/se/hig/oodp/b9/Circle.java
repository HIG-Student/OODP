package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Circle extends Ellipse
{
    /**
     * Extends {@link Ellipse} and setting both the width and height to "size"
     * 
     * @param position The center of the circle
     * @param size The radius of the circle
     */
    public Circle(Vertex2D position, double size)
    {
        super(position,size,size);
    }

    /**
     * @return the size (diameter)
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
