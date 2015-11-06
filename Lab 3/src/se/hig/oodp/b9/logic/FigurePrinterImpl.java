package se.hig.oodp.b9.logic;

import se.hig.oodp.FigurePrinter;
import se.hig.oodp.b9.data.Shape;

public class FigurePrinterImpl extends FigureAction implements FigurePrinter
{
    public FigurePrinterImpl(ShapeLists lists)
    {
        super(lists);
    }

    public void moveAll(double dx, double dy)
    {
        for (Shape shape : lists.getShapes())
            shape.moveBy(dx, dy);
    }

    @Override
    public void printAll()
    {
        for (Shape shape : lists.getShapes())
            System.out.println(shape.asString());
    }
}