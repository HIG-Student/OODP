package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.Test;

import se.hig.oodp.Vertex2D;

public class TestRectangle {

	@Test
	public void testCenter() 
	{
		Rectangle r = new Rectangle(new Vertex2D(1, 1),new Vertex2D(3, 3));
		
		assertEquals(new Vertex2D(2, 2),r.getCenter());
	}

}
