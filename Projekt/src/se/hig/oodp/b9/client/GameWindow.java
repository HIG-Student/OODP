/**
 * 
 */
package se.hig.oodp.b9.client;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.hig.oodp.b9.Card;

import java.io.IOException;
import java.net.URISyntaxException;

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
        frame.setBounds(0, 0, 1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        try
        {
            cardDrawer = new CardPainter("/cards/playingCards.png", "/cards/playingCards.xml");

            frame.setContentPane(new JPanel()
            {
                @Override
                public void paint(Graphics g)
                {
                    g.fillRect(0, 0, 100, 100);
                    cardDrawer.drawImage((Graphics2D)g, new Card());
                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Can't make card drawer!");
        }
    }
}