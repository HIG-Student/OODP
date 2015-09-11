package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Circle 
{
	Vertex2D position;
	double size;
	
	public Circle(Vertex2D position,double size)
	{
		this.position = position;
		this.size = size;
	}
	
	public void moveTo(Vertex2D position)
	{
		this.position = position;
	}
	
	public void moveBy(double x,double y)
	{
		position = this.position.moveBy(x,y);
	}
	
	public void scale(double scale)
	{
		size *= scale;
	}
	
	public Vertex2D getCenter()
	{
		return position;
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
		return "Circle " + position + " r: " + size;
	}
}
