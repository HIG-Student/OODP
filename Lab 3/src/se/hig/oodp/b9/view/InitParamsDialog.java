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

@SuppressWarnings("serial")
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
            ArrayList<PairField<? extends Number>> arr = new ArrayList<>();

            PairField<Integer> center = new PairField<Integer>("Center", new Pair<Integer>("x"), new Pair<Integer>("y"));
            arr.add(center);

            PairField<Integer> size = new PairField<Integer>("Size", new Pair<Integer>(null, 1, Integer.MAX_VALUE, 1, 1));
            arr.add(size);

            PairField<Integer> rotation = new PairField<Integer>("Rotation", new Pair<Integer>());
            arr.add(rotation);

            PairField<Double> dbl = new PairField<Double>("Scale", new Pair<Double>(null, 1d, (double) Integer.MAX_VALUE, 0.001, 0.1));
            arr.add(dbl);

            @SuppressWarnings("unused")
            InitParamsDialog dialog = new InitParamsDialog("Create mojs", arr);

            System.out.println("The size is: " + center.pairs[0].value);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private JSpinner createSpinner(String title, JPanel parent, double width, SpinnerNumberModel model)
    {
        JSpinner spinner = new JSpinner(model);

        // http://stackoverflow.com/a/7587253
        JComponent comp = spinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        if (title != null && title != "")
            parent.add(new JLabel(title + ": "));
        parent.add(spinner);

        return spinner;
    }

    @SuppressWarnings("unchecked")
    private <T extends Number> JPanel createNumberInput(PairField<T> pairs)
    {
        JPanel input = new JPanel();
        {
            input.add(new JLabel(pairs.name + ":   "));

            for (Pair<T> pair : pairs.pairs)
            {
                JSpinner spinner = createSpinner(pair.name, input, 100, new SpinnerNumberModel((Number) pair.start, (Comparable<T>) pair.min, (Comparable<T>) pair.max, (Number) pair.step));
                spinner.addChangeListener(new ChangeListener()
                {
                    @Override
                    public void stateChanged(ChangeEvent e)
                    {
                        pair.value = (T) ((JSpinner) e.getSource()).getValue();
                    }
                });
                pair.value = (T) spinner.getValue();
            }
        }

        return input;
    }

    /**
     * Create the dialog.
     */
    public InitParamsDialog(String title, ArrayList<PairField<? extends Number>> inputTypes)
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

        for (PairField<? extends Number> pairField : inputTypes)
        {
            contentPanel.add(createNumberInput(pairField));
        }

        me.pack();

        me.setModal(true);
        me.setVisible(true);
    }

    public static boolean showDialog(String title, ArrayList<PairField<? extends Number>> input)
    {
        InitParamsDialog dialog = new InitParamsDialog(title, input);
        return dialog.wasOk;
    }
}
