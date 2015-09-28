/**
 * 
 */
package se.hig.oodp.b9.u2;

import java.util.Vector;

import se.hig.oodp.*;

/**
 *  Class for controlling a set of shapes
 */
public class ShapeControl_B implements FigureHandler
{
    /**
     *  The shapes that can't rotate and scale
     */
    Vector<Shape> allShapes = new Vector<Shape>();
    
    ShapeScaler scaler = new ShapeScaler();
    
    class ShapeDoer
    {
        Vector<Shape> shapes = new Vector<Shape>();
        
        public void add(Shape shape)
        {
            shapes.add(shape);
        }
        
        public void removeAll()
        {
            shapes.removeAllElements();
        }
    }
    
    class ShapeScaler extends ShapeDoer implements FigureScalor
    {
        /**
         *  Scales all shapes controlled by this control
         *  
         *  @param factor_x factor to scale with along x-axis (or width if applicable)
         *  @param factor_y factor to scale with along y-axis (or height if applicable)
         */
        @Override
        public void scaleAll(double factor_x, double factor_y)
        {
            for(Shape s : shapes)
                ((Scalable)s).scale(factor_x, factor_y);
        }
    }
    
    public void scaleAll(double factor_x, double factor_y)
    {
        scaler.scaleAll(factor_x, factor_y);
    }
    
    ShapeRotator rotator = new ShapeRotator();
    
    class ShapeRotator extends ShapeDoer  implements FigureRotor
    {
        /**
         *  Rotates all shapes controlled by this control
         *  
         *  @param angle the angle to rotate (degrees)
         */
        @Override
        public void rotateAll(double angle)
        {
            for(Shape s : shapes)
                ((Rotatable)s).rotate(angle);
        }
    }
    
    public void rotateAll(double angle)
    {
        rotator.rotateAll(angle);
    }
    
    ShapePrinter printer = new ShapePrinter();
    
    class ShapePrinter extends ShapeDoer implements FigurePrinter
    {
        /**
         *  Print all shapes controlled by this control
         */
        @Override
        public void printAll()
        {
            for(Shape s : shapes)
                System.out.println(s.toString());
        }
    }
    
    public void printAll()
    {
        printer.printAll();
    }
    
    ShapeMover mover = new ShapeMover();
    
    class ShapeMover extends ShapeDoer  implements FigureMover
    {        
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
    }
    
    public void moveAll(double dx,double dy)
    {
        mover.moveAll(dx, dy);
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
        Shape shape = new Circle(new Vertex2D(x,y),r);
        allShapes.add(shape);
        mover.add(shape);
        rotator.add(shape);
        scaler.add(shape);
        printer.add(shape);
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
        Shape shape = new Ellipse(new Vertex2D(x,y),a,b);
        allShapes.add(shape);
        mover.add(shape);
        rotator.add(shape);
        scaler.add(shape);
        printer.add(shape);
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
        Shape shape = new Line(new Vertex2D(x0,y0),new Vertex2D(x1,y1));
        allShapes.add(shape);
        mover.add(shape);
        rotator.add(shape);
        scaler.add(shape);
        printer.add(shape);
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
        Shape shape = new Point(new Vertex2D(x,y));
        allShapes.add(shape);
        mover.add(shape);
        printer.add(shape);
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
        Shape shape = new Rectangle(new Vertex2D(x,y),a,b);
        allShapes.add(shape);
        mover.add(shape);
        rotator.add(shape);
        scaler.add(shape);
        printer.add(shape);
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
        Shape shape = new Square(new Vertex2D(x,y),a);
        allShapes.add(shape);
        mover.add(shape);
        rotator.add(shape);
        scaler.add(shape);
        printer.add(shape);
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
        Shape shape = new Triangle(new Vertex2D(vx0,vy0),new Vertex2D(vx1,vy1),new Vertex2D(vx2,vy2));
        allShapes.add(shape);
        mover.add(shape);
        rotator.add(shape);
        scaler.add(shape);
        printer.add(shape);
    }

    /**
     *  Removes all shapes controlled by this control
     */
    @Override
    public void removeAll()
    {
        allShapes.removeAllElements();
        mover.removeAll();
        rotator.removeAll();
        scaler.removeAll();
        printer.removeAll();
    }
}
