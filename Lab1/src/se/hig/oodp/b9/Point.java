package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Point 
{
	Vertex2D position;
	
	public Point(Vertex2D position)
	{
		this.position = position;
	}

	public void moveTo(Vertex2D position)
	{
		this.position = position;
	}
	
	public void moveBy(Vertex2D position)
	{
		this.position = position;
	}
	
	public Vertex2D getCenter()
	{
		return this.position;
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
		return "Point " + position;
	}
}
