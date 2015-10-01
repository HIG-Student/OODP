package se.hig.oodp.b9.data;

import static org.junit.Assert.*;

import org.junit.*;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;

public class TestLine
{
    Line l;

    @Before
    public void setUp()
    {
        l = new Line(new Vertex2D(0.0, 0.0), new Vertex2D(4.0, 2.0));
    }

    @Test
    public void testCenter()
    {
        assertTrue("Position is off", l.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
    }

    @Test
    public void testMoveBy()
    {
        l.moveBy(3, 3);

        assertTrue("Position is off", l.getCenter().dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
        assertTrue("PointA is off", l.getNodes()[0].dist(new Vertex2D(3.0, 3.0)) <= T.PRECISION);
        assertTrue("PointB is off", l.getNodes()[1].dist(new Vertex2D(7.0, 5.0)) <= T.PRECISION);
    }

    @Test
    public void testMoveTo()
    {
        l.moveTo(new Vertex2D(5.0, 4.0));

        assertTrue("Position is off", l.getCenter().dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
        assertTrue("PointA is off", l.getNodes()[0].dist(new Vertex2D(3.0, 3.0)) <= T.PRECISION);
        assertTrue("PointB is off", l.getNodes()[1].dist(new Vertex2D(7.0, 5.0)) <= T.PRECISION);
    }

    @Test
    public void testScale()
    {
        l.scale(1.21, 1.21);

        assertTrue("Position is off", l.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("PointA is off", l.getNodes()[0].dist(new Vertex2D(-0.42, -0.21)) <= T.PRECISION);
        assertTrue("PointB is off", l.getNodes()[1].dist(new Vertex2D(4.42, 2.21)) <= T.PRECISION);
    }

    @Test
    public void testRotate()
    {
        l.rotate(30.0);

        assertTrue("Position is off", l.getCenter().dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("PointA is off", l.getNodes()[0].dist(new Vertex2D(0.768, -0.866)) <= T.PRECISION);
        assertTrue("PointB is off", l.getNodes()[1].dist(new Vertex2D(3.232, 2.866)) <= T.PRECISION);
    }
}
