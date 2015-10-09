package se.hig.oodp.b9.view;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class TextPopUp extends JDialog {
	
	public TextPopUp(String title,String gnuText) {
		
		TextPopUp me= this;
		
		setTitle(title);
		
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		
		Button button = new Button("Close");
		button.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				
			me.setVisible(false);
			me.dispose();
			
			}
		});
		getContentPane().add(button, BorderLayout.SOUTH);
		
		
		
		
		JTextArea textArea = new JTextArea();
		textArea.setText(gnuText);
		textArea.setEditable(false);
		getContentPane().add(textArea, BorderLayout.CENTER);
	
		
		
		me.setModal(true);
		me.setVisible(true);
	}
	
}
