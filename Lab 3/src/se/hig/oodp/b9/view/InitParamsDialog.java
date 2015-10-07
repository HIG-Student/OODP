/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import se.hig.oodp.Vertex2D;

public class InitParamsDialog extends JDialog
{
    private final JPanel contentPanel = new JPanel();

    public boolean wasOk = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        try
        {
            ArrayList<Pair<?>> arr = new ArrayList<>();

            Pair<Vertex2D> center = new Pair<Vertex2D>("Center", Pair.PairDataTypes.VERTEX2D);
            arr.add(center);

            Pair<Integer> size = new Pair<Integer>("Size", Pair.PairDataTypes.INTEGER, Pair.PairConstraintTypes.POSITIVE);
            arr.add(size);

            InitParamsDialog dialog = new InitParamsDialog("Create circle", arr);

            System.out.println("The size is: " + size.value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private SpinnerNumberModel getSpinnerNumberModel(Pair<?> pair)
    {
        Number value = 0;
        Number min = 0;
        Number max = 0;
        Number step = 0;

        switch (pair.pairDataType)
        {

        case VERTEX2D:
        case INTEGER:
            max = Integer.MAX_VALUE;
            min = Integer.MIN_VALUE;
            step = 1;
            break;

        case DOUBLE:
            max = Double.MAX_VALUE;
            min = Double.MIN_VALUE;
            step = 0.1d;
            break;

        default:
            System.out.println("Wrong type!");
            return null;
        }

        switch (pair.pairConstraintType)
        {

        case POSITIVE_NOT_ZERO:
            min = 0;
            value = 0;
            break;
        case POSITIVE:
            min = 1;
            value = 1;
            break;

        }

        switch (pair.pairDataType)
        {

        case VERTEX2D:
        case INTEGER:
            return new SpinnerNumberModel(value.intValue(),  min.intValue(), max.intValue(), step.intValue());

        case DOUBLE:
            return new SpinnerNumberModel(value.doubleValue(), min.doubleValue(), max.doubleValue(), step.doubleValue());

        default:
            System.out.println("Wrong type!");
            return null;
        }
    };

    private JSpinner createSpinner(String title, JPanel parent, double width, SpinnerNumberModel model)
    {
        JSpinner spinner = new JSpinner(model);

        // http://stackoverflow.com/a/7587253
        JComponent comp = spinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        parent.add(new JLabel(title));
        parent.add(spinner);

        return spinner;
    }

    private JPanel createVertex2DInput(Pair<Vertex2D> pair)
    {
        pair.value = new Vertex2D(0, 0);

        JPanel input = new JPanel();
        {
            input.add(new JLabel(pair.name + ":   "));

            createSpinner("x", input, 100, getSpinnerNumberModel(pair)).addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    pair.value = new Vertex2D((int) ((JSpinner) e.getSource()).getValue(), ((Vertex2D) pair.value).getY());
                }
            });

            createSpinner("y", input, 100, getSpinnerNumberModel(pair)).addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    pair.value = new Vertex2D(((Vertex2D) pair.value).getX(), (int) ((JSpinner) e.getSource()).getValue());
                }
            });
        }
        return input;
    }

    private JPanel createIntegerInput(Pair<Integer> pair)
    {
        pair.value = 0;

        JPanel input = new JPanel();
        {
            createSpinner(pair.name + ":   ", input, 100, getSpinnerNumberModel(pair)).addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    pair.value = (Integer)((JSpinner) e.getSource()).getValue();
                }
            });
        }
        return input;
    }

    private JPanel createDoubleInput(Pair<Double> pair)
    {
        pair.value = 0d;

        JPanel input = new JPanel();
        {
            createSpinner(pair.name + ":   ", input, 100, getSpinnerNumberModel(pair)).addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    pair.value = (Double) ((JSpinner) e.getSource()).getValue();
                }
            });
        }

        return input;
    }

    /**
     * Create the dialog.
     */
    public InitParamsDialog(String title, ArrayList<Pair<?>> inputTypes)
    {
        InitParamsDialog me = this;

        setTitle(title);

        setBounds(100, 100, 500, 75 + inputTypes.size() * 40);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        wasOk = true;
                        me.setVisible(false);
                        me.dispose();
                    }
                });
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }

            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        me.setVisible(false);
                        me.dispose();
                    }
                });
                buttonPane.add(cancelButton);
            }
        }

        for (Pair pair : inputTypes)
        {
            switch (pair.pairDataType)
            {
            case INTEGER:
                contentPanel.add(createIntegerInput(pair));
                break;

            case DOUBLE:
                contentPanel.add(createDoubleInput(pair));
                break;

            case VERTEX2D:
                contentPanel.add(createVertex2DInput(pair));
                break;

            default:
                System.out.println("Error: Wrong type!!");
                break;
            }
        }

        me.setModal(true);
        me.setVisible(true);
    }

    public static boolean showDialog(String title, ArrayList<Pair<?>> input)
    {
        InitParamsDialog dialog = new InitParamsDialog(title, input);
        return dialog.wasOk;
    }
}
