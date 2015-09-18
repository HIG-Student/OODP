package se.hig.oodp.b9.u1_2;

import java.util.Arrays;

import se.hig.oodp.Vertex2D;

public abstract class PolyShape
{
    Vertex2D[] positions;

    public PolyShape(Vertex2D[] positions)
    {
        this.positions = positions;
    }

    public Vertex2D[] getPositions()
    {
        return positions.clone();
    }

    public void moveTo(Vertex2D position)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].moveBy(-center.getX(), -center.getY())
                    .moveBy(position.getX(), position.getY());
    }

    public void moveBy(double x, double y)
    {
        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].moveBy(x, y);
    }

    public void scale(double scale)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].scale(center, scale, scale);
    }

    public void rotate(double angle)
    {
        Vertex2D center = getCenter();

        for (int i = 0; i < positions.length; i++)
            positions[i] = positions[i].rotate(center, angle);
    }

    // TODO Better centroid
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
