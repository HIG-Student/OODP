package se.hig.oodp.b9;



public class Line {
	
	private Position pointA;
	private Position pointB;

	
	
	public Line(Position pointA, Position pointB){
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	public void moveTo(double x, double y){
		
	moveTo(new Position(x, y));
	
	}
	
	
	
	
	
	
	
	
	
	
	
	public void moveTo(Position position){
		
		Position pointA_diff = pointA.sub(getCenter());
		Position pointB_diff = pointB.sub(getCenter());
		
		pointA = pointA_diff.add(position);
		pointB = pointB_diff.add(position);
	}
	
	public void moveBy(double x,  double y){
		
		moveBy(new Position(x, y));
	}
	public void moveBy(Position position){
		
		pointA = pointA.add(position);
		pointB = pointB.add(position);
		}
	public Position getCenter(){
		
		return pointA.add(pointB.sub(pointA).div(2d));
	}
	
	public void remove(){
		
	}
	public void draw(){
		
	}
	@Override
	public String toString(){
		
		return "Line " + pointA+ " to "+pointB ;
	}
	
}


