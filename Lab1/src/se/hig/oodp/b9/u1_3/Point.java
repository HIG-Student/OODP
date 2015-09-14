package se.hig.oodp.b9.u1_3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.hig.oodp.Vertex2D;

public class Point implements Shape
{
	Vertex2D position;
	
	public Point(Vertex2D position)
	{
		this.position = position;
	}

	@Override
	public Vertex2D getCenter() 
	{
		return position;
	}

	@Override
	public void moveTo(Vertex2D position) 
	{
		this.position = position;
	}

	@Override
	public void moveBy(double x, double y) 
	{
		position = position.moveBy(x,y);
	}

	@Override
	public void remove() 
	{
		// TODO To be implemented
	}

	@Override
	public void draw() 
	{
		// TODO To be implemented
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + " " + position;
	}
}
