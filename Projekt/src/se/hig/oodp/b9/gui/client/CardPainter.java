package se.hig.oodp.b9.gui.client;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.hig.oodp.b9.model.Card;

/**
 * Draws images from a spritesheet
 * 
 * @see ICardPainter
 */
public class CardPainter implements ICardPainter
{
    /**
     * Standard size of the cards
     */
    Dimension size;
    /**
     * Spritesheet with cards
     */
    Image cardSheet;
    /**
     * The back of the card
     */
    Image cardBack;
    /**
     * Rectangles that clips single cards from the spritesheet
     */
    HashMap<String, Rectangle> clipList = new HashMap<String, Rectangle>();

    /**
     * Draws cards from a spritesheet <br>
     * <br>
     * Inspiration for XML reader by
     * {@link <a href="http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/">mkyong</a>}
     * 
     * @param pathToCardsSheet
     *            the path to the image (spritesheet)
     * @param pathToXML
     *            path to an XML contaning information about the location of the
     *            cards in the spritesheet
     * @param pathToCardBack
     *            path to the image of the card back
     * @param size
     *            the standard size of the cards
     * @throws Exception
     *             if problem with loading resources
     */
    public CardPainter(String pathToCardsSheet, String pathToXML, String pathToCardBack, Dimension size) throws Exception
    {
        cardSheet = ImageIO.read(CardPainter.class.getResource(pathToCardsSheet));

        cardBack = ImageIO.read(CardPainter.class.getResource(pathToCardBack));

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(CardPainter.class.getResource(pathToXML).getFile()));

        NodeList nList = doc.getElementsByTagName("SubTexture");
        for (int i = 0; i < nList.getLength(); i++)
        {
            Node node = nList.item(i);
            clipList.put(node.getAttributes().getNamedItem("name").getNodeValue(), new Rectangle(Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue()), Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue()), Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue()), Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue())));
        }

        this.size = size;
    }

    @Override
    public void drawImage(Graphics2D g, Card card)
    {
        String type = "";
        String value = "";

        if (card.getCardInfo() == null)
        {
            Shape oldClip = g.getClip();
            g.setClip(null);
            g.drawImage(cardBack, -cardBack.getWidth(null) / 2, -cardBack.getHeight(null) / 2, null);
            g.setClip(oldClip);
        }
        else
        {
            switch (card.getCardInfo().getType())
            {
            case Hjärter:
                type = "Hearts";
                break;
            case Klöver:
                type = "Clubs";
                break;
            case Ruter:
                type = "Diamonds";
                break;
            case Spader:
                type = "Spades";
                break;
            }

            switch (card.getCardInfo().getValue())
            {
            case Ess:
                value = "A";
                break;
            case Kung:
                value = "K";
                break;
            case Dam:
                value = "Q";
                break;
            case Knekt:
                value = "J";
                break;
            default:
                value = "" + (card.getCardInfo().getValue().ordinal() + 1);
            }

            Shape oldClip = g.getClip();
            g.setClip(null);
            Rectangle clip = clipList.get("card" + type + value + ".png");
            AffineTransform transform = g.getTransform();

            g.translate(-clip.width / 2, -clip.height / 2);
            g.translate(-clip.x, -clip.y);
            g.clipRect(clip.x, clip.y, clip.width, clip.height);
            g.drawImage(cardSheet, 0, 0, null);

            g.setTransform(transform);
            g.setClip(oldClip);
        }
    }

    @Override
    public void drawHighlightImage(Graphics2D g, Card card)
    {
        Shape oldClip = g.getClip();
        g.setClip(null);
        // http://stackoverflow.com/a/6734194
        g.fillRoundRect(-cardBack.getWidth(null) / 2, -cardBack.getHeight(null) / 2, cardBack.getWidth(null), cardBack.getHeight(null), 5, 5);
        g.setClip(oldClip);
    }

    @Override
    public Dimension getSize()
    {
        return size;
    }
}
