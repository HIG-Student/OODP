package se.hig.oodp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;

public class Vertex2DTest
{
	double x = 2.0;
	double y = 1.0;
	double cx = 1.0;
	double cy = 1.0;
	
	Vertex2D v;
	
	@Before
	public void setUp () throws Exception
	{
		v = new Vertex2D (x, y);
	}

	@After
	public void tearDown () throws Exception
	{
	}

	@Test
	public void testNewVertex ()
	{
		assertEquals ("Wrong position!", x, v.getX (), 0.01);
		assertEquals ("Wrong position!", y, v.getY (), 0.01);
	}

	@Test
	public void testVertexRotation ()
	{
		Vertex2D c = new Vertex2D (0, 0);
		Vertex2D rv = v.rotate (c, 30.0);
		assertEquals ("Wrong position!", 1.232, rv.getX (), 0.01);
		assertEquals ("Wrong position!", 1.866, rv.getY (), 0.01);
	}
	
	@Test
	public void testVertexMoveBy ()
	{
		Vertex2D mv = v.moveBy(1.0, 1.0);
		assertEquals ("Wrong position!", 3.0, mv.getX (), 0.01);
		assertEquals ("Wrong position!", 2.0, mv.getY (), 0.01);
	}
	
	@Test
	public void testVertexScale ()
	{
		Vertex2D c = new Vertex2D (cx, cy);
		Vertex2D sv = v.scale (c, 2.0, 1.5);
		assertEquals ("Wrong position!", 3.0, sv.getX (), 0.01);
		assertEquals ("Wrong position!", 1.0, sv.getY (), 0.01);
	}

	@Test
	public void testDistance ()
	{
		Vertex2D c = new Vertex2D (cx, cy);
		assertEquals ("Wrong distance!", 1.0, v.dist (c), 0.01);
	}

	@Test
	public void testEquals ()
	{
		Vertex2D c = new Vertex2D (cx, cy);
		Vertex2D d = new Vertex2D (x, y);
		assertTrue (v.equals (v));
		assertTrue (v.equals (d));
		assertTrue (d.equals (v));
		assertFalse (v.equals (c));
		assertFalse (c.equals (v));
	}
}
