package se.hig.oodp.b9.u2;

import java.util.Vector;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1.end.PolyShape;

public class Polygon extends PolyShape
{
    /**
     * Makes a polygon
     * <br>
     * Extends {@link PolyShape}
     * 
     * @param positions the nodes this polygon is built with
     */
    Polygon(Vertex2D[] positions)
    {
        super(positions);
    }
    
    
    /**
     * Builder of polygons
     */
    public static class Builder
    {
        /**
         * The nodes the polygon should be built with
         */
        public Vector<Vertex2D> nodes = new Vector<Vertex2D>();

        /**
         * The builder of polygons
         */
        public Builder() { };
        
        
        /**
         * Clears all nodes that the builder currently holds
         * 
         * @return the builder itself
         */
        public Builder clear()
        {
            nodes.removeAllElements();

            return this;
        }
        
        
        /**
         * Add a node to the list
         * 
         * @param position the new node
         * @return the builder itself
         */
        public Builder add(Vertex2D position)
        {
            nodes.add(position);

            return this;
        }
        
        
        /**
         * Generate a polygon from the nodes this builder holds
         * 
         * @return the generated polygon
         * @throws Exception thrown if there is less than three nodes
         */
        public Polygon create() throws Exception
        {
        	//control that the "holder" have more then 2 points 
            if (nodes.size() <= 2)
            {
                throw new Exception(
                        "You need more than two nodes in a polygon!");
            }

            Vertex2D[] arr = new Vertex2D[nodes.size()];
            nodes.toArray(arr);
            return new Polygon(arr);
        }
    }
}
