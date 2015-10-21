/**
 * 
 */
package se.hig.oodp.b9.client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;

import se.hig.oodp.b9.Card;

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

    public static JSVGCanvas svgCanvas;

    /**
     * Launch the application.
     * 
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws URISyntaxException
    {
        {

        }

        svgCanvas = new JSVGCanvas()
        {
            @Override
            public void paint(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;

                Card card = new Card(Card.Type.Klöver, Card.Value.values()[(int) (System.currentTimeMillis() / 1000) % 12]);

                int x = (int) (card.value.ordinal() * this.getSVGDocumentSize().getWidth() / 13);
                int y = (int) (card.type.ordinal() * this.getSVGDocumentSize().getHeight() / 5);

                System.out.println(this.getSVGDocumentSize().getWidth());

                g.translate(-x, -y);
                g.clipRect(x, y, (int) (this.getSVGDocumentSize().getWidth() / 13 + 0.5), (int) (this.getSVGDocumentSize().getHeight() / 5));

                super.paint(g);

                super.invalidate();
            }
        };
        svgCanvas.setDoubleBufferedRendering(true);
        svgCanvas.setDisableInteractions(true);

        svgCanvas.setURI(GameWindow.class.getResource("/anglo.svg").toString());
        svgCanvas.setSize(new Dimension(100, 100));

        svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter()
        {
            public void gvtBuildCompleted(GVTTreeBuilderEvent e)
            {
                System.out.println("Build Done.");
                // done!
                runGUI();
            }
        });
    }

    static void runGUI()
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

        frame.setContentPane(svgCanvas);
    }
}