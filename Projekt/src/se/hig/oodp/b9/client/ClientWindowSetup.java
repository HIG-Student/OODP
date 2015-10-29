package se.hig.oodp.b9.client;

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

public class ClientWindowSetup extends JFrame
{
    private JTextField textName;
    private JTextField textServer;
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
                    ClientWindowSetup frame = new ClientWindowSetup();
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
    public ClientWindowSetup()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 309, 118);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                tabbedPane.setSelectedIndex(3);
                tabbedPane.setEnabled(false);

                try
                {
                    ClientGame client = new ClientGame(new Player(textName.getText()), new ClientNetworkerSocket(textServer.getText(), (Integer) spinnerPort.getValue()));
                    client.getNetworker().onMessage.add(message -> labelLog.setText(labelLog.getText() + "\n" + (message.getSource() != null ? (message.getSource().getName() + ": ") : "") + message.getMessage()));
                    GameWindow.start(client);
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(ClientWindowSetup.this, "Error:\n\n\t" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        textName.setText("Player 1");
        panel.add(textName, BorderLayout.CENTER);
        textName.setColumns(10);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Port", null, panel_1, null);

        spinnerPort = new JSpinner();
        spinnerPort.setModel(new SpinnerNumberModel(59440, 50000, 90000, 1));
        panel_1.add(spinnerPort);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Server IP", null, panel_2, null);

        textServer = new JTextField();
        textServer.setText("127.0.0.1");
        panel_2.add(textServer);
        textServer.setColumns(10);

        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Log", null, panel_3, null);
        tabbedPane.setEnabledAt(3, false);

        labelLog = new JLabel("New label");
        labelLog.setHorizontalAlignment(SwingConstants.CENTER);
        panel_3.add(labelLog);
    }

}
