package se.hig.oodp.b9.d1;

import se.hig.oodp.Vertex2D;

public class Circle
{
    Vertex2D position;
    double size;

    public Circle(Vertex2D position, double size)
    {
        this.position = position;
        this.size = size;
    }

    /**
     * @return the size
     */
    public double getSize()
    {
        return size;
    }

    public void moveTo(Vertex2D position)
    {
        this.position = position;
    }

    public void moveBy(double x, double y)
    {
        position = this.position.moveBy(x, y);
    }

    public void scale(double scale)
    {
        size *= scale;
    }

    public Vertex2D getCenter()
    {
        return position;
    }

    @Override
    public String toString()
    {
        return "Circle " + position + " r: " + size;
    }
}
