/**
 * 
 */
package se.hig.oodp.b9.gui.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import se.hig.oodp.b9.communication.Move;
import se.hig.oodp.b9.logic.Two;
import se.hig.oodp.b9.logic.client.ClientGame;
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
     * Painter of cards
     */
    ICardPainter cardPainter;

    /**
     * The actual window
     */
    private JFrame frame;

    /**
     * The content panel
     */
    private GameArea gameArea;

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

        this.game = game;

        frame.setTitle("Kasino: " + game.getMe().getName());

        try
        {
            cardPainter = new CardPainter("/cards/playingCards.png", "cards/playingCards.xml", "/cards/Singles/cardBack_blue2.png", new Dimension(140, 190));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame, "Can't make card painter", "Error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        game.getNetworker().onClose.add((str) ->
        {
            System.out.println("Networker closed!");

            JOptionPane.showMessageDialog(GameWindow.this.frame, "Closed:\n\n\t" + str, "Error", JOptionPane.ERROR_MESSAGE);

            frame.setVisible(false);
            frame.dispose();
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

        gameArea = new GameArea();
        gameArea.setOpaque(true);
        frame.setContentPane(gameArea);
        frame.pack();

        game.sendGreeting();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension size = new Dimension(1006, 1029 + 20);

        frame.setSize(size);
        frame.setPreferredSize(size);

        frame.setLocationRelativeTo(null);

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

    @SuppressWarnings("serial")
    class GameArea extends JPanel
    {
        /**
         * Card boundings
         */
        List<Rectangle> boundings;

        /**
         * Mapping to cards
         */
        HashMap<Rectangle, UUID> boundingMaps;

        /**
         * The selection
         */
        Move selection;

        public GameArea()
        {
            boundings = new ArrayList<Rectangle>();
            boundingMaps = new HashMap<Rectangle, UUID>();
            selection = new Move(null);

            game.onTurnStatus.add(ok ->
            {
                if (!ok)
                    selection.setActiveCard(null);

                repaint();
            });

            game.onChange.add((bool) ->
            {
                repaint();
            });

            frame.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e)
                {
                    System.out.println("KEY!");
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
                            GameArea.this.repaint();
                        }
                    }
                }
            });

            addMouseListener(new MouseAdapter()
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

                    GameArea.this.repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

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

                            Player player = game.getTable().getPlayers()[i];
                            UUID[] collection = game.getTable().getPlayerHandIds(player);

                            int index = 0;
                            for (int x = -2; x <= 1; x++)
                            {
                                if (index >= collection.length)
                                    break;

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

                UUID[] poolCardIds = game.getTable().getPoolIds();

                for (int y = 0; y < 5; y++)
                {
                    if (index == poolCardIds.length)
                        return;

                    for (int x = -6; x < 5; x++)
                    {
                        if (index == poolCardIds.length)
                            return;

                        UUID card = poolCardIds[index];

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
    }
}