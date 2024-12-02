package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

import main.java.MESSAGETYPE;
import main.java.Message;

public class AdminPanel {
	
	JFrame adminFrame = new JFrame("Admin Frame");
	
	JPanel buttonContainer = new JPanel();
	
	JButton createAccountButton = new JButton("Create Account");
	JButton deleteAccountButton = new JButton("Delete Account");
	JButton viewLogButton = new JButton("View Conversation Log");
	ObjectOutputStream msgOut;
	
	public AdminPanel(ObjectOutputStream out) {
		
		msgOut = out;
				
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
		
		adminFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		adminFrame.pack();
		adminFrame.setAlwaysOnTop(true);
		adminFrame.setVisible(true);
		adminFrame.setLocationRelativeTo(null);
		
		adminFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	adminFrame.dispose(); // Close the current frame
		    }
		});
	}
	
	private void addAccount() {
		// create account method
		Message msg = new Message();
		msg.setMessageType(MESSAGETYPE.ADD_ACCOUNT);
		String username = JOptionPane.showInputDialog(adminFrame, "Enter username of user to be added: ", null);
		if (username == null) {
			return;
		} else if (username.length() == 0) {
			JOptionPane.showMessageDialog(adminFrame, "No username entered. Please try again.", "Warning",
					JOptionPane.ERROR_MESSAGE);
			addAccount();
		} else {
			msg.setUsername(username);
			String pass = JOptionPane.showInputDialog(adminFrame, "Enter username of user to be added: ", null);
			if (pass == null) {
				return;
			} 
			else if (pass.length() == 0) {
				JOptionPane.showMessageDialog(adminFrame, "No password entered. Please try again.", "Warning",
						JOptionPane.ERROR_MESSAGE);
				addAccount();
			}
			else {
				msg.setPassword(pass);
			}
		}
		
		try {
			msgOut.writeObject(msg);
			msgOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void close() {
		adminFrame.dispose();
	}
	
	private void removeAccount() {
		Message msg = new Message();
		msg.setMessageType(MESSAGETYPE.REMOVE_ACCOUNT);
		String username = JOptionPane.showInputDialog(adminFrame, "Enter username of user to be removed: ", null);
		if (username == null) {
			return;
		} else if (username.length() == 0) {
			JOptionPane.showMessageDialog(adminFrame, "No username entered. Please try again.", "Warning",
					JOptionPane.ERROR_MESSAGE);
			removeAccount();
		} else {
			msg.setUsername(username);
		}
		
		try {
			msgOut.writeObject(msg);
			msgOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Create Account")){
				addAccount();
	        }
			
			else if (event.getActionCommand().equals("Delete Account")) {
				//delete account method
				removeAccount();
			}
			
			else if (event.getActionCommand().equals("View Conversation Log")) {
				//view log method
				// TODO:
				Message msg = new Message();
				msg.setMessageType(MESSAGETYPE.GET_LOG);
				try {
					msgOut.writeObject(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*
	public void openAdminPanel() {
		new AdminPanel();
	}
	*/
}
