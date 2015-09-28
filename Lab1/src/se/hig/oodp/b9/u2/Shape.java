package se.hig.oodp.b9.u2;

import java.util.Arrays;

import se.hig.oodp.Vertex2D;


/**
 * Generic class for shapes
 */
public abstract class Shape
{
    /**
     * The nodes that the shape is built with
     */
    Vertex2D[] nodes;
    
    /**
     * Creates a shape
     * 
     * @param nodes
     */
    public Shape(Vertex2D[] nodes)
    {
        this.nodes = nodes;
    }
    
    /**
     * Getter for the nodes (returns copy, so it can't be changed)
     * 
     * @return nodes
     */
    public Vertex2D[] getNodes()
    {
        return nodes.clone();
    }
    
    /**
     * Moves the shape to a position (aligns the center to the new position)
     * @param position the new position to move to
     */
    public void moveTo(Vertex2D position)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < nodes.length; i++)
            nodes[i] = nodes[i]
                    .moveBy(-center.getX(), -center.getY())
                    .moveBy(position.getX(), position.getY());
    }
    
    /**
     * Moves the shape
     * @param x distance along x-axis
     * @param y distance along y-axis
     */
    public void moveBy(double x,double y)
    {
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = nodes[i].moveBy(x, y);
    }
    
    /**
     * Returns the center based on the average positions
     * <br><br>
     * <code>Sum({Vertex2D} nodes) / #nodes</code>
     * @return the center of this shape
     */
    public Vertex2D getCenter()
    {
        double sum_x = 0;
        double sum_y = 0;

        for (Vertex2D p : nodes)
        {
            sum_x += p.getX();
            sum_y += p.getY();
        }

        return new Vertex2D(sum_x / nodes.length, sum_y / nodes.length);
    }
    
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " "
                + Arrays.toString(nodes);
    }
}
