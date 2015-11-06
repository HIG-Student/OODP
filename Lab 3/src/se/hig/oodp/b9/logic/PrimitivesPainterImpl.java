package se.hig.oodp.b9.logic;

import se.hig.oodp.PrimitivesPainter;
import se.hig.oodp.Vertex2D;

public class PrimitivesPainterImpl implements PrimitivesPainter
{
    Graphix g;

    public PrimitivesPainterImpl(Graphix g)
    {
        this.g = g;
    }

    @Override
    public void paintPoint(Vertex2D v)
    {
        if (g.getGraphics() == null)
            return;

        g.getGraphics().fillOval((int) v.getX(), (int) v.getY(), 5, 5);
    }

    @Override
    public void paintLine(Vertex2D v0, Vertex2D v1)
    {
        if (g.getGraphics() == null)
            return;

        g.getGraphics().drawLine((int) v0.getX(), (int) v0.getY(), (int) v1.getX(), (int) v1.getY());
    }

    @Override
    public void paintEllipse(Vertex2D v, double a, double b)
    {
        if (g.getGraphics() == null)
            return;

        g.getGraphics().drawOval((int) v.getX() - (int) a / 2, (int) v.getY() - (int) b / 2, (int) a, (int) b);
    }
}