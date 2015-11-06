package se.hig.oodp.b9.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

import se.hig.oodp.FigureHandler;
import se.hig.oodp.FigureMover;
import se.hig.oodp.FigurePainter;
import se.hig.oodp.FigurePrinter;
import se.hig.oodp.FigureRotor;
import se.hig.oodp.FigureScalor;
import se.hig.oodp.PrimitivesPainter;
import se.hig.oodp.b9.logic.FigureHandlerImpl;
import se.hig.oodp.b9.logic.FigureMoverImpl;
import se.hig.oodp.b9.logic.FigurePainterImpl;
import se.hig.oodp.b9.logic.FigurePrinterImpl;
import se.hig.oodp.b9.logic.FigureRotorImpl;
import se.hig.oodp.b9.logic.FigureScalorImpl;
import se.hig.oodp.b9.logic.Graphix;
import se.hig.oodp.b9.logic.PrimitivesPainterImpl;
import se.hig.oodp.b9.logic.ShapeLists;

@SuppressWarnings("serial")
public class Window extends JFrame
{
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

        final ShapeLists shapeLists = new ShapeLists();

        final Graphix graphix = new Graphix();
        final PrimitivesPainter primitivesPainter = new PrimitivesPainterImpl(graphix);
        final FigurePainter figurePainter = new FigurePainterImpl(shapeLists, primitivesPainter);

        final Canvas contentPane = new Canvas(graphix, figurePainter);

        final FigureHandler figureHandler = new FigureHandlerImpl(shapeLists);
        final FigurePrinter figurePrinter = new FigurePrinterImpl(shapeLists);
        final FigureMover figureMover = new FigureMoverImpl(shapeLists);
        final FigureRotor figureRotor = new FigureRotorImpl(shapeLists);
        final FigureScalor figureScalor = new FigureScalorImpl(shapeLists);

        shapeLists.onChange.add(shape -> contentPane.repaint());

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmLoad = new JMenuItem("Load");
        mntmLoad.addActionListener((e) -> ShapeIO.showLoadShapesDialog(me, shapeLists));
        mnFile.add(mntmLoad);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener((e) -> ShapeIO.showSaveShapesDialog(me, shapeLists));
        mnFile.add(mntmSave);

        JMenuItem mntmExport = new JMenuItem("Export");
        mntmExport.addActionListener((e) -> ShapeIO.saveShapesScreenShoot(me, contentPane.screenShoot()));
        mnFile.add(mntmExport);

        JMenu mnCreate = new JMenu("Create");
        menuBar.add(mnCreate);

        JMenuItem mntmPoint = new JMenuItem("Point");
        mntmPoint.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            if (InitParamsDialog.showDialog("Create point", arr))
            {
                figureHandler.createPoint(center.get(), center.get());
            }
        });
        mnCreate.add(mntmPoint);

        JMenuItem mntmLine = new JMenuItem("Line");
        mntmLine.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> nodeA = new PairField<Integer>("Node A", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(nodeA);

            PairField<Integer> nodeB = new PairField<Integer>("Node B", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(nodeB);

            if (InitParamsDialog.showDialog("Create line", arr))
            {
                figureHandler.createLine(nodeA.get(), nodeA.get(), nodeB.get(), nodeB.get());
            }
        });
        mnCreate.add(mntmLine);

        JMenuItem mntmTriangle = new JMenuItem("Triangle");
        mntmTriangle.addActionListener((e) ->
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
                figureHandler.createTriangle(nodeA.get(), nodeA.get(), nodeB.get(), nodeB.get(), nodeC.get(), nodeC.get());
            }
        });
        mnCreate.add(mntmTriangle);

        JMenuItem mntmRectangle = new JMenuItem("Rectangle");
        mntmRectangle.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            PairField<Integer> width = new PairField<Integer>("Width", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(width);

            PairField<Integer> height = new PairField<Integer>("Height", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(height);

            if (InitParamsDialog.showDialog("Create rectangle", arr))
            {
                figureHandler.createRectangle((double) center.get(), (double) center.get(), (double) width.get(), (double) height.get());
            }
        });
        mnCreate.add(mntmRectangle);

        JMenuItem mntmSquare = new JMenuItem("Square");
        mntmSquare.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            PairField<Integer> size = new PairField<Integer>("Size", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(size);

            if (InitParamsDialog.showDialog("Create square", arr))
            {
                figureHandler.createSquare(center.get(), center.get(), size.get());
            }
        });
        mnCreate.add(mntmSquare);

        JMenuItem mntmEllipse = new JMenuItem("Ellipse");
        mntmEllipse.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            PairField<Integer> width = new PairField<Integer>("Width", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(width);

            PairField<Integer> height = new PairField<Integer>("Height", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(height);

            if (InitParamsDialog.showDialog("Create ellipse", arr))
            {
                figureHandler.createEllipse(center.get(), center.get(), width.get(), height.get());
            }
        });
        mnCreate.add(mntmEllipse);

        JMenuItem mntmCircle = new JMenuItem("Circle");
        mntmCircle.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            PairField<Integer> size = new PairField<Integer>("Size", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(size);

            if (InitParamsDialog.showDialog("Create circle", arr))
            {
                figureHandler.createCircle(center.get(), center.get(), size.get());
            }
        });
        mnCreate.add(mntmCircle);

        JMenu mnAction = new JMenu("Action");
        menuBar.add(mnAction);

        JMenuItem mntmPrintAll = new JMenuItem("Print All");
        mntmPrintAll.addActionListener((e) -> figurePrinter.printAll());
        mnAction.add(mntmPrintAll);

        JMenuItem mntmMoveAll = new JMenuItem("Move All");
        mntmMoveAll.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Offset", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            if (InitParamsDialog.showDialog("Move all", arr))
            {
                figureMover.moveAll(center.get(), center.get());
            }
        });
        mnAction.add(mntmMoveAll);

        JMenuItem mntmScaleAll = new JMenuItem("Scale All");
        mntmScaleAll.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Double> scale = new PairField<Double>("Scale", new Pair<Double>(null, 1d, (double) Integer.MAX_VALUE, 0.001, 0.1));
            arr.add(scale);

            if (InitParamsDialog.showDialog("Scale all", arr))
            {
                double factor = scale.get();
                figureScalor.scaleAll(factor, factor);
            }
        });
        mnAction.add(mntmScaleAll);

        JMenuItem mntmRotateAll = new JMenuItem("Rotate All");
        mntmRotateAll.addActionListener((e) ->
        {
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> angle = new PairField<Integer>("Angle", new Pair<Integer>());
            arr.add(angle);

            if (InitParamsDialog.showDialog("Rotate all", arr))
            {
                figureRotor.rotateAll(angle.get());
            }
        });
        mnAction.add(mntmRotateAll);

        JMenuItem mntmRemoveAll = new JMenuItem("Remove All");
        mntmRemoveAll.addActionListener((e) -> shapeLists.clear());
        mnAction.add(mntmRemoveAll);

        setContentPane(contentPane);
    }
}
