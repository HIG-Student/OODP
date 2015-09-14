package se.hig.oodp.b9.u1_1;

import se.hig.oodp.Vertex2D;

public class Line 
{
	Vertex2D pointA;
	Vertex2D pointB;

	public Line(Vertex2D pointA, Vertex2D pointB)
	{
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	/**
	 * @return the pointA
	 */
	public Vertex2D getPointA() 
	{
		return pointA;
	}

	/**
	 * @return the pointB
	 */
	public Vertex2D getPointB() 
	{
		return pointB;
	}

	public void moveTo(Vertex2D position)
	{
		Vertex2D center = getCenter();
		pointA = pointA.moveBy(-center.getX(), -center.getY()).moveBy(position.getX(), position.getY());
		pointB = pointB.moveBy(-center.getX(), -center.getY()).moveBy(position.getX(), position.getY());
	}
	
	public void moveBy(double x,double y)
	{
		pointA = pointA.moveBy(x,y);
		pointB = pointB.moveBy(x,y);
	}
	
	public void scale(double scale)
	{
		Vertex2D center = getCenter();
		pointA = pointA.scale(center, scale, scale);
		pointB = pointB.scale(center, scale, scale);
	}
	
	public void rotate(double angle)
	{
		Vertex2D center = getCenter();
		pointA = pointA.rotate(center, angle);
		pointB = pointB.rotate(center, angle);
	}
	
	public Vertex2D getCenter()
	{
		return pointB.scale(pointA, 0.5, 0.5);
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
		return "Line " + pointA + " to " + pointB ;
	}
}


