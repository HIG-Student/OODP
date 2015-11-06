package se.hig.oodp.b9.logic;

import se.hig.oodp.FigurePainter;
import se.hig.oodp.PrimitivesPainter;
import se.hig.oodp.b9.data.Shape;

public class FigurePainterImpl extends FigureAction implements FigurePainter
{
    PrimitivesPainter painter;

    public FigurePainterImpl(ShapeLists list, PrimitivesPainter painter)
    {
        super(list);
        this.painter = painter;
    }

    @Override
    public void paintAll()
    {
        for (Shape s : lists.shapes)
        {
            s.draw(painter);
        }
    }
}
