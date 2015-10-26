package se.hig.oodp.b9.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ServerWindow extends JFrame
{
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
                    ServerWindow frame = new ServerWindow();
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
    public ServerWindow()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        ServerNetworkerSocket sns = null;

        while (sns == null)
        {
            try
            {
                sns = new ServerNetworkerSocket(Integer.parseInt(JOptionPane.showInputDialog("Pick the server port", 55342)));
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(this, "Error when listening to port\n\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Error when listening to port\n\nPort must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (sns == null)
                if (JOptionPane.showConfirmDialog(this, "Do you want to try another port?", "Retry?", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                {
                    System.out.println("Aborted by user");
                    System.exit(1);
                }
        }

        ServerGame game = new ServerGame(sns);

        // TODO: Add new components to show server status
    }
}