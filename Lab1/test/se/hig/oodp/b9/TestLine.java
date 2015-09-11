package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.Line;


public class TestLine {

	@Test
	public void testCenter() {
		
		
		
		Line l = new Line(new Vertex2D(2, 5),new Vertex2D(8,5));
		
		assertEquals(new Vertex2D(5, 5), l.getCenter());
		
		
		
		
	}

}
