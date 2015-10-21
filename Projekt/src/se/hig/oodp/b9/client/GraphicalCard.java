package se.hig.oodp.b9.client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import se.hig.oodp.b9.Card;

/**
 * A graphic representation of a card
 */
@SuppressWarnings("serial")
public class GraphicalCard extends JPanel
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
        System.out.println("Creae");
    }
    
    public GraphicalCard()
    {
        System.out.println("Creae null");
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        // g.drawRect(0, 0, 100, 100);
        g.drawImage(cards, 0, 0, null);
        //g.drawImage(GraphicalCard.cards, 0, 0, null);
        System.out.println("draw");
    }
    
    // http://stackoverflow.com/questions/6845231/how-to-correctly-get-image-from-resources-folder-in-netbeans
    public static BufferedImage cards;
}
