package se.hig.oodp.b9.u1_bonus;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_3.Point;
import se.hig.oodp.b9.u1_3.Rotatable;
import se.hig.oodp.b9.u1_3.Scalable;

public class Ellipse extends Point implements Scalable , Rotatable
{
    double width;
    double height;
    double rotation;

    public Ellipse(Vertex2D position, double width, double height)
    {
        super(position);
        this.width = width;
        this.height = height;
    }

    public Ellipse(Vertex2D position, double width, double height,
            double rotation)
    {
        super(position);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public double getRotation()
    {
        return rotation;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    @Override
    public void rotate(double angle)
    {
        rotation += angle;
    }

    @Override
    public void scale(double scale)
    {
        width *= scale;
        height *= scale;
    }

    @Override
    public String toString()
    {
        return "Ellipse " + getCenter() + " ; width: " + width + " ; height: "
                + height + " ; rot: " + rotation;
    }
}
