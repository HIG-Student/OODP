package se.hig.oodp.b9.u1_2;

import se.hig.oodp.Vertex2D;

public class Point
{
	Vertex2D position;
	
	public Point(Vertex2D position)
	{
		this.position = position;
	}

	public Vertex2D getCenter() 
	{
		return position;
	}

	public void moveTo(Vertex2D position) 
	{
		this.position = position;
	}

	public void moveBy(double x, double y) 
	{
		position = position.moveBy(x, y);
	}

	public void remove() 
	{
		// TODO To be implemented
	}

	public void draw() 
	{
		// TODO To be implemented
	}
}
