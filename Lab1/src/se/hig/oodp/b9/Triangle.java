package se.hig.oodp.b9;



public class Triangle 
{
	
	private Position pointA;
	private Position pointB;
	private Position pointC;

	public Triangle(Position pointA, Position pointB, Position pointC)
	{
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
	}
	
	public void moveTo(Position position)
	{
		Position center = getCenter();
		pointA = pointA.sub(center).add(position);
		pointB = pointB.sub(center).add(position);
		pointC = pointC.sub(center).add(position);
	}
	
	public void moveBy(Position position)
	{
		pointA = pointA.add(position);
		pointB = pointB.add(position);
		pointC = pointC.add(position);
	}
	
	public void scale(double scale)
	{
		Position center = getCenter();
		pointA = pointA.sub(center).mul((1/3) * scale).add(center);
		pointB = pointB.sub(center).mul((1/3) * scale).add(center);
		pointC = pointC.sub(center).mul((1/3) * scale).add(center);
	}
	
	public void rotate(double angle)
	{
		Position center = getCenter();
		pointA = pointA.rotate(center, angle);
		pointB = pointB.rotate(center, angle);
		pointC = pointC.rotate(center, angle);
	}
	
	public Position getCenter()
	{
		return pointA.add(pointB).add(pointC).div(3d);
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




