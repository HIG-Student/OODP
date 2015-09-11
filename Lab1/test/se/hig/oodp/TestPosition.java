package se.hig.oodp;

import static org.junit.Assert.*;

import org.junit.Test;

import se.hig.oodp.b9.Position;

public class TestPosition {
		

		
	@Test
	public void testInit() {
		double x; 
		double y;
		
		for(double i=0.1;i<10;i++){
		x=i;
		y=i;
		
		Position p = new Position(x, y);
		
		
		assertTrue("this position are not corect",p.getX() == x && p.getY() == y);
	}
	}
	@Test 
	public void testAdd(){
		Position p = new Position(1, 1);
		
		for (double i =-5;i<5;i++){
			
			assertEquals(new Position(i+1,i+1), p.add(new Position(i,i)));
			
			
		}
		
	}
	@Test
	public void testSub(){
		
	Position p = new Position(10, 10);
		
		for (double i =5;i>-5;i--){
		
			assertEquals(new Position(10-i,10-i), p.sub(new Position(i,i)));
		
		
	}
	}
	@Test
	public void testMul(){
		
		Position p = new Position(1, 1);
		
		for(double i=1;i<50;i++){
			
			assertEquals(new Position(i*1,i*1), p.mul(i));
			
			
		}
		
		
	}
	public void testDiv(){
		
		Position p =new Position(100, 100);
		
		for(double i=1; i<10; i++){
			
			assertEquals(new Position(100/i,100/i), p.div(i));
			
		}
		
	}
	
	}


