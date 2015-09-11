package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Position extends Vertex2D
{
	
	public Position(double x, double y)
	{
		super (x,y);
	}
	
	public Position add(Position pos) 
	{
		return new Position(this.getX()+pos.getX(),this.getY()+pos.getY());
	}
	
	public Position sub(Position pos) 
	{
		return new Position(this.getX()-pos.getX(),this.getY()-pos.getY());
	}
	
	public Position div(double scale) 
	{
		return new Position(this.getX()/scale,this.getY()/scale);
	}
	
	public Position mul(double scale) 
	{
		return new Position(this.getX()*scale,this.getY()*scale);
	}
	
	public boolean isCloseTo(Position position,double epsilon)
	{
		return	Math.abs(this.getX() - position.getX()) <= epsilon &&
				Math.abs(this.getY() - position.getY()) <= epsilon;
	}
}
	
