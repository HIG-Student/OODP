package se.hig.oodp.b9.u2.d1;

/**
 *  Interface for scalable objects
 */
public interface Scalable
{
    /**
     * Scales the shape from its center
     * 
     * @param factor_x how much to scale the shape along x-axis
     * @param factor_y how much to scale the shape along y-axis
     */
    public void scale(double factor_x, double factor_y);
}
