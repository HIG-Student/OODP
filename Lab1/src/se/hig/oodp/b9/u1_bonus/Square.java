package se.hig.oodp.b9.u1_bonus;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_3.Rectangle;

public class Square extends Rectangle
{
	public Square(Vertex2D position, double size) 
	{
		super(position, size, size);
	}
	
	public Square(Vertex2D position, double size, double rotation) 
	{
		super(position, size, size,rotation);
	}
}
