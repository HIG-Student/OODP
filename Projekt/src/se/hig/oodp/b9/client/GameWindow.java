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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.Player;

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

    ClientGame game;

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

        try
        {
            cardDrawer = new CardPainter("/cards/playingCards.png", "/cards/playingCards.xml", "/cards/Singles/cardBack_blue2.png", new Dimension(140, 190));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame, "Can't make card painter", "Error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Pick server \\

        // Start own server \\

        Player player = new Player("MrGNU");
        game = new ClientGame(player,null);
        
        // LocalNetworkingUnit lnu = new LocalNetworkingUnit();
        
        // new server
        
        // new game (<- server)

        // game = lnu.cg;

        runGame();
    }

    public void runGame()
    {
        frame.setContentPane(new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);

                System.out.println(game.table != null ? game.table.deck.size() : "-");
                
                if (game.table == null)
                    return;

                Graphics2D g2d = (Graphics2D) g;

                Stack<AffineTransform> transformStack = new Stack<AffineTransform>();

                transformStack.push(g2d.getTransform());
                {
                    transformStack.push(g2d.getTransform());
                    {
                        g2d.translate(getWidth() / 2, getHeight() / 2);

                        // Player draw
                        for (int i = 0; i < 4; i++)
                        {
                            if (i == game.table.players.size())
                                return;

                            transformStack.push(g2d.getTransform());
                            {
                                double rot = (Math.PI / 2) * i;

                                g2d.rotate(rot);

                                // Player card draw
                                int index = 0;
                                for (int x = -2; x <= 1; x++)
                                {
                                    Player player = game.table.players.get(i);
                                    CardCollection collection = game.table.playerHands.get(player);
                                    if (index == game.table.playerHands.get(game.table.players.get(i)).size())
                                        return;

                                    transformStack.push(g2d.getTransform());
                                    {
                                        g2d.translate((cardDrawer.getSize().getWidth() / 2 + 20) + x * (cardDrawer.getSize().getWidth() + 10), getHeight() / 2 - cardDrawer.getSize().getHeight() / 3);

                                        cardDrawer.drawImage(g2d, game.table.playerHands.get(game.table.players.get(i)).get(index++));
                                    }
                                    g2d.setTransform(transformStack.pop());
                                }

                            }
                            g2d.setTransform(transformStack.pop());
                        }
                    }
                    g2d.setTransform(transformStack.pop());

                    g2d.translate(getWidth() / 2, cardDrawer.getSize().getHeight() / 3 + cardDrawer.getSize().getHeight() + 20);

                    // Pool draw
                    int index = 0;
                    for (int y = 0; y < 5; y++)
                    {
                        if (index == game.table.pool.size())
                            return;

                        for (int x = -6; x < 5; x++)
                        {
                            if (index == game.table.pool.size())
                                return;

                            transformStack.push(g2d.getTransform());
                            {
                                g2d.translate(cardDrawer.getSize().getWidth() / 3 + x * cardDrawer.getSize().getWidth() / 3, y * 100);

                                cardDrawer.drawImage(g2d, game.table.pool.get(index++));
                            }
                            g2d.setTransform(transformStack.pop());
                        }
                    }
                }
                g2d.setTransform(transformStack.pop());
            }
        });
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
    }
}