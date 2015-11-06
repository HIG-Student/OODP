package se.hig.oodp.b9.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.hig.oodp.b9.data.Rotatable;
import se.hig.oodp.b9.data.Scalable;
import se.hig.oodp.b9.data.Shape;

/**
 * Class for controlling a set of shapes
 */
@SuppressWarnings("serial")
public class ShapeLists implements Serializable
{
    public final Event<Shape> onChange = new Event<Shape>();

    /**
     * The shapes this control controls
     */
    List<Shape> shapes = new ArrayList<Shape>();

    public Shape[] getShapes()
    {
        return shapes.toArray(new Shape[0]);
    }

    /**
     * The rotatable shapes this control controls
     */
    List<Rotatable> rotatableShapes = new ArrayList<Rotatable>();

    public Rotatable[] getRotatableShapes()
    {
        return rotatableShapes.toArray(new Rotatable[0]);
    }

    /**
     * The scalable shapes this control controls
     */
    List<Scalable> scalableShapes = new ArrayList<Scalable>();

    public Scalable[] getScalableShapes()
    {
        return scalableShapes.toArray(new Scalable[0]);
    }

    public void addShape(Shape shape)
    {
        if (shapes.contains(shape))
            return;

        if (shape instanceof Rotatable)
            rotatableShapes.add((Rotatable) shape);
        if (shape instanceof Scalable)
            scalableShapes.add((Scalable) shape);

        shapes.add(shape);

        onChange.invoke(shape);
    }

    public void clear()
    {
        shapes.clear();
        rotatableShapes.clear();
        scalableShapes.clear();

        onChange.invoke(null);
    }

    /**
     * Loads the shapes into this control
     * 
     * @param shapes
     *            the shapes to add
     */
    public void loadShapes(Shape[] shapes)
    {
        for (Shape s : shapes)
        {
            addShape(s);
        }
    }
}
