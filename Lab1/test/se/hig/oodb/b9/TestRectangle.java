package se.hig.oodb.b9;

import static org.junit.Assert.*;

import org.junit.Test;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.Rectangle;

public class TestRectangle {

	@Test
	public void test() {
		Rectangle r = new Rectangle(new Vertex2D(2, 2),new Vertex2D(2, 3),new Vertex2D(3, 3),new Vertex2D(3, 2));
		
//		assertEquals(expecteds, actuals, delta);
	}

}
