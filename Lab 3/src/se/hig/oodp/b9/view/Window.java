/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.data.PolyShape;
import se.hig.oodp.b9.data.Shape;
import se.hig.oodp.b9.model.PrimitivesPainter;
import se.hig.oodp.b9.model.ShapeControl;

public class Window extends JFrame
{
    private ShapeControl shapeControl;

    private abstract class Canvas extends JPanel implements PrimitivesPainter
    {

    }

    private JPanel contentPane;

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
                    Window frame = new Window();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Window()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new Canvas()
        {
            private Graphics2D g;

            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                this.g = (Graphics2D) g;

                System.out.println("DRAW!");

                for (Shape s : shapeControl.getShapes())
                {
                    g.setColor(Color.RED);
                    s.draw(this);
                }
            }

            @Override
            public void paintPolygon(Vertex2D[] nodes)
            {
                System.out.println("DRAW poly");

                int[] xPoints = new int[nodes.length];
                int[] yPoints = new int[nodes.length];

                for (int i = 0; i < nodes.length; i++)
                {
                    xPoints[i] = (int) nodes[i].getX();
                    yPoints[i] = (int) nodes[i].getY();
                }

                g.fillPolygon(xPoints, yPoints, nodes.length);
            }

            @Override
            public void paintEllipse(Vertex2D center, double width, double height, double rotation)
            {
                g.fillOval((int) center.getX() - (int) width / 2, (int) center.getY() - (int) height / 2, (int) width, (int) height);
            }

            @Override
            public void paintLine(Vertex2D a, Vertex2D b)
            {
                g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
            }
        };

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        shapeControl = new ShapeControl()
        {
            @Override
            public void onChange()
            {
                contentPane.repaint();
            }
        };

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

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
        mntmPoint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> center = new Pair<Vertex2D>("Center", Vertex2D.class);
                arr.add(center);
                
                if (InitParamsDialog.showDialog("Create point", arr))
                {
                    shapeControl.createPoint(center.value.getX(),center.value.getY());
                }
            }
        });
        mnCreate.add(mntmPoint);

        JMenuItem mntmLine = new JMenuItem("Line");
        mntmLine.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> nodeA = new Pair<Vertex2D>("Node A", Vertex2D.class);
                arr.add(nodeA);

                Pair<Vertex2D> nodeB = new Pair<Vertex2D>("Node B", Vertex2D.class);
                arr.add(nodeB);

                if (InitParamsDialog.showDialog("Create line", arr))
                {
                    shapeControl.createLine(nodeA.value.getX(), nodeA.value.getY(), nodeB.value.getX(), nodeB.value.getY());
                }
            }
        });
        mnCreate.add(mntmLine);

        JMenuItem mntmTriangle = new JMenuItem("Triangle");
        mntmTriangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> nodeA = new Pair<Vertex2D>("Node A", Vertex2D.class);
                arr.add(nodeA);

                Pair<Vertex2D> nodeB = new Pair<Vertex2D>("Node B", Vertex2D.class);
                arr.add(nodeB);

                Pair<Vertex2D> nodeC = new Pair<Vertex2D>("Node C", Vertex2D.class);
                arr.add(nodeC);

                if (InitParamsDialog.showDialog("Create triangle", arr))
                {
                    shapeControl.createTriangle(nodeA.value.getX(), nodeA.value.getY(), nodeB.value.getX(), nodeB.value.getY(), nodeC.value.getX(), nodeC.value.getY());
                }
            }
        });
        mnCreate.add(mntmTriangle);

        JMenuItem mntmRectangle = new JMenuItem("Rectangle");
        mntmRectangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> center = new Pair<Vertex2D>("Center", Vertex2D.class);
                arr.add(center);

                Pair<Integer> width = new Pair<Integer>("Width", Integer.class);
                arr.add(width);

                Pair<Integer> height = new Pair<Integer>("Height", Integer.class);
                arr.add(height);

                if (InitParamsDialog.showDialog("Create rectangle", arr))
                {
                    shapeControl.createRectangle(center.value.getX(), center.value.getY(), width.value, height.value);
                }
            }
        });
        mnCreate.add(mntmRectangle);

        JMenuItem mntmSquare = new JMenuItem("Square");
        mntmSquare.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> center = new Pair<Vertex2D>("Center", Vertex2D.class);
                arr.add(center);

                Pair<Integer> size = new Pair<Integer>("Size", Integer.class);
                arr.add(size);

                if (InitParamsDialog.showDialog("Create square", arr))
                {
                    shapeControl.createSquare(center.value.getX(), center.value.getY(), size.value);
                }
            }
        });
        mnCreate.add(mntmSquare);

        JMenuItem mntmEllipse = new JMenuItem("Ellipse");
        mntmEllipse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> center = new Pair<Vertex2D>("Center", Vertex2D.class);
                arr.add(center);

                Pair<Integer> width = new Pair<Integer>("Width", Integer.class);
                arr.add(width);

                Pair<Integer> height = new Pair<Integer>("Height", Integer.class);
                arr.add(height);

                if (InitParamsDialog.showDialog("Create ellipse", arr))
                {
                    shapeControl.createEllipse(center.value.getX(), center.value.getY(), width.value, height.value);
                }
            }
        });
        mnCreate.add(mntmEllipse);

        JMenuItem mntmCircle = new JMenuItem("Circle");
        mntmCircle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> center = new Pair<Vertex2D>("Center", Vertex2D.class);
                arr.add(center);

                Pair<Integer> size = new Pair<Integer>("Size", Integer.class);
                arr.add(size);

                if (InitParamsDialog.showDialog("Create circle", arr))
                {
                    shapeControl.createCircle(center.value.getX(), center.value.getY(), size.value);
                }
            }
        });
        mnCreate.add(mntmCircle);

        JMenu mnAction = new JMenu("Action");
        menuBar.add(mnAction);

        JMenuItem mntmPrintAll = new JMenuItem("Print All");
        mnAction.add(mntmPrintAll);

        JMenuItem mntmMoveAll = new JMenuItem("Move All");
        mntmMoveAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Vertex2D> center = new Pair<Vertex2D>("Offset", Vertex2D.class);
                arr.add(center);

                if(InitParamsDialog.showDialog("Move all", arr))
                {
                    shapeControl.moveAll(center.value.getX(), center.value.getY());
                }
            }
        });
        mnAction.add(mntmMoveAll);

        JMenuItem mntmScaleAll = new JMenuItem("Scale All");
        mntmScaleAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Double> scaleX = new Pair<Double>("Offset x", Double.class);
                arr.add(scaleX);
                
                Pair<Double> scaleY = new Pair<Double>("Offset y", Double.class);
                arr.add(scaleY);

                if(InitParamsDialog.showDialog("Scale all", arr))
                {
                    shapeControl.scaleAll(scaleX.value, scaleY.value);
                }
            }
        });
        mnAction.add(mntmScaleAll);

        JMenuItem mntmRotateAll = new JMenuItem("Rotate All");
        mntmRotateAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Pair<?>> arr = new ArrayList<>();

                Pair<Integer> angle = new Pair<Integer>("Angle", Integer.class);
                arr.add(angle);

                if(InitParamsDialog.showDialog("Rotate all", arr))
                {
                    shapeControl.rotateAll(angle.value);
                }
            }
        });
        mnAction.add(mntmRotateAll);
        
        JMenuItem mntmRemoveAll = new JMenuItem("Remove All");
        mntmRemoveAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                shapeControl.removeAll();
            }
        });
        mnAction.add(mntmRemoveAll);

        setContentPane(contentPane);

    }
}
