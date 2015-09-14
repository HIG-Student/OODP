package se.hig.oodp.b9.u1_bonus;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;

public class TestEllipse 
{
	Ellipse e;
	
	@Before
	public void setUp()
	{
		e = new Ellipse(new Vertex2D(2.0,1.0),1.0,0.7);
	}
	
	@Test
	public void testConstruct()
	{
		assertTrue("Position is off",e.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
		assertTrue("Width is off",Math.abs(e.getWidth() - 1.0) <= T.PRECISION);
		assertTrue("Height is off",Math.abs(e.getHeight() - 0.7) <= T.PRECISION);
	}
	
	@Test
	public void testMoveBy()
	{
		e.moveBy(3.0, 3.0);
		
		assertTrue("Position is off",e.getCenter().dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
		assertTrue("Width is off",Math.abs(e.getWidth() - 1.0) <= T.PRECISION);
		assertTrue("Height is off",Math.abs(e.getHeight() - 0.7) <= T.PRECISION);
	}
	
	@Test
	public void testMoveTo()
	{
		e.moveTo(new Vertex2D(5.0, 4.0));
		
		assertTrue("Position is off",e.getCenter().dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
		assertTrue("Width is off",Math.abs(e.getWidth() - 1.0) <= T.PRECISION);
		assertTrue("Height is off",Math.abs(e.getHeight() - 0.7) <= T.PRECISION);
	}
	
	@Test
	public void testScale()
	{
		e.scale(1.21);
		
		assertTrue("Position is off",e.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
		assertTrue("Width is off",Math.abs(e.getWidth() - 1.21) <= T.PRECISION);
		assertTrue("Height is off",Math.abs(e.getHeight() - 0.847) <= T.PRECISION);
	}
}
