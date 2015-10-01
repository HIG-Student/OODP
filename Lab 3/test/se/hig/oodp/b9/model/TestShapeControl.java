package se.hig.oodp.b9.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.hig.oodp.b9.data.Shape;
import se.hig.oodp.b9.model.ShapeControl;

public class TestShapeControl
{
    ShapeControl controller;

    @Before
    public void setUp()
    {
        controller = new ShapeControl();
    }

    @Test
    public void testCreatePoint()
    {
        controller.createPoint(0, 0);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.isEmpty());
        assertTrue(controller.rotatableShapes.isEmpty());
    }

    @Test
    public void testCreateLine()
    {
        controller.createLine(0, 0, 1, 1);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.size() == 1);
        assertTrue(controller.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateTriangle()
    {
        controller.createTriangle(0, 0, 1, 1, 2, 2);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.size() == 1);
        assertTrue(controller.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateRectangle()
    {
        controller.createRectangle(0, 0, 2, 3);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.size() == 1);
        assertTrue(controller.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateSquare()
    {
        controller.createSquare(0, 0, 2);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.size() == 1);
        assertTrue(controller.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateEllipse()
    {
        controller.createEllipse(0, 0, 2, 3);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.size() == 1);
        assertTrue(controller.rotatableShapes.size() == 1);
    }

    @Test
    public void testCreateCircle()
    {
        controller.createCircle(0, 0, 2);

        assertTrue(controller.shapes.size() == 1);
        assertTrue(controller.scalableShapes.size() == 1);
        assertTrue(controller.rotatableShapes.size() == 1);
    }

    @Test
    public void testRemoveAll()
    {

        controller.createPoint(0, 0);
        controller.createLine(0, 0, 1, 1);
        controller.createTriangle(0, 0, 1, 1, 2, 2);
        controller.createRectangle(0, 0, 2, 3);
        controller.createSquare(0, 0, 2);
        controller.createEllipse(0, 0, 2, 3);
        controller.createCircle(0, 0, 2);

        assertTrue(controller.shapes.size() == 7);
        assertTrue(controller.scalableShapes.size() == 6);
        assertTrue(controller.rotatableShapes.size() == 6);

        controller.removeAll();

        assertTrue(controller.shapes.isEmpty());
        assertTrue(controller.scalableShapes.isEmpty());
        assertTrue(controller.rotatableShapes.isEmpty());
    }

    @Test
    public void testGetShapes()
    {

        controller.createPoint(0, 0);
        controller.createLine(0, 0, 1, 1);
        controller.createTriangle(0, 0, 1, 1, 2, 2);
        controller.createRectangle(0, 0, 2, 3);
        controller.createSquare(0, 0, 2);
        controller.createEllipse(0, 0, 2, 3);
        controller.createCircle(0, 0, 2);

        Shape[] shapes = controller.getShapes();

        assertTrue(shapes.length == 7 && shapes.length == controller.shapes.size());

        for (int i = 0; i < shapes.length; i++)
            shapes[i] = null;

        shapes = null;

        assertFalse(controller.shapes.isEmpty());

        for (Shape shape : controller.shapes)
            assertTrue(shape != null);
    }
}
