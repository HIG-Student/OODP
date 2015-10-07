package se.hig.oodp.b9.data;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.model.PrimitivesPainter;

public class Point extends Shape
{
    /**
     * Makes a point <br>
     * Extends {@link Shape}
     * 
     * @param position
     *            the position
     */
    public Point(Vertex2D position)
    {
        super(new Vertex2D[] { position });
    }
    
    @Override
    public void draw(PrimitivesPainter pp)
    {
        pp.paintEllipse(nodes[0], 5, 5, 0);
    }
}
