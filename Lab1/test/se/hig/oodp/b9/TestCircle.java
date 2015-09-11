package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.Test;

import se.hig.oodp.Vertex2D;

public class TestCircle 
{
	@Test
	public void testMoveTo()
	{
		Circle c = new Circle(new Vertex2D(5,5),10);
		c.moveTo(new Vertex2D(3,8));
		assertTrue("Incorrect position",c.getCenter().equals(new Vertex2D(3,8)));
	}
	
	@Test
	public void testMoveBy()
	{
		Circle c = new Circle(new Vertex2D(5,5),10);
		c.moveBy(2,4);
		assertTrue("Incorrect position",c.getCenter().equals(new Vertex2D(7,9)));
	}
	
	@Test
	public void testScale()
	{
		Circle c = new Circle(new Vertex2D(5,5),10);
		c.scale(2);
		assertTrue("Incorrect size",c.getSize() == 10 * 2);
	}
}
