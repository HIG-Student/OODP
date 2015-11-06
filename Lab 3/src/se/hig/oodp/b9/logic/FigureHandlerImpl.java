package se.hig.oodp.b9.logic;

import se.hig.oodp.FigureHandler;
import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.data.Circle;
import se.hig.oodp.b9.data.Ellipse;
import se.hig.oodp.b9.data.Line;
import se.hig.oodp.b9.data.Point;
import se.hig.oodp.b9.data.Rectangle;
import se.hig.oodp.b9.data.Square;
import se.hig.oodp.b9.data.Triangle;

public class FigureHandlerImpl implements FigureHandler
{
    ShapeLists lists;
    
    public FigureHandlerImpl(ShapeLists lists)
    {
        this.lists = lists;
    }

    /**
     * Creates a circle
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param r
     *            radius
     */
    @Override
    public void createCircle(double x, double y, double r)
    {
        lists.addShape(new Circle(new Vertex2D(x, y), r));
    }

    /**
     * Creates a ellipse
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param a
     *            width
     * @param b
     *            height
     */
    @Override
    public void createEllipse(double x, double y, double a, double b)
    {
        lists.addShape(new Ellipse(new Vertex2D(x, y), a, b));
    }

    /**
     * Creates a ellipse
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param width
     *            width
     * @param height
     *            height
     * @param rotation
     *            rotation
     */
    public void createEllipse(double x, double y, double width, double height, double rotation)
    {
        lists.addShape(new Ellipse(new Vertex2D(x, y), width, height, rotation));
    }

    /**
     * Creates a line
     * 
     * @param x0
     *            x-coordinate for point 1
     * @param y0
     *            y-coordinate for point 1
     * @param x1
     *            x-coordinate for point 2
     * @param y1
     *            y-coordinate for point 2
     */
    @Override
    public void createLine(double x0, double y0, double x1, double y1)
    {
        lists.addShape(new Line(new Vertex2D(x0, y0), new Vertex2D(x1, y1)));
    }

    /**
     * Creates a point
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     */
    @Override
    public void createPoint(double x, double y)
    {
        lists.addShape(new Point(new Vertex2D(x, y)));
    }

    /**
     * Creates a rectangle
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param a
     *            width
     * @param b
     *            height
     */
    @Override
    public void createRectangle(double x, double y, double a, double b)
    {
        lists.addShape(new Rectangle(new Vertex2D(x, y), a, b));
    }

    /**
     * Creates a rectangle
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param width
     *            width
     * @param height
     *            height
     * @param rotation
     *            rotation
     */
    public void createRectangle(double x, double y, double width, double height, double rotation)
    {
        lists.addShape(new Rectangle(new Vertex2D(x, y), width, height, rotation));
    }

    /**
     * Creates a square
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param a
     *            width
     * @param b
     *            height
     * @throws Exception
     *             thrown if not a square
     */
    @Override
    public void createSquare(double x, double y, double a)
    {
        lists.addShape(new Square(new Vertex2D(x, y), a));
    }

    /**
     * Creates a square
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param a
     *            width
     * @param size
     *            size
     * @param rotation
     *            rotation
     * @throws Exception
     *             thrown if not a square
     */
    public void createSquare(double x, double y, double size, double rotation)
    {
        lists.addShape(new Square(new Vertex2D(x, y), size, rotation));
    }

    /**
     * Creates a triangle
     * 
     * @param vx0
     *            x-coordinate for point 1
     * @param vy0
     *            y-coordinate for point 1
     * @param vx1
     *            x-coordinate for point 2
     * @param vy1
     *            y-coordinate for point 2
     * @param vx2
     *            x-coordinate for point 3
     * @param vy2
     *            y-coordinate for point 3
     */
    @Override
    public void createTriangle(double vx0, double vy0, double vx1, double vy1, double vx2, double vy2)
    {
        lists.addShape(new Triangle(new Vertex2D(vx0, vy0), new Vertex2D(vx1, vy1), new Vertex2D(vx2, vy2)));
    }

    /**
     * Removes all shapes controlled by this control
     */
    @Override
    public void removeAll()
    {
        lists.clear();
    }
}
