/**
 * 
 */
package se.hig.oodp.b9.client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Panel;

import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;

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
     * Launch the application.
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
        frame.setBounds(100, 100, 844, 559);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("120px"), FormFactory.GLUE_COLSPEC, ColumnSpec.decode("120px"), }, new RowSpec[] { RowSpec.decode("fill:120px"), FormFactory.GLUE_ROWSPEC, RowSpec.decode("fill:120px"), }));

        Panel panelNorth = new Panel();
        frame.getContentPane().add(panelNorth, "2, 1");

        Panel panelWest = new Panel();
        frame.getContentPane().add(panelWest, "1, 2");

        Panel panelCenter = new Panel();
        frame.getContentPane().add(panelCenter, "2, 2");
        panelCenter.setLayout(null);

        Panel panelEast = new Panel();
        frame.getContentPane().add(panelEast, "3, 2");

        Panel panelSouth = new Panel();
        frame.getContentPane().add(panelSouth, "2, 3");

        try
        {
            JSVGCanvas svgCanvas = new JSVGCanvas();
            svgCanvas.setURI(GraphicalCard.class.getResource("/anglo.svg").toURI().toString());
            panelSouth.add(svgCanvas);
        }
        catch (Exception e)
        {
        }
    }
}
