package se.hig.oodp.b9.gui.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import se.hig.oodp.b9.gui.TextNode;
import se.hig.oodp.b9.logic.Event;
import se.hig.oodp.b9.logic.Rules;
import se.hig.oodp.b9.logic.client.AI;
import se.hig.oodp.b9.logic.client.AIStrategyPickSingleMore;
import se.hig.oodp.b9.logic.client.AIStrategyPickSingle;
import se.hig.oodp.b9.logic.client.AIStrategyThrowAll;
import se.hig.oodp.b9.logic.client.ClientGame;
import se.hig.oodp.b9.logic.client.ClientNetworkerSocket;
import se.hig.oodp.b9.logic.server.ServerGame;
import se.hig.oodp.b9.logic.server.ServerNetworkerSocket;
import se.hig.oodp.b9.model.Player;

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
     * Number of AIs
     */
    protected int AINum = 1;

    /**
     * On window closed
     */
    public final Event<Boolean> onClose = new Event<Boolean>();

    /**
     * Create the frame.
     */
    public ServerWindowSetup()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 464, 268);

        // http://stackoverflow.com/a/19739532
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onClose.invoke(true);
            }
        });

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
                panelPlayersList.setLayout(new BoxLayout(panelPlayersList, BoxLayout.Y_AXIS));
                panelPlayers.add(panelPlayersList, BorderLayout.CENTER);

                JPanel panelLog = new JPanel();
                tabbedPane.addTab("Log", null, panelLog, null);

                TextNode labelLog = new TextNode(logString);
                panelLog.setLayout(new BorderLayout());
                // http://stackoverflow.com/a/1053036
                JScrollPane sp = new JScrollPane(labelLog);
                panelLog.add(sp, BorderLayout.CENTER);

                btnStart.setEnabled(false);

                JPanel panelAI = new JPanel();
                tabbedPane.addTab("AI", null, panelAI, null);
                panelAI.setLayout(new BorderLayout(0, 0));

                JButton btnAddAI1 = new JButton("AI - throwing");
                panelAI.add(btnAddAI1, BorderLayout.NORTH);
                btnAddAI1.addActionListener(action ->
                {
                    try
                    {
                        AI ai = new AI(new ClientGame(new Player("AI " + AINum++), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting(), new AIStrategyThrowAll());
                        onClose.add(ok -> ai.getGame().end("Window closed"));
                    }
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(ServerWindowSetup.this, "Error:\n\n\t" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                JButton btnAddAI2 = new JButton("AI - take single");
                panelAI.add(btnAddAI2, BorderLayout.WEST);
                btnAddAI2.addActionListener(action ->
                {
                    try
                    {
                        AI ai = new AI(new ClientGame(new Player("AI " + AINum++), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting(), new AIStrategyPickSingle());
                        onClose.add(ok -> ai.getGame().end("Window closed"));
                    }
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(ServerWindowSetup.this, "Error:\n\n\t" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                JButton btnAddAI3 = new JButton("AI - take multiple single");
                panelAI.add(btnAddAI3, BorderLayout.SOUTH);
                btnAddAI3.addActionListener(action ->
                {
                    try
                    {
                        AI ai = new AI(new ClientGame(new Player("AI " + AINum++), new ClientNetworkerSocket("127.0.0.1", port)).sendGreeting(), new AIStrategyPickSingleMore());
                        onClose.add(ok -> ai.getGame().end("Window closed"));
                    }
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(ServerWindowSetup.this, "Error:\n\n\t" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                try
                {
                    ServerGame server = new ServerGame(new ServerNetworkerSocket(port), new Rules());

                    onClose.add(ok -> server.kill());

                    server.getNetworker().onLog.add(message -> labelLog.setText((logString += (message + "\n"))));

                    server.playerAdded.add(player ->
                    {
                        panelPlayersList.add(new JLabel(player.getName()), BorderLayout.NORTH);

                        if (server.getPlayers().length >= 2)
                            btnStartGame.setEnabled(true);

                        if (server.getPlayers().length >= 4)
                        {
                            btnAddAI1.setEnabled(false);
                            btnAddAI2.setEnabled(false);
                            btnAddAI3.setEnabled(false);
                        }
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

                    tabbedPane.removeAll();
                    btnStart.setEnabled(true);
                    addPortSpinner();
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
