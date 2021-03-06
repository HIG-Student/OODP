package se.hig.oodp.b9.d1;

import se.hig.oodp.Vertex2D;

public class Rectangle
{
    Vertex2D pointA;
    Vertex2D pointB;
    Vertex2D pointC;
    Vertex2D pointD;

    public Rectangle(Vertex2D position, double width, double height)
    {
        pointA = new Vertex2D(position.getX() - width / 2, position.getY()
                - height / 2);
        pointB = new Vertex2D(position.getX() + width / 2, position.getY()
                - height / 2);
        pointC = new Vertex2D(position.getX() + width / 2, position.getY()
                + height / 2);
        pointD = new Vertex2D(position.getX() - width / 2, position.getY()
                + height / 2);
    }

    public Rectangle(Vertex2D position, double width, double height,
            double rotation)
    {
        pointA = new Vertex2D(position.getX() - width / 2, position.getY()
                - height / 2);
        pointB = new Vertex2D(position.getX() + width / 2, position.getY()
                - height / 2);
        pointC = new Vertex2D(position.getX() + width / 2, position.getY()
                + height / 2);
        pointD = new Vertex2D(position.getX() - width / 2, position.getY()
                + height / 2);

        rotate(rotation);
    }

    /**
     * @return the pointA
     */
    public Vertex2D getPointA()
    {
        return pointA;
    }

    /**
     * @return the pointB
     */
    public Vertex2D getPointB()
    {
        return pointB;
    }

    /**
     * @return the pointC
     */
    public Vertex2D getPointC()
    {
        return pointC;
    }

    /**
     * @return the pointD
     */
    public Vertex2D getPointD()
    {
        return pointD;
    }

    public void moveTo(Vertex2D position)
    {
        Vertex2D center = getCenter();
        pointA = pointA.moveBy(-center.getX(), -center.getY()).moveBy(
                position.getX(), position.getY());
        pointB = pointB.moveBy(-center.getX(), -center.getY()).moveBy(
                position.getX(), position.getY());
        pointC = pointC.moveBy(-center.getX(), -center.getY()).moveBy(
                position.getX(), position.getY());
        pointD = pointD.moveBy(-center.getX(), -center.getY()).moveBy(
                position.getX(), position.getY());
    }

    public void moveBy(double x, double y)
    {
        pointA = pointA.moveBy(x, y);
        pointB = pointB.moveBy(x, y);
        pointC = pointC.moveBy(x, y);
        pointD = pointD.moveBy(x, y);
    }

    public void scale(double scale)
    {
        Vertex2D center = getCenter();
        pointA = pointA.scale(center, scale, scale);
        pointB = pointB.scale(center, scale, scale);
        pointC = pointC.scale(center, scale, scale);
        pointD = pointD.scale(center, scale, scale);
    }

    public void rotate(double angle)
    {
        Vertex2D center = getCenter();
        pointA = pointA.rotate(center, angle);
        pointB = pointB.rotate(center, angle);
        pointC = pointC.rotate(center, angle);
        pointD = pointD.rotate(center, angle);
    }

    public Vertex2D getCenter()
    {
        Vertex2D pointAB = pointB.scale(pointA, 0.5, 0.5);
        Vertex2D pointCD = pointD.scale(pointC, 0.5, 0.5);

        return pointAB.scale(pointCD, 0.5, 0.5);
    }

    @Override
    public String toString()
    {
        return "Rectangle " + pointA + " to " + pointB + " to " + pointC
                + " to " + pointD;
    }
}
