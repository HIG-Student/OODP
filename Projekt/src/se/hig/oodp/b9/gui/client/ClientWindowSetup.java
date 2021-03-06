package se.hig.oodp.b9.gui.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import se.hig.oodp.b9.gui.TextNode;
import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.client.ClientGame;
import se.hig.oodp.b9.logic.client.ClientNetworkerSocket;
import se.hig.oodp.b9.model.Player;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Window that helps the user start a client game <br>
 * <br>
 * Extends {@link JFrame}
 */
@SuppressWarnings("serial")
public class ClientWindowSetup extends JFrame
{
    /**
     * The name input field
     */
    private JTextField textName;
    /**
     * The host input field
     */
    private JTextField textServer;
    /**
     * The port input spinner
     */
    private JSpinner spinnerPort;
    /**
     * The tabbed panel
     */
    private JTabbedPane tabbedPane;

    /**
     * On window closed
     */
    public final Event<Boolean> onClose = new Event<Boolean>();

    /**
     * String containing the log messages (separated by '\n')
     */
    private String logString = "[[LOG]]\n";

    /**
     * Launch the application.
     * 
     * @param args
     *            commandline arguments
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
     * Create the window
     */
    public ClientWindowSetup()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 309, 118);
        setLocationRelativeTo(null);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                try
                {
                    ClientGame client = new ClientGame(new Player(textName.getText()), new ClientNetworkerSocket(textServer.getText(), (Integer) spinnerPort.getValue()));
                    onClose.add(ok -> client.end("Window closed"));

                    tabbedPane.removeAll();

                    JPanel logPanel = new JPanel();
                    logPanel.setLayout(new BorderLayout());
                    tabbedPane.addTab("Log", null, logPanel, null);
                    tabbedPane.setEnabledAt(0, false);

                    TextNode labelLog = new TextNode(logString);
                    JScrollPane sp = new JScrollPane(labelLog);
                    logPanel.add(sp, BorderLayout.CENTER);

                    Dimension size = new Dimension(800, 600);
                    ClientWindowSetup.this.setSize(size);
                    ClientWindowSetup.this.setPreferredSize(size);
                    ClientWindowSetup.this.setLocationRelativeTo(null);

                    client.getNetworker().onMessage.add(message -> labelLog.setText((logString += ((message.getSource() != null ? (message.getSource().getName() + ": ") : "") + message.getMessage() + "\n"))));
                    GameWindow.start(client);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
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
        panel_1.setLayout(new BorderLayout(0, 0));
        panel_1.add(spinnerPort, BorderLayout.CENTER);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Server IP", null, panel_2, null);

        textServer = new JTextField();
        textServer.setText("2001:6b0:23:91:5c07:e103:e0b:8b59");
        textServer.setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));
        panel_2.add(textServer, BorderLayout.CENTER);
        textServer.setColumns(10);
    }

}
