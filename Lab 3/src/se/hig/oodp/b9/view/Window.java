/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.StringJoiner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.hig.oodp.Vertex2D;
import se.hig.oodp.b9.data.Shape;
import se.hig.oodp.b9.model.PrimitivesPainter;
import se.hig.oodp.b9.model.ShapeControl;

@SuppressWarnings("serial")
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

    public void showLoadShapesDialog()
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load shapes");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Shapes list", "shp");
            fileChooser.setFileFilter(filter);
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileHidingEnabled(false);

            if (fileChooser.showOpenDialog(contentPane.getParent()) == JFileChooser.APPROVE_OPTION)
            {
                File fileToLoad = fileChooser.getSelectedFile();
                if (!fileToLoad.exists() && !fileToLoad.getName().endsWith(".shp"))
                {
                    JOptionPane.showMessageDialog(contentPane.getParent(), "You have to load an .shp file!", "Wrong file type!", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    loadShapes(fileToLoad);
                }
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(contentPane.getParent(), "Error while loading file!\n\n" + e.getMessage(), "Error on loading!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadShapes(File fileToLoad) throws Exception
    {
        FileInputStream fileIn = new FileInputStream(fileToLoad);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Shape[] newShapes = (Shape[]) in.readObject();
        in.close();
        fileIn.close();
        shapeControl.loadShapes(newShapes);
    }

    public void showSaveShapesDialog()
    {
        try
        {
            File file = saveFileDialog("Save shapes", "shp");
            saveShapes(file);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(contentPane.getParent(), "Error on saving!\n\n" + e.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void saveShapes(File file) throws Exception
    {
        // http://www.tutorialspoint.com/java/java_serialization.htm

        if (file == null)
            throw new Exception("No file chosen");
        file.createNewFile();
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(shapeControl.getShapes());
        out.close();
        fileOut.close();
    }

    /**
     * Create the frame.
     */
    public Window()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 800);
        setLocationRelativeTo(null);

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
                    AffineTransform transform = this.g.getTransform();
                    s.draw(this);
                    this.g.setTransform(transform);
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
                g.rotate(Math.toRadians(rotation), center.getX(), center.getY());
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
        mntmLoad.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                showLoadShapesDialog();
            }
        });
        mnFile.add(mntmLoad);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                showSaveShapesDialog();
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
                    File file = saveFileDialog("Export to image", "png");
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
            	StringJoiner joiner = new StringJoiner("\n");
            	for(Shape s : shapeControl.getShapes())
            		joiner.add(s.toString());
            	new TextPopUp("Printout",joiner.toString());
            
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

        if (false)
        {
            shapeControl.createLine(0, 0, 200, 200);
            shapeControl.createLine(200, 0, 0, 200);
            shapeControl.createEllipse(100, 100, 100, 20);

            shapeControl.rotateAll(45);

            try
            {
                saveShapes(new File("H:/test.shp"));
            }
            catch (Exception e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        else
        {
            try
            {
                loadShapes(new File("H:/test.shp"));
            }
            catch (Exception e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private File saveFileDialog(String title, String fileEnding) throws Exception
    {
        // http://www.codejava.net/java-se/swing/show-save-file-dialog-using-jfilechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileEnding, fileEnding);
        fileChooser.setFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter);

        if (fileChooser.showSaveDialog(contentPane.getParent()) == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave.exists() && !fileToSave.getName().endsWith("." + fileEnding))
            {
                throw new Exception("You have to save as ." + fileEnding + " !");
            }
            else
            {
                String fileName = fileToSave.getAbsolutePath();
                if (!fileName.endsWith("." + fileEnding))
                    fileName = fileName + "." + fileEnding;

                fileToSave = new File(fileName);
                fileToSave.createNewFile();
                return fileToSave;
            }
        }
        else
            return null;
    }
}
