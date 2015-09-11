package se.hig.oodp.b9;

public class Point {
	
	private Position position;
	
	public Point(Position position){
		
		this.position = position;
	}

	public void moveTo(double x, double y){
	
		moveTo(new Position(x, y));
	}
	
	public void moveTo(Position position){
		this.position = position;
	}
	
	public void moveBy(double x,  double y){
		
		moveBy(new Position(x, y));
	}
	public void moveBy(Position position){
		
		this.position = position;
		}
	public Position getCenter(){
		
		return this.position;
	}
	
	public void remove(){
		
	}
	public void draw(){
		
	}
	@Override
	public String toString(){
		
		return "point" + position;
	}
	
}
