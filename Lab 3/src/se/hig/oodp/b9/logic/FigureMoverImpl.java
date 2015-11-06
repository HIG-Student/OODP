package se.hig.oodp.b9.logic;

import se.hig.oodp.FigureMover;
import se.hig.oodp.b9.data.Shape;

public class FigureMoverImpl extends FigureAction implements FigureMover
{
    public FigureMoverImpl(ShapeLists lists)
    {
        super(lists);
    }

    @Override
    public void moveAll(double dx, double dy)
    {
        for (Shape shape : lists.getShapes())
            shape.moveBy(dx, dy);
        
        lists.onChange.invoke(null);
    }
}
