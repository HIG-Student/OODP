package se.hig.oodp.b9.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.FigureHandler;
import se.hig.oodp.b9.data.Shape;
import se.hig.oodp.b9.logic.ShapeLists;

public class TestShapeControl
{
    ShapeLists lists;
    FigureHandler figureHandler;

    @Before
    public void setUp()
    {
        lists = new ShapeLists();
        figureHandler = new FigureHandlerImpl(lists);
    }

    @Test
    public void testCreatePoint()
    {
        figureHandler.createPoint(0, 0);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.isEmpty());
        assertTrue(lists.rotatableShapes.isEmpty());
    }

    @Test
    public void testCreateLine()
    {
        figureHandler.createLine(0, 0, 1, 1);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.size() == 1);
        assertTrue(lists.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateTriangle()
    {
        figureHandler.createTriangle(0, 0, 1, 1, 2, 2);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.size() == 1);
        assertTrue(lists.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateRectangle()
    {
        figureHandler.createRectangle(0, 0, 2, 3);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.size() == 1);
        assertTrue(lists.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateSquare()
    {
        figureHandler.createSquare(0, 0, 2);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.size() == 1);
        assertTrue(lists.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateEllipse()
    {
        figureHandler.createEllipse(0, 0, 2, 3);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.size() == 1);
        assertTrue(lists.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateCircle()
    {
        figureHandler.createCircle(0, 0, 2);

        assertTrue(lists.shapes.size() == 1);
        assertTrue(lists.scalableShapes.size() == 1);
        assertTrue(lists.rotatableShapes.size() == 1);
    }

    @Test
    public void testRemoveAll()
    {

        figureHandler.createPoint(0, 0);
        figureHandler.createLine(0, 0, 1, 1);
        figureHandler.createTriangle(0, 0, 1, 1, 2, 2);
        figureHandler.createRectangle(0, 0, 2, 3);
        figureHandler.createSquare(0, 0, 2);
        figureHandler.createEllipse(0, 0, 2, 3);
        figureHandler.createCircle(0, 0, 2);

        assertTrue(lists.shapes.size() == 7);
        assertTrue(lists.scalableShapes.size() == 6);
        assertTrue(lists.rotatableShapes.size() == 6);

        figureHandler.removeAll();

        assertTrue(lists.shapes.isEmpty());
        assertTrue(lists.scalableShapes.isEmpty());
        assertTrue(lists.rotatableShapes.isEmpty());
    }

    @Test
    public void testGetShapes()
    {

        figureHandler.createPoint(0, 0);
        figureHandler.createLine(0, 0, 1, 1);
        figureHandler.createTriangle(0, 0, 1, 1, 2, 2);
        figureHandler.createRectangle(0, 0, 2, 3);
        figureHandler.createSquare(0, 0, 2);
        figureHandler.createEllipse(0, 0, 2, 3);
        figureHandler.createCircle(0, 0, 2);

        Shape[] shapes = lists.getShapes();

        assertTrue(shapes.length == 7 && shapes.length == lists.shapes.size());

        for (int i = 0; i < shapes.length; i++)
            shapes[i] = null;

        shapes = null;

        assertFalse(lists.shapes.isEmpty());

        for (Shape shape : lists.shapes)
            assertTrue(shape != null);
    }
}
