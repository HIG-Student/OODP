package se.hig.oodp.b9.client;

import java.awt.Dimension;
import java.awt.Graphics2D;

import se.hig.oodp.b9.Card;

public interface ICardPainter
{
    public void drawImage(Graphics2D g, Card card);
    public Dimension getSize();
}
