package se.hig.oodp.b9;

import static org.junit.Assert.*;
import org.junit.*;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.Line;


public class TestLine 
{
	public Line l;
	
	@Before
	public void setUp()
	{
		l = new Line(new Vertex2D(2, 5),new Vertex2D(8,5));
	}
	
	@Test
	public void testCenter() 
	{
		assertEquals(new Vertex2D(5, 5), l.getCenter());
	}
	
	@Test
	public void testMoveBy() 
	{
		l.moveBy(3, 7);
		assertEquals("getCenter()",new Vertex2D(8, 12), l.getCenter());
		
		assertEquals("pointA",new Vertex2D(5, 12), l.getPointA());
		assertEquals("pointB",new Vertex2D(11,12), l.getPointB());
	}
	
	@Test
	public void testMoveTo() 
	{
		l.moveTo(new Vertex2D(4, 10));
		assertEquals("getCenter()",new Vertex2D(4, 10), l.getCenter());
		
		assertEquals("pointA",new Vertex2D(1, 10), l.getPointA());
		assertEquals("pointB",new Vertex2D(7,10), l.getPointB());
	}
	
	@Test
	public void testScale() 
	{
		l.scale(2);
		assertEquals("getCenter()",new Vertex2D(5, 5), l.getCenter());
		
		assertEquals("pointA",new Vertex2D(-1, 5), l.getPointA());
		assertEquals("pointB",new Vertex2D(11,5), l.getPointB());
	}
	
	@Test
	public void testRotate() 
	{
		l.rotate(90);
		assertEquals("getCenter()",new Vertex2D(5, 5), l.getCenter());
		
		assertEquals("pointA",new Vertex2D(5,2), l.getPointA());
		assertEquals("pointB",new Vertex2D(5,8), l.getPointB());
	}
}
