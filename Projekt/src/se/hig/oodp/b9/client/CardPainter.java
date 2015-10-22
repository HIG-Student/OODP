package se.hig.oodp.b9.client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.hig.oodp.b9.Card;

public class CardPainter implements ICardPainter
{
    Image cardSheet;
    HashMap<String, Rectangle> clipList = new HashMap<String, Rectangle>();

    public CardPainter(String pathToCardsSheet, String pathToXML) throws Exception
    {
        cardSheet = ImageIO.read(CardPainter.class.getResource(pathToCardsSheet)); // "/cards/playingCards.png"

        // http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(CardPainter.class.getResource(pathToXML).getFile()));

        NodeList nList = doc.getElementsByTagName("SubTexture");
        for (int i = 0; i < nList.getLength(); i++)
        {
            Node node = nList.item(i);
            clipList.put(node.getAttributes().getNamedItem("name").getNodeValue(), new Rectangle(Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue()), Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue()), Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue()), Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue())));
        }
    }

    public void drawImage(Graphics2D g, Card card)
    {
        String type = "";
        String value = "";

        if (card.getCardInfo() == null)
        {
            // draw back
            Rectangle clip = clipList.get("cardJoker.png");
            g.translate(-clip.x, -clip.y);
            g.clipRect(clip.x, clip.y, clip.width, clip.height);
            g.drawImage(cardSheet, 0, 0, null);
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

            Rectangle clip = clipList.get("card" + type + value + ".png");
            g.translate(-clip.x, -clip.y);
            g.clipRect(clip.x, clip.y, clip.width, clip.height);
            g.drawImage(cardSheet, 0, 0, null);
        }
    }
}
