package se.hig.oodp.b9.u1_bonus;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;

public class TestSquare 
{
	Square s;
	
	@Before
	public void setUp()
	{
		s = new Square(new Vertex2D(2.0,1.0),4);
	}
	
	@Test
	public void testConstruct() 
	{
		assertTrue("Position is off",s.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
		assertTrue("PointA is off",s.getPositions()[0].dist(new Vertex2D(0.0, -1.0)) <= T.PRECISION);
		assertTrue("PointB is off",s.getPositions()[1].dist(new Vertex2D(4.0, -1.0)) <= T.PRECISION);
		assertTrue("PointC is off",s.getPositions()[2].dist(new Vertex2D(4.0, 3.0)) <= T.PRECISION);
		assertTrue("PointD is off",s.getPositions()[3].dist(new Vertex2D(0.0, 3.0)) <= T.PRECISION);
	}
	
	@Test
	public void testMoveBy() 
	{
		s.moveBy(3.0,3.0);
		
		assertTrue("Position is off",s.getCenter().dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
		assertTrue("PointA is off",s.getPositions()[0].dist(new Vertex2D(3.0, 2.0)) <= T.PRECISION);
		assertTrue("PointB is off",s.getPositions()[1].dist(new Vertex2D(7.0, 2.0)) <= T.PRECISION);
		assertTrue("PointC is off",s.getPositions()[2].dist(new Vertex2D(7.0, 6.0)) <= T.PRECISION);
		assertTrue("PointD is off",s.getPositions()[3].dist(new Vertex2D(3.0, 6.0)) <= T.PRECISION);
	}
	
	@Test
	public void testMoveTo() 
	{
		s.moveTo(new Vertex2D(5.0, 4.0));
		
		assertTrue("Position is off",s.getCenter().dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
		assertTrue("PointA is off",s.getPositions()[0].dist(new Vertex2D(3.0, 2.0)) <= T.PRECISION);
		assertTrue("PointB is off",s.getPositions()[1].dist(new Vertex2D(7.0, 2.0)) <= T.PRECISION);
		assertTrue("PointC is off",s.getPositions()[2].dist(new Vertex2D(7.0, 6.0)) <= T.PRECISION);
		assertTrue("PointD is off",s.getPositions()[3].dist(new Vertex2D(3.0, 6.0)) <= T.PRECISION);
	}
	
	@Test
	public void testScale() 
	{
		s.scale(1.21);
		
		assertTrue("Position is off",s.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
		assertTrue("PointA is off",s.getPositions()[0].dist(new Vertex2D(-0.42, -1.42)) <= T.PRECISION);
		assertTrue("PointB is off",s.getPositions()[1].dist(new Vertex2D(4.42, -1.42)) <= T.PRECISION);
		assertTrue("PointC is off",s.getPositions()[2].dist(new Vertex2D(4.42, 3.42)) <= T.PRECISION);
		assertTrue("PointD is off",s.getPositions()[3].dist(new Vertex2D(-0.42, 3.42)) <= T.PRECISION);
	}
	
	@Test
	public void testRotate() 
	{
		s.rotate(30);
		
		assertTrue("Position is off",s.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
		assertTrue("PointA is off",s.getPositions()[0].dist(new Vertex2D(1.268, -1.732)) <= T.PRECISION);
		assertTrue("PointB is off",s.getPositions()[1].dist(new Vertex2D(4.732, 0.268)) <= T.PRECISION);
		assertTrue("PointC is off",s.getPositions()[2].dist(new Vertex2D(2.732, 3.732)) <= T.PRECISION);
		assertTrue("PointD is off",s.getPositions()[3].dist(new Vertex2D(-0.732, 1.732)) <= T.PRECISION);
	}
}
