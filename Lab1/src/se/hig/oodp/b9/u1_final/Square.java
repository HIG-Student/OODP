package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_final.Rectangle;

public class Square extends Rectangle
{
    public Square(Vertex2D position, double size)
    {
        super(position, size, size);
    }
    //add a square with a angle 
    public Square(Vertex2D position, double size, double rotation)
    {
        super(position, size, size, rotation);
    }
}
