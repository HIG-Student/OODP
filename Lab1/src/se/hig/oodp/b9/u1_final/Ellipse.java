package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_final.Rotatable;
import se.hig.oodp.b9.u1_final.Scalable;

public class Ellipse implements Shape , Scalable , Rotatable
{
    /**
     * The height of the ellipse
     */
    double height;
    /**
     * The position of the ellipse (center)
     */
    Vertex2D position;
    /**
     * The rotation of the ellipse
     */
    double rotation;
    /**
     * The width of the ellipse
     */
    double width;
    
    /**
     * Makes an ellipse
     * 
     * @param position The center of the ellipse
     * @param width The width of the ellipse
     * @param height The height of the ellipse
     */
    public Ellipse(Vertex2D position, double width, double height)
    {
        this.position = position;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Makes an ellipse
     * 
     * @param position The center of the ellipse
     * @param width The width of the ellipse
     * @param height The height of the ellipse
     * @param rotation The rotation of the ellipse (degrees)
     */
    public Ellipse(Vertex2D position, double width, double height, double rotation)
    {
        this.position = position;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }
    
    @Override
    public Vertex2D getCenter()
    {
        return position;
    }
 
    /**
     * @return the height
     */
    public double getHeight()
    {
        return height;
    }

  
    /**
     * @return the rotation
     */
    public double getRotation()
    {
        return rotation;
    }

    /**
     * @return the width
     */
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
    public void rotate(double angle)
    {
        rotation += angle;
    }

    /**
     * Multiplies the width and height with "scale"
     */
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
