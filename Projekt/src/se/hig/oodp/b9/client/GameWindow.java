/**
 * 
 */
package se.hig.oodp.b9.client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Stack;

/**
 * The window that will show the game board
 */
public class GameWindow
{
    /**
     * The actual window
     */
    private JFrame frame;

    ICardPainter cardDrawer;

    /**
     * Launch the application.
     * 
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    GameWindow window = new GameWindow();
                    window.frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GameWindow()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(0, 0, 1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        try
        {
            cardDrawer = new CardPainter("/cards/playingCards.png", "/cards/playingCards.xml","/cards/Singles/cardBack_blue2.png", new Dimension(140, 190));

            frame.setContentPane(new JPanel()
            {
                @Override
                public void paint(Graphics g)
                {
                    Graphics2D g2d = (Graphics2D) g;

                    Stack<AffineTransform> transformStack = new Stack<AffineTransform>();

                    transformStack.push(g2d.getTransform());
                    {
                        g2d.translate(getWidth() / 2, getHeight() / 2);

                        for (int i = 0; i < 4; i++)
                        {
                            transformStack.push(g2d.getTransform());
                            {
                                double rot = (Math.PI / 2) * i;

                                g2d.rotate(rot);

                                for (int x = -2; x <= 1; x++)
                                {
                                    transformStack.push(g2d.getTransform());
                                    {
                                        g2d.translate((cardDrawer.getSize().getWidth() / 2 + 20) + x * (cardDrawer.getSize().getWidth() + 10), getHeight() / 2  - cardDrawer.getSize().getHeight() / 3);

                                        cardDrawer.drawImage(g2d, new Card(CardInfo.UNKNOWN));
                                    }
                                    g2d.setTransform(transformStack.pop());
                                }

                            }
                            g2d.setTransform(transformStack.pop());
                        }
                    }
                    g2d.setTransform(transformStack.pop());
                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Can't make card drawer!");
        }
    }
}