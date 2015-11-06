package se.hig.oodp.b9.logic;

import se.hig.oodp.FigureScalor;
import se.hig.oodp.b9.data.Scalable;

public class FigureScalorImpl extends FigureAction implements FigureScalor
{
    public FigureScalorImpl(ShapeLists lists)
    {
        super(lists);
    }

    @Override
    public void scaleAll(double factor_x, double factor_y)
    {
        for (Scalable shape : lists.getScalableShapes())
            shape.scale(factor_x, factor_y);
        
        lists.onChange.invoke(null);
    }
}