package se.hig.oodp.b9.u1.end;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;
import se.hig.oodp.b9.u1.end.PolyShape;
import se.hig.oodp.b9.u1.end.Triangle;

// TODO Fix testScale and testRotation

public class TestTriangle
{
    protected PolyShape t;

    @Before
    public void setUp()
    {
        t = new Triangle(new Vertex2D(-1.0, 1.0), new Vertex2D(3.0, -1.0),
                new Vertex2D(5.0, 3.0));
    }

    @Test
    public void testCenter()
    {
        assertTrue("The center is off",
                new Vertex2D(7d / 3d, 1.0).dist(t.getCenter()) <= T.PRECISION);
    }

    @Test
    public void testMoveBy()
    {
        t.moveBy(3, 3);

        assertTrue("The center is off",
                new Vertex2D(16d / 3d, 4.0).dist(t.getCenter()) <= T.PRECISION);
        assertTrue("PointA is off",
                new Vertex2D(2.0, 4.0).dist(t.getPositions()[0]) <= T.PRECISION);
        assertTrue("PointB is off",
                new Vertex2D(6.0, 2.0).dist(t.getPositions()[1]) <= T.PRECISION);
        assertTrue("PointC is off",
                new Vertex2D(8.0, 6.0).dist(t.getPositions()[2]) <= T.PRECISION);
    }

    @Test
    public void testMoveTo()
    {
        t.moveTo(new Vertex2D(16d / 3d, 4.0));

        assertTrue("The center is off",
                new Vertex2D(16d / 3d, 4.0).dist(t.getCenter()) <= T.PRECISION);
        assertTrue("PointA is off",
                new Vertex2D(2.0, 4.0).dist(t.getPositions()[0]) <= T.PRECISION);
        assertTrue("PointB is off",
                new Vertex2D(6.0, 2.0).dist(t.getPositions()[1]) <= T.PRECISION);
        assertTrue("PointC is off",
                new Vertex2D(8.0, 6.0).dist(t.getPositions()[2]) <= T.PRECISION);
    }

    @Test
    public void testScale()
    {
        t.scale(1.21);

        assertTrue("The center is off",
                new Vertex2D(7d / 3d, 1.0).dist(t.getCenter()) <= T.PRECISION);
        assertTrue(
                "PointA is off",
                new Vertex2D(-1.7, 1.0).dist(t.getPositions()[0]) <= T.PRECISION);
        assertTrue(
                "PointB is off",
                new Vertex2D(3.14, -1.42).dist(t.getPositions()[1]) <= T.PRECISION);
        assertTrue(
                "PointC is off",
                new Vertex2D(5.60, 3.42).dist(t.getPositions()[2]) <= T.PRECISION);
    }

    @Test
    public void testRotate()
    {
        t.rotate(30.0);

        assertTrue("The center is off",
                new Vertex2D(7d / 3d, 1.0).dist(t.getCenter()) <= T.PRECISION);
        assertTrue(
                "PointA is off",
                new Vertex2D(-0.553, -0.666).dist(t.getPositions()[0]) <= T.PRECISION);
        assertTrue(
                "PointB is off",
                new Vertex2D(3.910, -0.398).dist(t.getPositions()[1]) <= T.PRECISION);
        assertTrue(
                "PointC is off",
                new Vertex2D(3.642, 4.065).dist(t.getPositions()[2]) <= T.PRECISION);
    }
}
