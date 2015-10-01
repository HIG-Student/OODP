package se.hig.oodp.b9.data;

import se.hig.oodp.Vertex2D;

/**
 * Generic class for shapes built with polygons
 */
public abstract class PolyShape extends Shape implements Rotatable , Scalable
{
    /**
     * Makes a polyshape
     * 
     * @param nodes
     *            the shape is made of
     */
    public PolyShape(Vertex2D[] nodes)
    {
        super(nodes);
    }

    @Override
    public void scale(double factor_x, double factor_y)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < nodes.length; i++)
            nodes[i] = nodes[i].scale(center, factor_x, factor_y);
    }

    @Override
    public void rotate(double angle)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < nodes.length; i++)
            nodes[i] = nodes[i].rotate(center, angle);
    }
}
