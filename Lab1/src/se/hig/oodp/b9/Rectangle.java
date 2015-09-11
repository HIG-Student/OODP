package se.hig.oodp.b9;

public class Rectangle 
{
	Position position;
	double rotation;
	double width;
	double height;

	public Rectangle(Position position,double width,double height)
	{
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Position position,double width,double height,double rotation)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
	}
	
	public void moveTo(Position position)
	{
		this.position = position;
	}
	
	public void moveBy(Position position)
	{
		this.position = this.position.add(position);
	}
	
	public void scale(double scale)
	{
		this.width *= scale;
		this.height *= scale;
	}
	
	public void rotate(double angle)
	{
		this.rotation += angle;
	}
	
	public Position getCenter()
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
		return "Rectangle " + position + " @ " + rotation + " " + width + " x " + height;
	}
}
