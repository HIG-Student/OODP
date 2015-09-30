package se.hig.oodp.b9.end;

import static org.junit.Assert.*;
import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.end.Polygon;
import se.hig.oodp.b9.end.TestTriangle;

public class TestPolygon extends TestTriangle
{
    @Override
    public void setUp()
    {
        try
        {
            t = new Polygon.Builder().add(new Vertex2D(-1.0, 1.0))
                    .add(new Vertex2D(3.0, -1.0)).add(new Vertex2D(5.0, 3.0))
                    .create();
        }
        catch (Exception e)
        {
            fail("Exception: " + e.getMessage());
        }
    }
}
