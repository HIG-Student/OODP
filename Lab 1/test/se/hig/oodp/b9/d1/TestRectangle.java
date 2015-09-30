package se.hig.oodp.b9.d1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;
import se.hig.oodp.b9.d1.Rectangle;

public class TestRectangle
{

    Rectangle r;

    @Before
    public void setUp()
    {
        r = new Rectangle(new Vertex2D(2.0, 1.0), 6, 4);
    }

    @Test
    public void testConstruct()
    {
        assertTrue("Position is off", r.getCenter()
                .dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("PointA is off", r.getPointA()
                .dist(new Vertex2D(-1.0, -1.0)) <= T.PRECISION);
        assertTrue("PointB is off",
                r.getPointB().dist(new Vertex2D(5.0, -1.0)) <= T.PRECISION);
        assertTrue("PointC is off",
                r.getPointC().dist(new Vertex2D(5.0, 3.0)) <= T.PRECISION);
        assertTrue("PointD is off",
                r.getPointD().dist(new Vertex2D(-1.0, 3.0)) <= T.PRECISION);
    }

    @Test
    public void testMoveBy()
    {
        r.moveBy(3.0, 3.0);

        assertTrue("Position is off", r.getCenter()
                .dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
        assertTrue("PointA is off",
                r.getPointA().dist(new Vertex2D(2.0, 2.0)) <= T.PRECISION);
        assertTrue("PointB is off",
                r.getPointB().dist(new Vertex2D(8.0, 2.0)) <= T.PRECISION);
        assertTrue("PointC is off",
                r.getPointC().dist(new Vertex2D(8.0, 6.0)) <= T.PRECISION);
        assertTrue("PointD is off",
                r.getPointD().dist(new Vertex2D(2.0, 6.0)) <= T.PRECISION);
    }

    @Test
    public void testMoveTo()
    {
        r.moveTo(new Vertex2D(5.0, 4.0));

        assertTrue("Position is off", r.getCenter()
                .dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
        assertTrue("PointA is off",
                r.getPointA().dist(new Vertex2D(2.0, 2.0)) <= T.PRECISION);
        assertTrue("PointB is off",
                r.getPointB().dist(new Vertex2D(8.0, 2.0)) <= T.PRECISION);
        assertTrue("PointC is off",
                r.getPointC().dist(new Vertex2D(8.0, 6.0)) <= T.PRECISION);
        assertTrue("PointD is off",
                r.getPointD().dist(new Vertex2D(2.0, 6.0)) <= T.PRECISION);
    }

    @Test
    public void testScale()
    {
        r.scale(1.21);

        assertTrue("Position is off", r.getCenter()
                .dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("PointA is off",
                r.getPointA().dist(new Vertex2D(-1.63, -1.42)) <= T.PRECISION);
        assertTrue("PointB is off",
                r.getPointB().dist(new Vertex2D(5.63, -1.42)) <= T.PRECISION);
        assertTrue("PointC is off", r.getPointC()
                .dist(new Vertex2D(5.63, 3.42)) <= T.PRECISION);
        assertTrue("PointD is off",
                r.getPointD().dist(new Vertex2D(-1.63, 3.42)) <= T.PRECISION);
    }

    @Test
    public void testRotate()
    {
        r.rotate(30);

        assertTrue("Position is off", r.getCenter()
                .dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("PointA is off",
                r.getPointA().dist(new Vertex2D(0.402, -2.232)) <= T.PRECISION);
        assertTrue("PointB is off",
                r.getPointB().dist(new Vertex2D(5.598, 0.768)) <= T.PRECISION);
        assertTrue("PointC is off",
                r.getPointC().dist(new Vertex2D(3.598, 4.232)) <= T.PRECISION);
        assertTrue("PointD is off",
                r.getPointD().dist(new Vertex2D(-1.598, 1.232)) <= T.PRECISION);
    }

}
