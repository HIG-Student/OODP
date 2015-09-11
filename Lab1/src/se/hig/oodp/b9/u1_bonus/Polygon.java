package se.hig.oodp.b9.u1_bonus;

import java.util.Vector;

import se.hig.oodp.Vertex2D;


// TODO Tests!

public class Polygon 
{
	Vertex2D[] positions;
	
	Polygon(Vertex2D[] positions)
	{
		this.positions = positions.clone();
	}
	
	public Vertex2D[] getPositions()
	{
		return positions.clone();
	}
	
	public void moveTo(Vertex2D position)
	{
		Vertex2D center = getCenter();
		
		for(int i = 0; i < positions.length;i++)
			positions[i] = positions[i].moveBy(-center.getX(), -center.getY()).moveBy(position.getX(), position.getY());
	}
	
	public void moveBy(double x, double y)
	{
		for(int i = 0; i < positions.length;i++)
			positions[i] = positions[i].moveBy(x, y);
	}
	
	public void scale(double scale)
	{
		Vertex2D center = getCenter();
		
		for(int i = 0; i < positions.length;i++)
			positions[i] = positions[i].scale(center,scale,scale);
	}
	
	public void rotate(double angle)
	{
		Vertex2D center = getCenter();
		
		for(int i = 0; i < positions.length;i++)
			positions[i] = positions[i].rotate(center,angle);
	}
	
	// TODO Fix better centroid
	
	public Vertex2D getCenter()
	{
		double sum_x = 0;
		double sum_y = 0;
		
		for(Vertex2D p : positions)
		{
			sum_x += p.getX();
			sum_y += p.getY();
		}
		
		return new Vertex2D(sum_x / positions.length , sum_y / positions.length);
	}
	
	public void remove()
	{
		
	}
	
	public void draw()
	{
		
	}
	
	@Override
	public String toString()
	{
		String positions_str = "";
		
		for(Vertex2D p : positions)
		{
			positions_str += p.toString() + " to ";
		}
		
		return "Polygon " + positions_str.substring(0, positions_str.length() - 4);
	}
	
	public class Builder
	{
		public Vector<Vertex2D> positions = new Vector<Vertex2D>();
		
		public void clear()
		{
			positions.removeAllElements();
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
