package se.hig.oodp.b9.u2;

import se.hig.oodp.Vertex2D;

public class Square extends Rectangle
{
    /**
     * Makes a square
     * <br>
     * Extends {@link Rectangle} using "size" for both width and height
     * 
     * @param position the center of the square
     * @param size the size of the square (size = width = height)
     */
    public Square(Vertex2D position, double size)
    {
        super(position, size, size);
    }
    
    /**
     * Makes a square
     * <br>
     * Extends {@link Rectangle} using "size" for both width and height
     * 
     * @param position the center of the square
     * @param size the size of the square (size = width = height)
     * @param rotation the rotation of the square (degrees)
     */
    public Square(Vertex2D position, double size, double rotation)
    {
        super(position, size, size, rotation);
    }
}
