package se.hig.oodp.b9;



public class Triangle {
	private Position pointA;
	private Position pointB;
	private Position pointC;

	
	
	public Triangle(Position pointA, Position pointB, Position pointC){
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
	}
	
	public void moveTo(double x, double y){
		
	moveTo(new Position(x, y));
	
	}
	
	
	
	
	
	
	
	
	
	
	
	public void moveTo(Position position){
		
		Position pointA_diff = pointA.sub(getCenter());
		Position pointB_diff = pointB.sub(getCenter());
		Position pointC_diff = pointC.sub(getCenter());
		
		pointA = pointA_diff.add(position);
		pointB = pointB_diff.add(position);
		pointC = pointC_diff.add(position);
	}
	
	public void moveBy(double x,  double y){
		
		moveBy(new Position(x, y));
	}
	public void moveBy(Position position){
		
		pointA = pointA.add(position);
		pointB = pointB.add(position);
		pointC = pointC.add(position);
		}
	public Position getCenter(){
		
		return pointA.add(pointB).add(pointC).div(3d);
		
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




