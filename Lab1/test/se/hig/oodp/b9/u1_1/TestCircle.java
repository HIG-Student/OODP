package se.hig.oodp.b9.u1_1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.T;
import se.hig.oodp.b9.u1_1.Circle;

public class TestCircle
{
    Circle c;

    @Before
    public void setUp()
    {
        c = new Circle(new Vertex2D(2.0, 1.0), 1.0);
    }

    @Test
    public void testInit()
    {
        assertTrue("Position is off", c.getCenter()
                .dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("Size is off", Math.abs(c.getSize() - 1.0) <= 0.05d);
    }

    @Test
    public void testMoveBy()
    {
        c.moveBy(3.0, 3.0);

        assertTrue("Position is off", c.getCenter()
                .dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
        assertTrue("Size is off", Math.abs(c.getSize() - 1.0) <= 0.05d);
    }

    @Test
    public void testMoveTo()
    {
        c.moveTo(new Vertex2D(5.0, 4.0));

        assertTrue("Position is off", c.getCenter()
                .dist(new Vertex2D(5.0, 4.0)) <= T.PRECISION);
        assertTrue("Size is off", Math.abs(c.getSize() - 1.0) <= 0.05d);
    }

    @Test
    public void testScale()
    {
        c.scale(1.21);

        assertTrue("Position is off", c.getCenter()
                .dist(new Vertex2D(2.0, 1.0)) <= T.PRECISION);
        assertTrue("Size is off", Math.abs(c.getSize() - 1.21) <= 0.05d);
    }
}
