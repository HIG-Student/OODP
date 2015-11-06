package se.hig.oodp.b9.logic;

import se.hig.oodp.FigureRotor;
import se.hig.oodp.b9.data.Rotatable;

public class FigureRotorImpl extends FigureAction implements FigureRotor
{
    public FigureRotorImpl(ShapeLists lists)
    {
        super(lists);
    }

    @Override
    public void rotateAll(double angle)
    {
        for (Rotatable shape : lists.getRotatableShapes())
            shape.rotate(angle);
        
        lists.onChange.invoke(null);
    }
}