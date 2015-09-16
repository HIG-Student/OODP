package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_final.Rotatable;
import se.hig.oodp.b9.u1_final.Scalable;

public class Ellipse implements Shape , Scalable , Rotatable
{
    double height;
    Vertex2D position;
    double rotation;
    double width;

    public Ellipse(Vertex2D position, double width, double height)
    {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Ellipse(Vertex2D position, double width, double height,
            double rotation)
    {
        this.position = position;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    @Override
    public void draw()
    {
        // TODO To be implemented
    }

    @Override
    public Vertex2D getCenter()
    {
        return position;
    }

    public double getHeight()
    {
        return height;
    }

    public double getRotation()
    {
        return rotation;
    }

    public double getWidth()
    {
        return width;
    }

    @Override
    public void moveBy(double x, double y)
    {
        position = position.moveBy(x, y);
    }

    @Override
    public void moveTo(Vertex2D position)
    {
        this.position = position;
    }

    @Override
    public void remove()
    {
        // TODO To be implemented
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
