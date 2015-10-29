/**
 * 
 */
package se.hig.oodp.b9.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import se.hig.oodp.b9.Card;
import se.hig.oodp.b9.CardCollection;
import se.hig.oodp.b9.CardInfo;
import se.hig.oodp.b9.Player;
import se.hig.oodp.b9.Rules;
import se.hig.oodp.b9.Move;
import se.hig.oodp.b9.server.ServerGame;
import se.hig.oodp.b9.server.ServerNetworkerSocket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    ICardPainter cardPainter;

    ClientGame game;

    /**
     * Launch the application.
     * 
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args)
    {
        int port = 59440;

        Player me = new Player("MrGNU");

        ClientNetworkerSocket clientNetworker = null;

        try
        {
            clientNetworker = new ClientNetworkerSocket("127.0.0.1", port);
            clientNetworker.onMessage.add(msg ->
            {
                System.out.println("Client: " + msg.getMessage() + (msg.getSource() != null ? (" (from: " + msg.getSource() + ")") : ""));
            });
        }
        catch (IOException e)
        {
            System.out.println("Can't listen to server!\n\t" + e.getMessage());
            System.exit(1);
        }

        start(new ClientGame(me, clientNetworker));
    }

    public static void start(ClientGame game)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    GameWindow window = new GameWindow(game);
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
    public GameWindow(ClientGame game)
    {
        initialize();

        frame.setTitle("Kasino: " + game.me.getName());

        this.game = game;

        game.onChange.add(() ->
        {
            frame.repaint();
        });

        try
        {
            cardPainter = new CardPainter("/cards/playingCards.png", "/cards/playingCards.xml", "/cards/Singles/cardBack_blue2.png", new Dimension(140, 190));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame, "Can't make card painter", "Error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        runGame();
        game.sendGreeting();
    }

    public void runGame()
    {
        List<Rectangle> boundings = new ArrayList<Rectangle>();
        HashMap<Rectangle, Card> boundingMaps = new HashMap<Rectangle, Card>();
        Move selection = new Move(null, new ArrayList<Card>());

        game.turnStatus.add(ok ->
        {
            // TODO: is correct??
            if (!ok)
                selection.setActiveCard(null);
            frame.repaint();
        });

        frame.add(new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);

                boundings.clear();
                boundingMaps.clear();

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
                            if (i == game.getTable().getPlayers().length)
                                break;

                            transformStack.push(g2d.getTransform());
                            {
                                double rot = (Math.PI / 2) * (i - game.getTable().getPlayerIndex(game.getMe()));

                                g2d.rotate(rot);

                                // Player card draw
                                int index = 0;
                                for (int x = -2; x <= 1; x++)
                                {
                                    Player player = game.getTable().getPlayers()[i];
                                    CardCollection collection = game.getTable().getPlayerHand(player);
                                    if (index == collection.size())
                                        break;

                                    Card card = collection.get(index);

                                    transformStack.push(g2d.getTransform());
                                    {
                                        g2d.translate((cardPainter.getSize().getWidth() / 2 + 20) + x * (cardPainter.getSize().getWidth() + 10), getHeight() / 2 - cardPainter.getSize().getHeight() / 3 + 20);

                                        Point2D start = g2d.getTransform().transform(new Point((int) (-cardPainter.getSize().getWidth() / 2), (int) (-cardPainter.getSize().getHeight() / 2)), null);
                                        Rectangle rec = new Rectangle(new Point((int) start.getX(), (int) start.getY()));

                                        // http://stackoverflow.com/questions/2244157/reverse-java-graphics2d-scaled-and-rotated-coordinates
                                        rec.add(g2d.getTransform().transform(new Point((int) (-cardPainter.getSize().getWidth() / 2), (int) (-cardPainter.getSize().getHeight() / 2)), null));
                                        rec.add(g2d.getTransform().transform(new Point((int) (cardPainter.getSize().getWidth() / 2), (int) (-cardPainter.getSize().getHeight() / 2)), null));
                                        rec.add(g2d.getTransform().transform(new Point((int) (cardPainter.getSize().getWidth() / 2), (int) (cardPainter.getSize().getHeight() / 2)), null));
                                        rec.add(g2d.getTransform().transform(new Point((int) (-cardPainter.getSize().getWidth() / 2), (int) (cardPainter.getSize().getHeight() / 2)), null));

                                        boundings.add(0, rec);
                                        boundingMaps.put(rec, card);

                                        cardPainter.drawImage(g2d, card);
                                        if ((selection.getActiveCard() != null && selection.getActiveCard().equals(card)) || selection.takeContains(card))
                                            cardPainter.drawHighlightImage(g2d, card);

                                    }
                                    g2d.setTransform(transformStack.pop());

                                    index++;
                                }

                            }
                            g2d.setTransform(transformStack.pop());
                        }
                    }
                    g2d.setTransform(transformStack.pop());

                    g2d.translate(getWidth() / 2, cardPainter.getSize().getHeight() / 3 + cardPainter.getSize().getHeight());

                    // Pool draw
                    int index = 0;
                    for (int y = 0; y < 5; y++)
                    {
                        if (index == game.getTable().getPool().size())
                            return;

                        for (int x = -6; x < 5; x++)
                        {
                            if (index == game.getTable().getPool().size())
                                return;

                            Card card = game.getTable().getPool().get(index);

                            transformStack.push(g2d.getTransform());
                            {
                                g2d.translate(cardPainter.getSize().getWidth() / 3 + x * cardPainter.getSize().getWidth() / 3, y * 100);

                                Point2D start = g2d.getTransform().transform(new Point((int) (-cardPainter.getSize().getWidth() / 2), (int) (-cardPainter.getSize().getHeight() / 2)), null);
                                Rectangle rec = new Rectangle(new Point((int) start.getX(), (int) start.getY()));

                                // http://stackoverflow.com/questions/2244157/reverse-java-graphics2d-scaled-and-rotated-coordinates
                                rec.add(g2d.getTransform().transform(new Point((int) (-cardPainter.getSize().getWidth() / 2), (int) (-cardPainter.getSize().getHeight() / 2)), null));
                                rec.add(g2d.getTransform().transform(new Point((int) (cardPainter.getSize().getWidth() / 2), (int) (-cardPainter.getSize().getHeight() / 2)), null));
                                rec.add(g2d.getTransform().transform(new Point((int) (cardPainter.getSize().getWidth() / 2), (int) (cardPainter.getSize().getHeight() / 2)), null));
                                rec.add(g2d.getTransform().transform(new Point((int) (-cardPainter.getSize().getWidth() / 2), (int) (cardPainter.getSize().getHeight() / 2)), null));

                                boundings.add(0, rec);
                                boundingMaps.put(rec, card);

                                cardPainter.drawImage(g2d, card);
                                if ((selection.getActiveCard() != null && selection.getActiveCard().equals(card)) || selection.takeContains(card))
                                    cardPainter.drawHighlightImage(g2d, card);
                            }
                            g2d.setTransform(transformStack.pop());

                            index++;
                        }
                    }
                }
                g2d.setTransform(transformStack.pop());
            }
        }, BorderLayout.CENTER);
        frame.pack();

        frame.repaint();

        frame.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (!game.isMyTurn())
                    return;

                for (Rectangle rec : boundings)
                    if (rec.contains(e.getPoint()))
                    {
                        Card card = boundingMaps.get(rec);

                        CardCollection collection = game.getTable().getCardLocation(card);

                        if (collection == game.getTable().getPool())
                        {
                            if (selection.getActiveCard() == null)
                                break;

                            selection.toggleTake(card);
                        }
                        else
                            if (collection.owner != null && collection.owner.equals(game.getMe()))
                            {
                                selection.toggleActive(card);
                            }

                        break;
                    }

                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
            }

        });

        frame.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if (selection.getActiveCard() == null)
                    {
                        // i dunno
                    }
                    else
                    {
                        game.myTurn = false;
                        game.makeMove(selection);
                        selection.setActiveCard(null);
                        frame.repaint();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension size = new Dimension(1006, 1029 + 20);

        frame.setSize(size);
        frame.setPreferredSize(size);

        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());

        // http://stackoverflow.com/a/9093526
        frame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                super.windowClosing(windowEvent);

                game.end("Closed window");
            }
        });
    }
}