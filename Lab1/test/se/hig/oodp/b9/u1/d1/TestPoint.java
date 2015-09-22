package se.hig.oodp.b9.u1.d1;

import static org.junit.Assert.*;

import org.junit.*;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;
import se.hig.oodp.b9.u1.d1.Point;

public class TestPoint
{
    Point p;

    @Before
    public void setUp()
    {
        p = new Point(new Vertex2D(2.0, 1.0));
    }

    @Test
    public void testInitPosition()
    {
        assertTrue("Position is off", p.getCenter()
                .dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
    }

    @Test
    public void testMoveBy()
    {
        p.moveBy(3, 3);
        assertTrue("Position is off", p.getCenter()
                .dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
    }
}
