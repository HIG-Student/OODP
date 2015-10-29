package se.hig.oodp.b9.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFormattedTextField;

import se.hig.oodp.b9.Player;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerWindowSetup extends JFrame
{
    private JTextField textName;
    private JSpinner spinnerPort;
    private JLabel labelLog;
    private JTabbedPane tabbedPane;

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
                    ServerWindowSetup frame = new ServerWindowSetup();
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
    public ServerWindowSetup()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 309, 118);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                tabbedPane.setSelectedIndex(2);
                tabbedPane.setEnabled(false);

                try
                {
                    ServerGame server = new ServerGame(new ServerNetworkerSocket((Integer) spinnerPort.getValue()));
                    server.getNetworker().onLog.add(message -> labelLog.setText(labelLog.getText() + "\n" + message));
                    // open window
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(ServerWindowSetup.this, "Error:\n\n\t" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                tabbedPane.setSelectedIndex(0);
                tabbedPane.setEnabled(true);
            }
        });
        getContentPane().add(btnStart, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Name", null, panel, null);
        panel.setLayout(new BorderLayout(0, 0));

        textName = new JTextField();
        textName.setBackground(Color.WHITE);
        textName.setHorizontalAlignment(SwingConstants.CENTER);
        textName.setText("u");
        panel.add(textName, BorderLayout.CENTER);
        textName.setColumns(10);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Port", null, panel_1, null);

        spinnerPort = new JSpinner();
        spinnerPort.setModel(new SpinnerNumberModel(59440, 50000, 90000, 1));
        panel_1.add(spinnerPort);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Log", null, panel_2, null);
        tabbedPane.setEnabledAt(2, false);

        labelLog = new JLabel("New label");
        panel_2.add(labelLog);
    }

}
