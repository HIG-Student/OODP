package se.hig.oodp.b9.u1_bonus;

import java.util.Vector;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_3.PolyShape;


// TODO Tests!

public class Polygon extends PolyShape
{	
	Polygon(Vertex2D[] positions) 
	{
		super(positions);
	}
	
	public class Builder
	{
		public Vector<Vertex2D> positions = new Vector<Vertex2D>();
		
		public void clear()
		{
			positions.removeAllElements();
		}
		
		public void add(Vertex2D position)
		{
			positions.add(position);
		}
		
		public Polygon create() throws Exception
		{
			if( positions.size() <= 2 )
			{
				throw new Exception("You need more than three nodes in a polygon!");
			}
			
			return new Polygon((Vertex2D[])positions.toArray());
		}
	}
}
