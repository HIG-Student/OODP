package se.hig.oodp.b9.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import se.hig.oodp.FigurePainter;
import se.hig.oodp.b9.logic.Graphix;

@SuppressWarnings("serial")
public class Canvas extends JPanel
{
    Graphix graphix;
    FigurePainter figPainter;

    public Canvas(Graphix graphix, FigurePainter figPainter)
    {
        this.graphix = graphix;
        this.figPainter = figPainter;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        graphix.setGraphics((Graphics2D) g);
        figPainter.paintAll();
    }

    public BufferedImage screenShoot()
    {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        paint(g);

        return bi;
    }
}
