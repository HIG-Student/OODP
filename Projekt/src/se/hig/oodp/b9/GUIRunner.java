package se.hig.oodp.b9;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import se.hig.oodp.b9.gui.client.ClientWindowSetup;
import se.hig.oodp.b9.gui.server.ServerWindowSetup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Class for running either server or client
 */
@SuppressWarnings("serial")
public class GUIRunner extends JFrame
{

    /**
     * Pane with contents
     */
    private JPanel contentPane;

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
                    GUIRunner frame = new GUIRunner();
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
    public GUIRunner()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JButton btnClient = new JButton("Client");
        btnClient.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ClientWindowSetup.main(null);
            }
        });
        contentPane.add(btnClient, BorderLayout.WEST);

        JButton btnServer = new JButton("Server");
        btnServer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ServerWindowSetup.main(null);
            }
        });
        contentPane.add(btnServer, BorderLayout.EAST);
    }

}
