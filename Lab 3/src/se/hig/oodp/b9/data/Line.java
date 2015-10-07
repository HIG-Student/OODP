package se.hig.oodp.b9.data;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.model.PrimitivesPainter;

public class Line extends PolyShape
{
    /**
     * Makes a line <br>
     * Extends {@link PolyShape}
     * 
     * @param pointA
     *            start point
     * @param pointB
     *            end point
     */
    public Line(Vertex2D pointA, Vertex2D pointB)
    {
        super(new Vertex2D[] { pointA, pointB });
    }
    
    @Override
    public void draw(PrimitivesPainter pp)
    {
        pp.paintLine(nodes[0], nodes[1]);
    }
}