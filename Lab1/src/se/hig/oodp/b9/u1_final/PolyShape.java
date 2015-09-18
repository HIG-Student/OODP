package se.hig.oodp.b9.u1_final;

import java.util.Arrays;

import se.hig.oodp.Vertex2D;

public abstract class PolyShape implements Shape , Scalable , Rotatable
{
    /**
     * The nodes the shape is made of
     */
    Vertex2D[] positions;

    /**
     * Makes a polyshape
     * 
     * @param positions the nodes the shape is made of
     */
    public PolyShape(Vertex2D[] positions)
    {
        this.positions = positions;
    }

    /**
     * @return the nodes the shape is made of
     */
    public Vertex2D[] getPositions()
    {
        return positions.clone();
    }
    
    @Override
    public void moveTo(Vertex2D position)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].moveBy(-center.getX(), -center.getY())
                    .moveBy(position.getX(), position.getY());
    }
    
    @Override
    public void moveBy(double x, double y)
    {
        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].moveBy(x, y);
    }
    
    @Override
    public void scale(double scale)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].scale(center, scale, scale);
    }
    
    @Override
    public void rotate(double angle)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].rotate(center, angle);
    }
    
    @Override
    public Vertex2D getCenter()
    {
        double sum_x = 0;
        double sum_y = 0;

        for (Vertex2D p : positions)
        {
            sum_x += p.getX();
            sum_y += p.getY();
        }

        return new Vertex2D(sum_x / positions.length, sum_y / positions.length);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " "
                + Arrays.toString(positions);
    }
}
