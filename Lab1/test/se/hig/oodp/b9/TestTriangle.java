package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;

public class TestTriangle {

	Triangle t;
	
	@Before
	public void setUp(){
		
      t = new Triangle(new Vertex2D(27, 24), new Vertex2D(47, 40), new Vertex2D(65, 20));
		
	}
	
	@Test
	public void testCenter() {
		
		assertTrue("The center is off",new Vertex2D(46, 28).dist(t.getCenter()) <= 0.5);
		
		
		
	}
	

	@Test 
	public void testMoveTo(){
		
		Triangle t = new Triangle(new Vertex2D(27, 24), new Vertex2D(47, 40), new Vertex2D(65, 20));
		t.moveTo(new Vertex2D(56,38));
		

		assertTrue("The center is off",new Vertex2D(56, 38).dist(t.getCenter()) <= 0.5);
		assertTrue("PointA is off",new Vertex2D(37, 34).dist(t.getPointA()) <= 0.5);
		assertTrue("PointB is off",new Vertex2D(57, 50).dist(t.getPointB()) <= 0.5);
		assertTrue("PointC is off",new Vertex2D(75, 30).dist(t.getPointC()) <= 0.5);
	
		
	}

}
