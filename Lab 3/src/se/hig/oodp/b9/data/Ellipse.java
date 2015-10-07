package se.hig.oodp.b9.data;

import java.awt.Graphics2D;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.model.PrimitivesPainter;

public class Ellipse extends Shape implements Scalable , Rotatable
{
    /**
     * The height of the ellipse
     */
    double height;
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
     * @param position
     *            The center of the ellipse
     * @param width
     *            The width of the ellipse
     * @param height
     *            The height of the ellipse
     */
    public Ellipse(Vertex2D position, double width, double height)
    {
        super(new Vertex2D[] { position });
        this.width = width;
        this.height = height;
    }

    /**
     * Makes an ellipse
     * 
     * @param position
     *            The center of the ellipse
     * @param width
     *            The width of the ellipse
     * @param height
     *            The height of the ellipse
     * @param rotation
     *            The rotation of the ellipse (degrees)
     */
    public Ellipse(Vertex2D position, double width, double height, double rotation)
    {
        super(new Vertex2D[] { position });
        this.width = width;
        this.height = height;
        this.rotation = rotation;
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
    public void rotate(double angle)
    {
        rotation += angle;
    }

    /**
     * Multiplies the width and height with the factors
     * 
     * @param factor_x
     *            how much to scale width with
     * @param factor_y
     *            how much to scale height with
     */
    @Override
    public void scale(double factor_x, double factor_y)
    {
        width *= factor_x;
        height *= factor_y;
    }

    @Override
    public String toString()
    {
        return "Ellipse " + getCenter() + " ; width: " + width + " ; height: " + height + " ; rot: " + rotation;
    }
    
    @Override
    public void draw(PrimitivesPainter pp)
    {
        pp.paintEllipse(nodes[0], width, height, rotation);
    }
}
