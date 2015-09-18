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
    
 // Create a ellipse by give it a position,width and height
    public Ellipse(Vertex2D position, double width, double height)
    {
        this.position = position;
        this.width = width;
        this.height = height;
    }
    
// Create a ellipse with a angle by give it a position,width,height and angel 
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

    //get the position for the center
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

    //move the ellipse x,y "steps" from it position by a given x,y
    @Override
    public void moveBy(double x, double y)
    {
        position = position.moveBy(x, y);
    }
    
    // Move the ellipse from point a to a given point b
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

    //Rotate the Ellipse around it's center by a given angle
    @Override
    public void rotate(double angle)
    {
        rotation += angle;
    }

    // Change the size on width and height by (double) 
    //@Override
    /**
     * @Scale: Change the size on width and height by (double) 
     */
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
