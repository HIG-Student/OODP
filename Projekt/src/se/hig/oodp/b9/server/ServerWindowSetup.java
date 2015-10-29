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

public class ServerWindowSetup extends JFrame
{
    private JSpinner spinnerPort;
    private JTabbedPane tabbedPane;
    private JPanel panelPort;

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

                JLabel labelLog = new JLabel("[[LOG]]");
                panelLog.add(labelLog);

                btnStart.setEnabled(false);

                try
                {
                    ServerGame server = new ServerGame(new ServerNetworkerSocket(port));

                    server.getNetworker().onLog.add(message -> labelLog.setText(labelLog.getText() + "<br>" + message.replace("\n", "<br>")));
                    server.getNetworker().onPlayerConnecting.add(player ->
                    {
                        panelPlayers.add(new JLabel(player.getName()), BorderLayout.NORTH);

                        if (server.getPlayers().length >= 2)
                            btnStartGame.setEnabled(true);
                    });

                    final ActionListener al = (e) ->
                    {
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
