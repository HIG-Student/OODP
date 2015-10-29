package se.hig.oodp.b9.client;

import java.awt.Dimension;
import java.awt.Graphics2D;

import se.hig.oodp.b9.Card;

/**
 * Interface for painting cards
 */
public interface ICardPainter
{
    /**
     * Draw the image
     * 
     * The center of the image should be located at (0,0)
     * 
     * @param g
     *            graphics2d that we should draw on/with
     * @param card
     *            to draw
     */
    public void drawImage(Graphics2D g, Card card);

    /**
     * Draw the highlight <br>
     * <br>
     * Should be centered over the image drawn by {@link drawImage}
     * 
     * @param g
     *            graphics2d that we should draw on/with
     * @param card
     *            to draw highlight for
     */
    public void drawHighlightImage(Graphics2D g, Card card);

    /**
     * Get the standard size
     * 
     * @return the standard size for the cards drawn
     */
    public Dimension getSize();
}
