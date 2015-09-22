package se.hig.oodp.b9.u2.d1;

import se.hig.oodp.Vertex2D;

public class Rectangle extends PolyShape
{
    /**
     * Makes a rectangle 
     * <br>
     * Extends {@link PolyShape}
     * 
     * @param position the center of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Vertex2D position, double width, double height)
    {
        super(new Vertex2D[] {
                new Vertex2D(position.getX() - width / 2, position.getY()
                        - height / 2),
                new Vertex2D(position.getX() + width / 2, position.getY()
                        - height / 2),
                new Vertex2D(position.getX() + width / 2, position.getY()
                        + height / 2),
                new Vertex2D(position.getX() - width / 2, position.getY()
                        + height / 2) });
    }
    
    /**
     * Makes a rectangle 
     * <br>
     * Extends {@link PolyShape}
     * 
     * @param position the center of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param rotation the rotation of the rectangle (degrees)
     */
    public Rectangle(Vertex2D position, double width, double height, double rotation)
    {
        super(new Vertex2D[] {
                new Vertex2D(position.getX() - width / 2, position.getY()
                        - height / 2),
                new Vertex2D(position.getX() + width / 2, position.getY()
                        - height / 2),
                new Vertex2D(position.getX() + width / 2, position.getY()
                        + height / 2),
                new Vertex2D(position.getX() - width / 2, position.getY()
                        + height / 2) });

        rotate(rotation);
    }
}
