/**
 * 
 */
package se.hig.oodp.b9.view;

import java.awt.BorderLayout;
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

            Pair<?> center = new Pair<Vertex2D>("Center", Vertex2D.class);
            arr.add(center);

            Pair<?> size = new Pair<Integer>("Size", Integer.class);
            arr.add(size);

            InitParamsDialog dialog = new InitParamsDialog("Create circle", arr);

            System.out.println("The size is: " + size.value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private SpinnerNumberModel GET_SPINNER_POSITIVE_INTEGER()
    {
        return new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
    };

    private SpinnerNumberModel GET_SPINNER_INTEGER()
    {
        return new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
    };

    private SpinnerNumberModel GET_SPINNER_POSITIVE_DOUBLE()
    {
        return new SpinnerNumberModel(1d, 0.001d, 1000d, 0.1d);
    };

    private JSpinner createSpinner(double width, SpinnerNumberModel model)
    {
        JSpinner spinner = new JSpinner(model);

        JComponent field = ((JSpinner.DefaultEditor) spinner.getEditor());
        field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));

        return spinner;
    }

    private JPanel createVertex2DInput(Pair<Vertex2D> pair)
    {
        pair.value = new Vertex2D(0, 0);

        JPanel input = new JPanel();
        {
            input.add(new JLabel(pair.name + ":   "));
            input.add(new JLabel("x"));
            {
                JSpinner xSpinner = createSpinner(100, GET_SPINNER_INTEGER());

                // http://stackoverflow.com/a/7587253
                JComponent comp = xSpinner.getEditor();
                JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
                field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));
                DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
                formatter.setCommitsOnValidEdit(true);
                xSpinner.addChangeListener(new ChangeListener()
                {
                    @Override
                    public void stateChanged(ChangeEvent e)
                    {
                        pair.value = new Vertex2D((int) xSpinner.getValue(), pair.value.getY());
                    }
                });

                input.add(xSpinner);
            }
            input.add(new JLabel("y"));
            {
                JSpinner ySpinner = createSpinner(100, GET_SPINNER_INTEGER());

                // http://stackoverflow.com/a/7587253
                JComponent comp = ySpinner.getEditor();
                JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
                field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));
                DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
                formatter.setCommitsOnValidEdit(true);
                ySpinner.addChangeListener(new ChangeListener()
                {
                    @Override
                    public void stateChanged(ChangeEvent e)
                    {
                        pair.value = new Vertex2D(pair.value.getX(), (int) ySpinner.getValue());
                    }
                });

                input.add(ySpinner);
            }
        }
        return input;
    }

    private JPanel createIntegerInput(Pair<Integer> pair)
    {
        pair.value = 0;

        JPanel input = new JPanel();
        {
            input.add(new JLabel(pair.name + ":   "));
            {
                JSpinner spinner = createSpinner(100, GET_SPINNER_POSITIVE_INTEGER());

                // http://stackoverflow.com/a/7587253
                JComponent comp = spinner.getEditor();
                JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
                field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));
                DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
                formatter.setCommitsOnValidEdit(true);
                spinner.addChangeListener(new ChangeListener()
                {
                    @Override
                    public void stateChanged(ChangeEvent e)
                    {
                        pair.value = (int) spinner.getValue();
                    }
                });

                input.add(spinner);
            }
        }
        return input;
    }

    private JPanel createDoubleInput(Pair<Double> pair)
    {
        pair.value = 0d;

        JPanel input = new JPanel();
        {
            input.add(new JLabel(pair.name + ":   "));
            {
                JSpinner spinner = createSpinner(100, GET_SPINNER_POSITIVE_DOUBLE());

                // http://stackoverflow.com/a/7587253
                JComponent comp = spinner.getEditor();
                JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
                field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));
                DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
                formatter.setCommitsOnValidEdit(true);
                spinner.addChangeListener(new ChangeListener()
                {
                    @Override
                    public void stateChanged(ChangeEvent e)
                    {
                        pair.value = (double) spinner.getValue();
                    }
                });

                input.add(spinner);
            }
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

        setBounds(100, 100, 450, 150);
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

        for (Pair<?> pair : inputTypes)
        {
            if (pair.valueClass == Vertex2D.class)
            {
                contentPanel.add(createVertex2DInput((Pair<Vertex2D>) pair));
            }
            else
                if (pair.valueClass == Double.class)
                {
                    contentPanel.add(createDoubleInput((Pair<Double>) pair));
                }
                else
                    if (pair.valueClass == Integer.class)
                    {
                        contentPanel.add(createIntegerInput((Pair<Integer>) pair));
                    }
                    else
                    {
                        System.out.println("Error: Wrong type!!");
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
