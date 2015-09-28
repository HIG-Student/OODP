/**
 * 
 */
package se.hig.oodp.b9.u2;

import java.util.ArrayList;
import java.util.List;

import se.hig.oodp.*;

/**
 *  Class for controlling a set of shapes
 */
public class ShapeControl implements FigureHandler,FigureMover,FigurePrinter,FigureRotor,FigureScalor
{
    /**
     *  The shapes this control controls
     */
    List<Shape> shapes = new ArrayList<Shape>();
    
    /**
     *  The rotatable shapes this control controls
     */
    List<Rotatable> rotatableShapes = new ArrayList<Rotatable>();
    
    /**
     *  The scalable shapes this control controls
     */
    List<Scalable> scalableShapes = new ArrayList<Scalable>();
    
    /**
     *  Scales all shapes controlled by this control
     *  
     *  @param factor_x factor to scale with along x-axis (or width if applicable)
     *  @param factor_y factor to scale with along y-axis (or height if applicable)
     */
    @Override
    public void scaleAll(double factor_x, double factor_y)
    {
        for(Scalable s : scalableShapes)
            s.scale(factor_x, factor_y);
    }

    /**
     *  Rotates all shapes controlled by this control
     *  
     *  @param angle the angle to rotate (degrees)
     */
    @Override
    public void rotateAll(double angle)
    {
        for(Rotatable s : rotatableShapes)
            s.rotate(angle);
    }

    /**
     *  Print all shapes controlled by this control
     */
    @Override
    public void printAll()
    {
        for(Shape s : shapes)
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
        for(Shape s : shapes)
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
        Circle circle = new Circle(new Vertex2D(x,y),r);
        shapes.add(circle);
        rotatableShapes.add(circle);
        scalableShapes.add(circle);
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
        Ellipse ellipse = new Ellipse(new Vertex2D(x,y),a,b);
        shapes.add(ellipse);
        rotatableShapes.add(ellipse);
        scalableShapes.add(ellipse);
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
        Line line = new Line(new Vertex2D(x0,y0),new Vertex2D(x1,y1));
        shapes.add(line);
        rotatableShapes.add(line);
        scalableShapes.add(line);
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
        Point point = new Point(new Vertex2D(x,y));
        shapes.add(point);
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
        Rectangle rectangle = new Rectangle(new Vertex2D(x,y),a,b);
        shapes.add(rectangle);
        rotatableShapes.add(rectangle);
        scalableShapes.add(rectangle);
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
        Square square = new Square(new Vertex2D(x,y),a);
        shapes.add(square);
        rotatableShapes.add(square);
        scalableShapes.add(square);
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
        Triangle triangle = new Triangle(new Vertex2D(vx0,vy0),new Vertex2D(vx1,vy1),new Vertex2D(vx2,vy2));
        shapes.add(triangle);
        rotatableShapes.add(triangle);
        scalableShapes.add(triangle);
    }

    /**
     *  Removes all shapes controlled by this control
     */
    @Override
    public void removeAll()
    {
        shapes.clear();
        rotatableShapes.clear();
        scalableShapes.clear();
    }
}
