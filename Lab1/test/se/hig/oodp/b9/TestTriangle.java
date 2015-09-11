package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.Test;

import se.hig.oodp.Vertex2D;

public class TestTriangle {

	@Test
	public void test() {
		
		Triangle t = new Triangle(new Vertex2D(2, 3), new Vertex2D(2, 3), new Vertex2D(2, 3));
		
		
		assertEquals(new Vertex2D(2, 3), t.getCenter());
		
	}

}
