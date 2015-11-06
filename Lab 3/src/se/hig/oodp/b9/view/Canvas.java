package se.hig.oodp.b9.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import se.hig.oodp.FigurePainter;
import se.hig.oodp.PrimitivesPainter;
import se.hig.oodp.b9.logic.FigurePainterImpl;
import se.hig.oodp.b9.logic.Graphix;
import se.hig.oodp.b9.logic.PrimitivesPainterImpl;
import se.hig.oodp.b9.logic.ShapeLists;

@SuppressWarnings("serial")
public class Canvas extends JPanel
{
    Graphix graphix;
    PrimitivesPainter primPainter;
    FigurePainter figPainter;

    public Canvas(ShapeLists shapeLists)
    {
        graphix = new Graphix();
        primPainter = new PrimitivesPainterImpl(graphix);
        figPainter = new FigurePainterImpl(shapeLists, primPainter);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        graphix.setGraphics((Graphics2D) g);
        figPainter.paintAll();
    }
}
