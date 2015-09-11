package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;



public class Line 
{
	
	private Position pointA;
	private Position pointB;

	public Line(Position pointA, Position pointB)
	{
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	public void moveTo(Position position)
	{
		Position center = getCenter();
		pointA = pointA.sub(center).add(position);
		pointB = pointB.sub(center).add(position);
	}
	
	public void moveBy(Position position)
	{
		pointA = pointA.add(position);
		pointB = pointB.add(position);
	}
	
	public void scale(double scale)
	{
		Position center = getCenter();
		pointA = pointA.sub(center).mul(0.5 * scale).add(center);
		pointB = pointB.sub(center).mul(0.5 * scale).add(center);
	}
	
	public void rotate(double angle)
	{
		Position center = getCenter();
		pointA = pointA.rotate(center, angle);
		pointB = pointB.rotate(center, angle);
	}
	
	public Position getCenter()
	{
		return pointA.add(pointB.sub(pointA).div(2d));
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


