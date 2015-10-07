/**
 * 
 */
package se.hig.oodp.b9.model;

import se.hig.oodp.Vertex2D;

public interface PrimitivesPainter
{
    public void paintLine(Vertex2D a,Vertex2D b);
    public void paintPolygon(Vertex2D[] nodes);
    public void paintEllipse(Vertex2D center,double width,double height,double rotation);
}