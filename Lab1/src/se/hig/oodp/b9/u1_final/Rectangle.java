package se.hig.oodp.b9.u1_final;

import se.hig.oodp.Vertex2D;

public class Rectangle extends PolyShape
{
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
    //Add a rectangle with a angle 
    public Rectangle(Vertex2D position, double width, double height,
            double rotation)
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
