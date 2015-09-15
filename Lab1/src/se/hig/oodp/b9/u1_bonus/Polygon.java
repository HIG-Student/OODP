package se.hig.oodp.b9.u1_bonus;

import java.util.Vector;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_3.PolyShape;

public class Polygon extends PolyShape
{	
	Polygon(Vertex2D[] positions) 
	{
		super(positions);
	}
	
	public static class Builder
	{
		public Vector<Vertex2D> positions = new Vector<Vertex2D>();
		
		public Builder() { };
		
		public Builder clear()
		{
			positions.removeAllElements();
			
			return this;
		}
		
		public Builder add(Vertex2D position)
		{
			positions.add(position);
			
			return this;
		}
		
		public Polygon create() throws Exception
		{
			if( positions.size() <= 2 )
			{
				throw new Exception("You need more than two nodes in a polygon!");
			}
			
			Vertex2D[] arr = new Vertex2D[positions.size()];
			positions.toArray(arr);
			return new Polygon(arr);
		}
	}
}
