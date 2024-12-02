package main.java.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

import main.java.MESSAGETYPE;
import main.java.Message;

public class UserList {
    String[] userListArray = {"Rowwel", "Kai", "Bryan", "John", "Johnny"};

    JFrame userListFrame = new JFrame("User List");
    JPanel userListPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton addUser = new JButton("Add User to Chat");
    JButton removeUser = new JButton("Remove User from Chat");
    int chatID;
    
    ObjectOutputStream msgOut;

    public UserList(ObjectOutputStream out, int id) {
        // User List Panel
        JList<String> userList = new JList<>(userListArray);
        userListPanel.add(userList);
        userListPanel.setLayout(new GridLayout());
        
        msgOut = out;
        chatID = id;

        // Button Panel
        buttonPanel.add(addUser);
        buttonPanel.add(removeUser);
        buttonPanel.setLayout(new GridLayout());

        // Add button listeners
        addUser.addActionListener(e -> {
        	String username = JOptionPane.showInputDialog(null, "Enter username of user to be added: ", null);
        	if (username == null) { 
				 return;
			 }
        	else if (username.length() == 0) {
        		JOptionPane.showMessageDialog(null, "No username entered. Please try again.", "Warning", JOptionPane.ERROR_MESSAGE);
        	}
        	else {
        		Message msg = new Message(username);
        		msg.setMessageType(MESSAGETYPE.ADD_USER);
        		msg.setID(chatID);
        		try {
					msgOut.writeObject(msg);
					msgOut.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });

        removeUser.addActionListener(e -> {
            JDialog dialog = new JDialog(userListFrame, "Button Pressed", true);
            dialog.setLayout(new FlowLayout());
            dialog.add(new JLabel("You pressed Remove User button!"));
            
            // Add OK button to close dialog
            JButton okButton = new JButton("OK");
            okButton.addActionListener(event -> dialog.dispose());
            dialog.add(okButton);
            
            // Set dialog properties
            dialog.setSize(250, 100);
            dialog.setLocationRelativeTo(userListFrame);
            dialog.setVisible(true);
        });

        // User List Frame
        userListFrame.add(userListPanel);
        userListFrame.add(buttonPanel);
        userListFrame.getContentPane().setLayout(new BoxLayout(userListFrame.getContentPane(), BoxLayout.Y_AXIS));
        
        // Frame settings
        userListFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        userListFrame.setSize(400, 300);
        userListFrame.setLocationRelativeTo(null);
        userListFrame.setVisible(true);
        
        userListFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        userListFrame.dispose(); // Close the current frame
		    }
		});
    }

    public void setVisible(boolean visible) {
        userListFrame.setVisible(visible);
    }
    /*
    public static void openUserList() {
    	SwingUtilities.invokeLater(() -> {
            UserList window = new UserList();
            window.setVisible(true);
        });
    }
    */
}