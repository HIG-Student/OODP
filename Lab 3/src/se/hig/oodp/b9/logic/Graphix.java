package se.hig.oodp.b9.logic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Stack;

public class Graphix
{
    Graphics2D g;

    Stack<AffineTransform> transformations = new Stack<AffineTransform>();

    public void push()
    {
        if (g == null)
            return;

        transformations.add(g.getTransform());
    }

    public void pop()
    {
        if (g == null)
            return;

        if (!transformations.isEmpty())
            g.transform(transformations.pop());
    }

    public void setGraphics(Graphics2D g)
    {
        this.g = g;
        transformations.clear();
    }

    public Graphics2D getGraphics()
    {
        return g;
    }
}
