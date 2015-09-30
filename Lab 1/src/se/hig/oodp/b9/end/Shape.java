package se.hig.oodp.b9.end;

import se.hig.oodp.Vertex2D;

public interface Shape
{
    /**
     * @return the center
     */
    public Vertex2D getCenter();

    /**
     * Moves this shape to a new position
     * 
     * @param position the position to move to
     */
    public void moveTo(Vertex2D position);

    /**
     * Moves this shape by a specified distance
     * 
     * @param x distance along the x axis
     * @param y distance along the y axis
     */
    public void moveBy(double x, double y);
}
