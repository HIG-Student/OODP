package se.hig.oodp.b9.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import se.hig.oodp.b9.Rules;
import se.hig.oodp.b9.TextNode;

/**
 * Window that helps the user start a server game <br>
 * <br>
 * Extends {@link JFrame}
 */
@SuppressWarnings("serial")
public class ServerWindowSetup extends JFrame
{
    /**
     * Port input spinner
     */
    private JSpinner spinnerPort;
    /**
     * Tabbed pane
     */
    private JTabbedPane tabbedPane;
    /**
     * Port panel
     */
    private JPanel panelPort;

    /**
     * String containing the log messages (separated by '\n')
     */
    private String logString = "[[LOG]]";

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
        setBounds(100, 100, 464, 268);

        JButton btnStart = new JButton("Start server");
        btnStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                int port = (Integer) spinnerPort.getValue();

                tabbedPane.removeAll();

                JPanel panelPlayers = new JPanel();
                tabbedPane.addTab("Players", null, panelPlayers, null);
                panelPlayers.setLayout(new BorderLayout(0, 0));

                JButton btnStartGame = new JButton("Start game");
                btnStartGame.setEnabled(false);
                panelPlayers.add(btnStartGame, BorderLayout.SOUTH);

                JPanel panelPlayersList = new JPanel();
                panelPlayers.add(panelPlayersList, BorderLayout.CENTER);

                JPanel panelLog = new JPanel();
                tabbedPane.addTab("Log", null, panelLog, null);

                TextNode labelLog = new TextNode(logString);
                panelLog.add(labelLog);

                btnStart.setEnabled(false);

                try
                {
                    ServerGame server = new ServerGame(new ServerNetworkerSocket(port));

                    server.getNetworker().onLog.add(message -> labelLog.setText((logString += message)));

                    server.getNetworker().onPlayerConnecting.add(player ->
                    {
                        panelPlayers.add(new JLabel(player.getName()), BorderLayout.NORTH);

                        if (server.getPlayers().length >= 2)
                            btnStartGame.setEnabled(true);
                    });

                    final ActionListener al = (e) ->
                    {
                        server.rules = new Rules();
                        server.startGame();
                        btnStartGame.setEnabled(false);
                    };

                    btnStartGame.addActionListener(al);

                    server.getNetworker().onKill.add(bool ->
                    {
                        tabbedPane.removeAll();
                        btnStart.setEnabled(true);
                        addPortSpinner();
                    });
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(ServerWindowSetup.this, "Error:\n\n\t" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getContentPane().add(btnStart, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        addPortSpinner();
    }

    /**
     * Add new port input spinner
     */
    public void addPortSpinner()
    {
        panelPort = new JPanel();
        tabbedPane.addTab("Port", null, panelPort, null);
        panelPort.setLayout(new BorderLayout(0, 0));

        spinnerPort = new JSpinner();
        panelPort.add(spinnerPort, BorderLayout.NORTH);
        spinnerPort.setModel(new SpinnerNumberModel(59440, 50000, 90000, 1));
    }
}
