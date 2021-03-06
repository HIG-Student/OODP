/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.StringJoiner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import se.hig.oodp.FigurePainter;
import se.hig.oodp.PrimitivesPainter;
import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.data.Ellipse;
import se.hig.oodp.b9.data.Shape;
import se.hig.oodp.b9.model.ShapeControl;

@SuppressWarnings("serial")
public class Window extends JFrame
{
    private ShapeControl shapeControl;

    private abstract class Canvas extends JPanel implements PrimitivesPainter , FigurePainter
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
        Window me = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 800);
        setLocationRelativeTo(null);

        contentPane = new Canvas()
        {
            private Graphics2D g;
            private Shape toDraw;

            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                this.g = (Graphics2D) g;

                System.out.println("DRAW!");

                paintAll();
            }

            @Override
            public void paintPoint(Vertex2D node)
            {
                g.fillOval((int) node.getX(), (int) node.getY(), 5, 5);
            }

            @Override
            public void paintEllipse(Vertex2D center, double width, double height)
            {
                g.rotate(Math.toRadians(((Ellipse) toDraw).getRotation()), toDraw.getNodes()[0].getX(), toDraw.getNodes()[0].getY());
                g.drawOval((int) center.getX() - (int) width / 2, (int) center.getY() - (int) height / 2, (int) width, (int) height);
            }

            @Override
            public void paintLine(Vertex2D a, Vertex2D b)
            {
                g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
            }

            @Override
            public void paintAll()
            {
                for (Shape s : shapeControl.getShapes())
                {
                    toDraw = s;
                    g.setColor(Color.black);
                    g.setStroke(new BasicStroke(3));;
                    AffineTransform transform = this.g.getTransform();
                    s.draw(this);
                    this.g.setTransform(transform);
                }
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

            @Override
            public void printAll()
            {
                StringJoiner joiner = new StringJoiner("\n");
                for (Shape s : shapeControl.getShapes())
                    joiner.add(s.asString());
                new TextPopUp("Printout", joiner.toString());
            }
        };

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmLoad = new JMenuItem("Load");
        mntmLoad.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ShapeIO.showLoadShapesDialog(me, shapeControl);
            }
        });
        mnFile.add(mntmLoad);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ShapeIO.showSaveShapesDialog(me, shapeControl);
            }
        });
        mnFile.add(mntmSave);

        JMenuItem mntmExport = new JMenuItem("Export");
        mntmExport.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // http://stackoverflow.com/a/1349264

                int w = contentPane.getWidth();
                int h = contentPane.getHeight();
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = bi.createGraphics();
                contentPane.paint(g);

                try
                {
                    File file = ShapeIO.saveFileDialog(me, "Export to image", "png");
                    ImageIO.write(bi, "png", file);
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(contentPane.getParent(), "Could not export to image!\n\n" + exception.getMessage(), "IOException!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        mnFile.add(mntmExport);

        JMenu mnCreate = new JMenu("Create");
        menuBar.add(mnCreate);

        JMenuItem mntmPoint = new JMenuItem("Point");
        mntmPoint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(center);

                if (InitParamsDialog.showDialog("Create point", arr))
                {
                    shapeControl.createPoint(center.get(), center.get());
                }
            }
        });
        mnCreate.add(mntmPoint);

        JMenuItem mntmLine = new JMenuItem("Line");
        mntmLine.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> nodeA = new PairField<Integer>("Node A", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(nodeA);

                PairField<Integer> nodeB = new PairField<Integer>("Node B", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(nodeB);

                if (InitParamsDialog.showDialog("Create line", arr))
                {
                    shapeControl.createLine(nodeA.get(), nodeA.get(), nodeB.get(), nodeB.get());
                }
            }
        });
        mnCreate.add(mntmLine);

        JMenuItem mntmTriangle = new JMenuItem("Triangle");
        mntmTriangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> nodeA = new PairField<Integer>("Node A", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(nodeA);

                PairField<Integer> nodeB = new PairField<Integer>("Node B", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(nodeB);

                PairField<Integer> nodeC = new PairField<Integer>("Node C", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(nodeC);

                if (InitParamsDialog.showDialog("Create triangle", arr))
                {
                    shapeControl.createTriangle(nodeA.get(), nodeA.get(), nodeB.get(), nodeB.get(), nodeC.get(), nodeC.get());
                }
            }
        });
        mnCreate.add(mntmTriangle);

        JMenuItem mntmRectangle = new JMenuItem("Rectangle");
        mntmRectangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(center);

                PairField<Integer> width = new PairField<Integer>("Width", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
                arr.add(width);

                PairField<Integer> height = new PairField<Integer>("Height", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
                arr.add(height);

                PairField<Integer> rotation = new PairField<Integer>("Rotation", new Pair<Integer>(null, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 1));
                arr.add(rotation);

                if (InitParamsDialog.showDialog("Create rectangle", arr))
                {
                    shapeControl.createRectangle(center.get(), center.get(), width.get(), height.get(), rotation.get());
                }
            }
        });
        mnCreate.add(mntmRectangle);

        JMenuItem mntmSquare = new JMenuItem("Square");
        mntmSquare.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(center);

                PairField<Integer> size = new PairField<Integer>("Size", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
                arr.add(size);

                PairField<Integer> rotation = new PairField<Integer>("Rotation", new Pair<Integer>(null, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 1));
                arr.add(rotation);

                if (InitParamsDialog.showDialog("Create square", arr))
                {
                    shapeControl.createSquare(center.get(), center.get(), size.get(), rotation.get());
                }
            }
        });
        mnCreate.add(mntmSquare);

        JMenuItem mntmEllipse = new JMenuItem("Ellipse");
        mntmEllipse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(center);

                PairField<Integer> width = new PairField<Integer>("Width", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
                arr.add(width);

                PairField<Integer> height = new PairField<Integer>("Height", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
                arr.add(height);

                PairField<Integer> rotation = new PairField<Integer>("Rotation", new Pair<Integer>(null, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 1));
                arr.add(rotation);

                if (InitParamsDialog.showDialog("Create ellipse", arr))
                {
                    shapeControl.createEllipse(center.get(), center.get(), width.get(), height.get(), rotation.get());
                }
            }
        });
        mnCreate.add(mntmEllipse);

        JMenuItem mntmCircle = new JMenuItem("Circle");
        mntmCircle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(center);

                PairField<Integer> size = new PairField<Integer>("Size", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
                arr.add(size);

                if (InitParamsDialog.showDialog("Create circle", arr))
                {
                    shapeControl.createCircle(center.get(), center.get(), size.get());
                }
            }
        });
        mnCreate.add(mntmCircle);

        JMenu mnAction = new JMenu("Action");
        menuBar.add(mnAction);

        JMenuItem mntmPrintAll = new JMenuItem("Print All");
        mntmPrintAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                shapeControl.printAll();

            }
        });
        mnAction.add(mntmPrintAll);

        JMenuItem mntmMoveAll = new JMenuItem("Move All");
        mntmMoveAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> center = new PairField<Integer>("Offset", new Pair<Integer>("x"), new Pair<Integer>("y"));
                arr.add(center);

                if (InitParamsDialog.showDialog("Move all", arr))
                {
                    shapeControl.moveAll(center.get(), center.get());
                }
            }
        });
        mnAction.add(mntmMoveAll);

        JMenuItem mntmScaleAll = new JMenuItem("Scale All");
        mntmScaleAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Double> scale = new PairField<Double>("Scale", new Pair<Double>(null, 1d, (double) Integer.MAX_VALUE, 0.001, 0.1));
                arr.add(scale);

                if (InitParamsDialog.showDialog("Scale all", arr))
                {
                    double factor = scale.get();
                    shapeControl.scaleAll(factor, factor);
                }
            }
        });
        mnAction.add(mntmScaleAll);

        JMenuItem mntmRotateAll = new JMenuItem("Rotate All");
        mntmRotateAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

                PairField<Integer> angle = new PairField<Integer>("Angle", new Pair<Integer>());
                arr.add(angle);

                if (InitParamsDialog.showDialog("Rotate all", arr))
                {
                    shapeControl.rotateAll(angle.get());
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
