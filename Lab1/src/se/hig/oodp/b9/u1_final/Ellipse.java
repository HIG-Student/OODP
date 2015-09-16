package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_final.Rotatable;
import se.hig.oodp.b9.u1_final.Scalable;

public class Ellipse implements Shape , Scalable , Rotatable
{
    Vertex2D position;
    double width;
    double height;
    double rotation;

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
    public void draw()
    {
        // TODO To be implemented
    }

    @Override
    public String toString()
    {
        return "Ellipse " + getCenter() + " ; width: " + width + " ; height: "
                + height + " ; rot: " + rotation;
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
    public void remove()
    {
        // TODO To be implemented
    }
}
