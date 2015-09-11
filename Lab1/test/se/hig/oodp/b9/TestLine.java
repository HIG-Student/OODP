package se.hig.oodp.b9;

import static org.junit.Assert.*;

import org.junit.*;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.Line;


public class TestLine 
{
//	public Line l;
	
//	@Before
//	public void setUp()
//	{
//		l = new Line(new Vertex2D(4,0 ),new Vertex2D(2,0));
//	}

	@Test
	public void testCenter() 
	{
		Line l = new Line(new Vertex2D(0,0 ),new Vertex2D(4,2));
		assertEquals(new Vertex2D(2, 1), l.getCenter());
	}
	
	@Test
	public void testMoveBy() 
	{
		Line l = new Line(new Vertex2D(0,0 ),new Vertex2D(4,2));
		l.moveBy(3, 3);
		assertEquals("getCenter()",new Vertex2D(5, 4), l.getCenter());
		
		assertTrue("Position is off",l.getPointA().dist(new Vertex2D(3, 3)) <= 0.5d);
		assertTrue("Position is off",l.getPointB().dist(new Vertex2D(7, 5)) <= 0.5d);

	}
	
	@Test
	public void testMoveTo() 
	{
		Line l = new Line(new Vertex2D(0,0 ),new Vertex2D(4,2));
		l.moveTo(new Vertex2D(5, 4));
		assertEquals("getCenter()",new Vertex2D(5, 4), l.getCenter());
		
		System.out.println(l.getPointA());
		
		assertTrue("Position is off",l.getPointA().dist(new Vertex2D(3, 3)) <= 0.5d);
		assertTrue("Position is off",l.getPointB().dist(new Vertex2D(7, 5)) <= 0.5d);

	}
	
	@Test
	public void testScale() 
	{
		Line l = new Line(new Vertex2D(0,0 ),new Vertex2D(4,2));
		l.scale(1.21);
		assertEquals("getCenter()",new Vertex2D(2, 1), l.getCenter());
		
		assertTrue("Position is off",l.getPointA().dist(new Vertex2D(-0.42, -0.21)) <= 0.5d);
		assertTrue("Position is off",l.getPointB().dist(new Vertex2D(4.42, 2.21)) <= 0.5d);
	
	}
	
	@Test
	public void testRotate() 
	{
		Line l = new Line(new Vertex2D(0,0 ),new Vertex2D(4,2));
		l.rotate(30);

		assertTrue("Position is off gnu",l.getCenter().dist(new Vertex2D(2, 1)) <= 0.5d);
		
		assertTrue("Position is off 1",l.getPointA().dist(new Vertex2D(0.768, -0.866)) <= 0.5d);
		assertTrue("Position is off 2",l.getPointB().dist(new Vertex2D(3.232, 2.866)) <= 0.5d);

	}
}
