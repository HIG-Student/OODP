/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.data.*;
import se.hig.oodp.b9.model.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI
{
    private ShapeControl shapeControl = new ShapeControl();

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
                    GUI window = new GUI();
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
    public GUI()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 847, 551);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmLoad = new JMenuItem("Load");
        mnFile.add(mntmLoad);

        JMenuItem mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        JMenuItem mntmExport = new JMenuItem("Export");
        mnFile.add(mntmExport);

        JMenu mnCreate = new JMenu("Create");
        menuBar.add(mnCreate);

        JMenuItem mntmPoint = new JMenuItem("Point");
        mnCreate.add(mntmPoint);

        JMenuItem mntmLine = new JMenuItem("Line");
        mnCreate.add(mntmLine);

        JMenuItem mntmTriangle = new JMenuItem("Triangle");
        mnCreate.add(mntmTriangle);

        JMenuItem mntmRectangle = new JMenuItem("Rectangle");
        mntmRectangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                shapeControl.createRectangle(10, 10, 200, 300);
            }
        });
        mnCreate.add(mntmRectangle);

        JMenuItem mntmSquare = new JMenuItem("Square");
        mnCreate.add(mntmSquare);

        JMenuItem mntmEllipse = new JMenuItem("Ellipse");
        mnCreate.add(mntmEllipse);

        JMenuItem mntmCircle = new JMenuItem("Circle");
        mnCreate.add(mntmCircle);

        JMenu mnAction = new JMenu("Action");
        menuBar.add(mnAction);

        JMenuItem mntmPrintAll = new JMenuItem("Print All");
        mnAction.add(mntmPrintAll);

        JMenuItem mntmMoveAll = new JMenuItem("Move All");
        mnAction.add(mntmMoveAll);

        JMenuItem mntmScaleAll = new JMenuItem("Scale All");
        mnAction.add(mntmScaleAll);

        JMenuItem mntmRotateAll = new JMenuItem("Rotate All");
        mnAction.add(mntmRotateAll);

        JMenu mnClear = new JMenu("Clear");
        menuBar.add(mnClear);

        Canvas canvas = new Canvas()
        {
            @Override
            public void paint(Graphics g)
            {                
                for (Shape shape : shapeControl.getShapes())
                {
                    System.out.println("Paint: " + shape);
                    if (shape instanceof Ellipse)
                    {

                    }
                    else
                    {
                        Vertex2D[] nodes = shape.getNodes();

                        int[] xPoints = new int[nodes.length];
                        int[] yPoints = new int[nodes.length];

                        for (int i = 0; i < nodes.length; i++)
                        {
                            xPoints[i] = (int) nodes[i].getX();
                            yPoints[i] = (int) nodes[i].getY();
                        }
                        
                        g.setColor(Color.BLACK);
                        g.fillPolygon(xPoints, yPoints, nodes.length);
                    }
                }
            }
        };
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
    }
}
