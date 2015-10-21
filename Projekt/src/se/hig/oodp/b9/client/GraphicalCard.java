package se.hig.oodp.b9.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.batik.swing.JSVGCanvas;

import se.hig.oodp.b9.Card;

/**
 * A graphic representation of a card
 */
@SuppressWarnings("serial")
public class GraphicalCard extends JFrame
{
    /**
     * The card this graphical component represents
     */
    public Card card;
    
    /**
     * Create a new graphical representation of a card
     * @param card the card to represent
     */
    public GraphicalCard(Card card)
    {
        this.card = card;
    }
    
    @Override
    public void paint(Graphics g)
    {
        
    }
    
    // http://stackoverflow.com/questions/6845231/how-to-correctly-get-image-from-resources-folder-in-netbeans
    public static BufferedImage getCardGraphic(Card card)
    {
        if(card == null)
            try
            {
                return  ImageIO.read(GraphicalCard.class.getResource("/kortlek/hjarter_atta.png"));
            }
            catch (IOException e)
            {
                return null;
            }
        
        try
        {
            return  ImageIO.read(GraphicalCard.class.getResource("/kortlek/hjarter_atta.png"));
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
