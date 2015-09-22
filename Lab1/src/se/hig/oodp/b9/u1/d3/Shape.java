package se.hig.oodp.b9.u1.d3;

import se.hig.oodp.Vertex2D;

public interface Shape
{
    public Vertex2D getCenter();

    public void moveTo(Vertex2D position);

    public void moveBy(double x, double y);
}
