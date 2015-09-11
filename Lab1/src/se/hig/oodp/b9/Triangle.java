package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Triangle 
{
	Vertex2D pointA;
	Vertex2D pointB;
	Vertex2D pointC;

	public Triangle(Vertex2D pointA, Vertex2D pointB, Vertex2D pointC)
	{
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
	}
	
	/**
	 * @return the pointA
	 */
	public Vertex2D getPointA() {
		return pointA;
	}

	/**
	 * @return the pointB
	 */
	public Vertex2D getPointB() {
		return pointB;
	}

	/**
	 * @return the pointC
	 */
	public Vertex2D getPointC() {
		return pointC;
	}

	public void moveTo(Vertex2D position)
	{
		Vertex2D center = getCenter();
		pointA = pointA.moveBy(-center.getX(), -center.getY()).moveBy(position.getX(), position.getY());
		pointB = pointB.moveBy(-center.getX(), -center.getY()).moveBy(position.getX(), position.getY());
		pointC = pointC.moveBy(-center.getX(), -center.getY()).moveBy(position.getX(), position.getY());
	}
	
	public void moveBy(double x,double y)
	{
		pointA = pointA.moveBy(x,y);
		pointB = pointB.moveBy(x,y);
		pointC = pointC.moveBy(x,y);
	}
	
	public void scale(double scale)
	{
		Vertex2D center = getCenter();
		pointA = pointA.scale(center, scale, scale);
		pointB = pointB.scale(center, scale, scale);
		pointC = pointC.scale(center, scale, scale);
	}
	
	public void rotate(double angle)
	{
		Vertex2D center = getCenter();
		pointA = pointA.rotate(center, angle);
		pointB = pointB.rotate(center, angle);
		pointC = pointC.rotate(center, angle);
	}
	
	public Vertex2D getCenter()
	{
		return new Vertex2D((pointA.getX() + pointB.getX() + pointC.getX()) / 3,
							(pointA.getY() + pointB.getY() + pointC.getY()) / 3);
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
		return "Triangle " + pointA + " to " + pointB + " to " + pointC;
	}
}




