/**
 * 
 */
package se.hig.oodp.b9.gui.client;

import java.awt.BorderLayout;
import java.awt.Color;
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

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Two;
import se.hig.oodp.b9.logic.client.ClientGame;
import se.hig.oodp.b9.model.CardInfo;
import se.hig.oodp.b9.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

/**
 * The window that will show the game board
 */
public class GameWindow
{
    /**
     * The actual window
     */
    private JFrame frame;

    /**
     * Card info mapped to cards
     */
    // HashMap<UUID, CardInfo> cardInfoMapper = new HashMap<UUID, CardInfo>();

    /**
     * Painter of cards
     */
    ICardPainter cardPainter;

    /**
     * Compares player score (highest first)
     */
    private Comparator<Two<Player, Integer>> compare = (Two<Player, Integer> a, Two<Player, Integer> b) -> b.getTwo().compareTo(a.getTwo());

    /**
     * The game
     */
    ClientGame game;

    /**
     * Start a new game window with a game
     * 
     * @param game
     *            the game
     */
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
     * Create the application
     * 
     * @param game
     *            the game
     */
    public GameWindow(ClientGame game)
    {
        initialize();

        frame.setTitle("Kasino: " + game.getMe().getName());

        this.game = game;

        game.onChange.add((bool) ->
        {
            frame.repaint();
        });

        try
        {
            cardPainter = new CardPainter("cards/playingCards.png", "cards/playingCards.xml", "cards/Singles/cardBack_blue2.png", new Dimension(140, 190));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame, "Can't make card painter", "Error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        runGame();
        game.sendGreeting();
    }

    /**
     * Start the game logic
     */
    @SuppressWarnings("serial")
    public void runGame()
    {
        List<Rectangle> boundings = new ArrayList<Rectangle>();
        HashMap<Rectangle, UUID> boundingMaps = new HashMap<Rectangle, UUID>();
        Move selection = new Move(null);

        game.onTurnStatus.add(ok ->
        {
            if (!ok)
                selection.setActiveCard(null);
            frame.repaint();
        });

        game.onEndGame.add(set ->
        {
            StringBuilder builder = new StringBuilder("This game:\n");

            List<Two<Player, Integer>> points = new ArrayList<Two<Player, Integer>>();
            set.getOne().forEach((a, b) -> points.add(new Two<Player, Integer>(a, b)));
            Collections.sort(points, compare);
            points.forEach(a -> builder.append("\t" + a.getOne().getName() + ": " + a.getTwo().intValue() + "\n"));

            builder.append("\n\nTotal:\n");
            List<Two<Player, Integer>> totalPoints = new ArrayList<Two<Player, Integer>>();
            set.getTwo().forEach((a, b) -> totalPoints.add(new Two<Player, Integer>(a, b)));
            Collections.sort(totalPoints, compare);
            totalPoints.forEach(a -> builder.append("\t" + a.getOne().getName() + ": " + a.getTwo().intValue() + "\n"));

            JOptionPane.showMessageDialog(frame, builder.toString());
        });

        frame.add(new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);

                boundings.clear();
                boundingMaps.clear();

                if (game.getTable() == null)
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
                                    UUID[] collection = game.getTable().getPlayerHandIds(player);
                                    if (index >= collection.length)
                                        break;

                                    // Might break a tiny bit here, i blaim Mr
                                    // GNU

                                    UUID card = collection[index];

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

                                        cardPainter.drawImage(g2d, game.getCardInfo(card));
                                        if ((selection.getActiveCard() != null && selection.getActiveCard().equals(card)) || selection.takeContains(card))
                                        {
                                            g2d.setColor(new Color(0, 0, 0, 0.5f));
                                            cardPainter.drawHighlightImage(g2d, game.getCardInfo(card));
                                        }

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
                        if (index == game.getTable().getPoolIds().length)
                            return;

                        for (int x = -6; x < 5; x++)
                        {
                            if (index == game.getTable().getPoolIds().length)
                                return;

                            UUID card = game.getTable().getPoolIds()[index];

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

                                cardPainter.drawImage(g2d, game.getCardInfo(card));
                                if (selection.takeContains(card))
                                {
                                    g2d.setColor(new Color(0, 0, 0, 0.5f));
                                    cardPainter.drawHighlightImage(g2d, game.getCardInfo(card));
                                }
                                else
                                    if (selection.currentTakeContains(card))
                                    {
                                        g2d.setColor(new Color(0, 1, 0, 0.5f));
                                        cardPainter.drawHighlightImage(g2d, game.getCardInfo(card));
                                    }
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
                        UUID card = boundingMaps.get(rec);

                        UUID collectionId = game.getTable().getCardLocation(card);

                        if (game.getTable().poolUUID.equals(collectionId))
                        {
                            if (selection.getActiveCard() == null)
                                break;

                            selection.toggleTake(card);
                        }
                        else
                            if (game.getMe().equals(game.getTable().getOwnerOfCollectionId(collectionId)))
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
                        // Nothing to do
                    }
                    else
                    {
                        if (selection.getCurrentTakeCards().length == 0)
                        {
                            game.setMyTurn(false);
                            game.makeMove(selection);
                            selection.setActiveCard(null);
                        }
                        else
                        {
                            selection.nextTake();
                        }
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