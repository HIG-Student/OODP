package se.hig.oodp;

/**
 * Class for modeling vertices. A vertex represents a position in the cartesian
 * 2D-space. It is an abstract concept and cannot be visualized.
 * 
 * @author Peter Jenke, Peter.Jenke@hig.se
 * @version 2015-04-15
 */
public class Vertex2D
{
    private double x;
    private double y;

    /**
     * Creates a new instance of Vertex2D at the position with coordinates x and
     * y.
     * 
     * @param x
     *            - x-coordinate
     * @param y
     *            - y-coordinate
     */
    public Vertex2D (double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this vertex.
     * 
     * @return x-coordinate
     */
    public double getX ()
    {
        return x;
    }

    /**
     * Returns the y-coordinate of this vertex.
     * 
     * @return y-coordinate
     */
    public double getY ()
    {
        return y;
    }

    /**
     * Returns a new vertex at the coordinates x and y.
     * 
     * @param x
     *            - x-coordinate
     * @param y
     *            - y-coordinate
     * @return vertex at position (x, y)
     */
    public Vertex2D moveTo (double x, double y)
    {
        return new Vertex2D (x, y);
    }

    /**
     * Returns a new vertex that is moved by d_x and d_y from the original
     * position.
     * 
     * @param d_x
     *            - moving distance in x-direction
     * @param d_y
     *            - moving distance in y-direction
     * @return vertex at position (x + d_x, y + d_y)
     */
    public Vertex2D moveBy (double d_x, double d_y)
    {
        return new Vertex2D (x + d_x, y + d_y);
    }

	/**
	 * Computes a new position by rotating the actual position around the center
	 * given by Vertex c in mathematically positive direction by angle (given in degree). After
	 * rotation, the method returns a new instance of Vertex at the new
	 * position.
	 * 
	 * The method does not change the position of this instance.
	 * 
	 * @param center
	 *            Vertex representing the rotation center
	 * @param angle
	 *            rotation angle given in degree in CCW
	 * @return new instance of Vertex with position after rotation
	 */
	public Vertex2D rotate (Vertex2D center, double angle)
	{
		double arc_angle = Math.toRadians (angle);

		Vertex2D trans1_v = moveBy (-center.x, -center.y);

		Vertex2D rot_v = new Vertex2D (
				trans1_v.x * Math.cos (arc_angle) - trans1_v.y * Math.sin (arc_angle),
				trans1_v.x * Math.sin (arc_angle) + trans1_v.y * Math.cos (arc_angle));

		return rot_v.moveBy (center.x, center.y);
	}

	/**
	 * Computes a new position by scaling the actual position with reference
	 * position given by Vertex c. After scaling, the method returns a new
	 * instance of Vertex at the new position.
	 * 
	 * The method does not change the position of this instance.
	 * 
	 * @param center
	 *            Vertex representing the reference position
	 * @param factor_x
	 *            scaling factor in x-direction
	 * @param factor_y
	 *            scaling factor in y-direction
	 * @return new instance of Vertex with position after scaling
	 */
	public Vertex2D scale (Vertex2D center, double factor_x, double factor_y)
	{
		Vertex2D trans1_v = moveBy (-center.x, -center.y);

		Vertex2D scal_v = new Vertex2D (trans1_v.x * factor_x, trans1_v.y
				* factor_y);

		return scal_v.moveBy (center.x, center.y);
	}
	
    /**
     * Returns the distance of this position to the position given by v.
     * 
     * @param v
     *            - vertex
     * @return distance from this vertex to v
     */
    public double dist (Vertex2D v)
    {
        return Math.sqrt ((v.x - x) * (v.x - x) + (v.y - y) * (v.y - y));
    }

    /**
     * Returns a string-representation of this vertex.
     * 
     * @return string-representation
     */
    @Override
    public String toString ()
    {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals (Object o)
    {
        if (!(o instanceof Vertex2D))
            return false;
        Vertex2D v = (Vertex2D) o;
        return v.x == this.x && v.y == this.y;
    }
}
