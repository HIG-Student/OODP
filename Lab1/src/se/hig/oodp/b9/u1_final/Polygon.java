package se.hig.oodp.b9.u1_final;

import java.util.Vector;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.u1_final.PolyShape;

public class Polygon extends PolyShape
{
    Polygon(Vertex2D[] positions)
    {
        super(positions);
    }
    
    //Class that will create the Polygon
    public static class Builder
    {
        public Vector<Vertex2D> positions = new Vector<Vertex2D>();

        public Builder()
        {
        };
        
        //remove all points that you have added to the "holder" 
        public Builder clear()
        {
            positions.removeAllElements();

            return this;
        }
        
        //add a point to a "holder"
        public Builder add(Vertex2D position)
        {
            positions.add(position);

            return this;
        }
        
        // Create the polygon from all points that was added to the "holder"
        public Polygon create() throws Exception
        {
        	//control that the "holder" have more then 2 points 
            if (positions.size() <= 2)
            {
                throw new Exception(
                        "You need more than two nodes in a polygon!");
            }

            Vertex2D[] arr = new Vertex2D[positions.size()];
            positions.toArray(arr);
            return new Polygon(arr);
        }
    }
}
