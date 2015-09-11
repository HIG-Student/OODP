package se.hig.oodp.b9;

import se.hig.oodp.Vertex2D;

public class Position extends Vertex2D
{
	public Position(Vertex2D vertex)
	{
		super(vertex.getX(),vertex.getY());
	}
	
	public Position(double x, double y)
	{
		super (x,y);
	}
	
	public Position moveTo(double x, double y)
	{
		return new Position(super.moveTo(x,y));
	}
	
	public Position moveBy(double x, double y)
	{
		return new Position(super.moveBy(x,y));
	}
	
	public Position scale(Position center,double scale)
	{
		return new Position(super.scale(center, scale, scale));
	}
	
	public Position rotate(Position center,double angle)
	{
		return new Position(super.rotate(center, angle));
	}
	
	public double dist(Position other)
	{
		return super.dist(other);
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
	
