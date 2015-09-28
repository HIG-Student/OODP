/**
 * 
 */
package se.hig.oodp.b9.u2;

import java.util.Vector;

import se.hig.oodp.*;

/**
 *  Class for controlling a set of shapes
 */
public class ShapeControl_A implements FigureHandler,FigureMover,FigurePrinter,FigureRotor,FigureScalor
{
    /**
     *  The shapes that can't rotate and scale
     */
    Vector<Shape> unEditableShapes = new Vector<Shape>();
    
    /**
     *  The shapes that can rotate and scale
     */
    Vector<Shape> editableShapes = new Vector<Shape>();
    
    /**
     *  Scales all shapes controlled by this control
     *  
     *  @param factor_x factor to scale with along x-axis (or width if applicable)
     *  @param factor_y factor to scale with along y-axis (or height if applicable)
     */
    @Override
    public void scaleAll(double factor_x, double factor_y)
    {
        for(Shape s : editableShapes)
            ((Scalable)s).scale(factor_x, factor_y);
    }

    /**
     *  Rotates all shapes controlled by this control
     *  
     *  @param angle the angle to rotate (degrees)
     */
    @Override
    public void rotateAll(double angle)
    {
        for(Shape s : editableShapes)
            ((Rotatable)s).rotate(angle);
    }

    /**
     *  Print all shapes controlled by this control
     */
    @Override
    public void printAll()
    {
        for(Shape s : editableShapes)
            System.out.println(s.toString());
        
        for(Shape s : unEditableShapes)
            System.out.println(s.toString());
    }

    /**
     *  Moves all shapes controlled by this control
     *  
     *  @param dx distance along x-axis
     *  @param dy distance along y-axis
     */
    @Override
    public void moveAll(double dx, double dy)
    {
        for(Shape s : editableShapes)
            s.moveBy(dx, dy);
        for(Shape s : unEditableShapes)
            s.moveBy(dx, dy);
    }

    /**
     *  Creates a circle
     *  
     *  @param x x-coordinate
     *  @param y y-coordinate
     *  @param r radius
     */
    @Override
    public void createCircle(double x, double y, double r)
    {
        editableShapes.add(new Circle(new Vertex2D(x,y),r));
    }

    /**
     *  Creates a ellipse
     *  
     *  @param x x-coordinate
     *  @param y y-coordinate
     *  @param a width
     *  @param b height
     */
    @Override
    public void createEllipse(double x, double y, double a, double b)
    {
        editableShapes.add(new Ellipse(new Vertex2D(x,y),a,b));
    }

    /**
     *  Creates a line
     *  
     *  @param x0 x-coordinate for point 1
     *  @param y0 y-coordinate for point 1
     *  @param x1 x-coordinate for point 2
     *  @param y1 y-coordinate for point 2
     */
    @Override
    public void createLine(double x0, double y0, double x1, double y1)
    {
        editableShapes.add(new Line(new Vertex2D(x0,y0),new Vertex2D(x1,y1)));
    }

    /**
     *  Creates a point
     *  
     *  @param x x-coordinate
     *  @param y y-coordinate
     */
    @Override
    public void createPoint(double x, double y)
    {
        unEditableShapes.add(new Point(new Vertex2D(x,y)));
    }

    /**
     *  Creates a rectangle
     *  
     *  @param x x-coordinate
     *  @param y y-coordinate
     *  @param a width
     *  @param b height
     */
    @Override
    public void createRectangle(double x, double y, double a, double b)
    {
        editableShapes.add(new Rectangle(new Vertex2D(x,y),a,b));
    }

    /**
     *  Creates a square
     *  
     *  @param x x-coordinate
     *  @param y y-coordinate
     *  @param a width
     *  @param b height
     * @throws Exception thrown if not a square
     */
    @Override
    public void createSquare(double x, double y, double a)
    {
        editableShapes.add(new Square(new Vertex2D(x,y),a));
    }

    /**
     *  Creates a triangle
     *  
     *  @param vx0 x-coordinate for point 1
     *  @param vy0 y-coordinate for point 1
     *  @param vx1 x-coordinate for point 2
     *  @param vy1 y-coordinate for point 2
     *  @param vx2 x-coordinate for point 3
     *  @param vy2 y-coordinate for point 3
     */
    @Override
    public void createTriangle(double vx0, double vy0, double vx1, double vy1,
            double vx2, double vy2)
    {
        editableShapes.add(new Triangle(new Vertex2D(vx0,vy0),new Vertex2D(vx1,vy1),new Vertex2D(vx2,vy2)));
    }

    /**
     *  Removes all shapes controlled by this control
     */
    @Override
    public void removeAll()
    {
        editableShapes.removeAllElements();
        unEditableShapes.removeAllElements();
    }
}
