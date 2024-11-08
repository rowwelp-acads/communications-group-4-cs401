package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminPanel {
	
	JFrame adminFrame = new JFrame("Admin Frame");
	
	JPanel buttonContainer = new JPanel();
	
	JButton createAccountButton = new JButton("Create Account");
	JButton deleteAccountButton = new JButton("Delete Account");
	JButton viewLogButton = new JButton("View Conversation Log");
	
	public AdminPanel() {
				
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
		
		//centering stuff
		buttonContainer.add(Box.createVerticalGlue());
		
		//actual buttons added
		buttonContainer.add(createAccountButton);
		buttonContainer.add(deleteAccountButton);
		buttonContainer.add(viewLogButton);
		
		//center things
		createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		deleteAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewLogButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonContainer.add(Box.createVerticalGlue());
		
		adminFrame.setLayout(new BoxLayout(adminFrame.getContentPane(), BoxLayout.Y_AXIS));
		adminFrame.add(buttonContainer, BorderLayout.CENTER);
		
		EventListener event = new EventListener();
		createAccountButton.addActionListener(event);
		deleteAccountButton.addActionListener(event);
		viewLogButton.addActionListener(event);
		
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.pack();
		adminFrame.setAlwaysOnTop(true);
		adminFrame.setVisible(true);
		adminFrame.setLocationRelativeTo(null);
	}
	
	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Create Account")){
				//create account method
				JOptionPane.showMessageDialog(adminFrame, "Create account button hit", "Create account method", JOptionPane.INFORMATION_MESSAGE);
	        }
			
			else if (event.getActionCommand().equals("Delete Account")) {
				//delete account method
				JOptionPane.showMessageDialog(adminFrame, "delete account button hit", "delete account method", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (event.getActionCommand().equals("View Conversation Log")) {
				//view log method
				JOptionPane.showMessageDialog(adminFrame, "view conversation button hit", "view conversation log method", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void openAdminPanel() {
		new AdminPanel();
	}
	
}
