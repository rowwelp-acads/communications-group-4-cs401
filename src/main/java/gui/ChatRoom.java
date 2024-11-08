package main.java.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ChatRoom {
	JFrame chatroomFrame = new JFrame("Chatroom");
	
	// A
	JPanel chatPanel = new JPanel();
	// B
	JPanel optionsPanel = new JPanel();
	// b
	JPanel messageSendPanel = new JPanel();
	
	JTextField messageField = new JTextField();
	
	JButton userListButton = new JButton("Manage Chat...");
	JButton sendMessageButton = new JButton("Send");
	
	String messagesExample[] = {"User 1: Hello!",
			"User 2: Hello!",
			"User 1: How are you?",
			"User 2: I'm good. How about you?",
			"User 1: I'm doing quite well thanks",
			"User 1: What are you working on?",
			"User 2: I'm currently implementing the chatroom GUI.",
			"User 1: Yikes!"
	};
	
	public ChatRoom() {
		JList<String> messageList = new JList<>(messagesExample);
		// a
		JScrollPane messageListScrollPane = new JScrollPane(messageList);
		messageListScrollPane.setPreferredSize(new Dimension(400,300));
		
		// Set to scroll to the bottom of the messageList
		JScrollBar messageListScrollBar = messageListScrollPane.getVerticalScrollBar();
		messageListScrollBar.setValue(messageListScrollBar.getMaximum());
		
		
		SwingUtilities.invokeLater(() -> {
			JScrollBar verticalBar = messageListScrollPane.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
		});

		
		// Set Layouts
		chatroomFrame.setLayout(new BoxLayout(chatroomFrame.getContentPane(), BoxLayout.Y_AXIS));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		messageSendPanel.setLayout(new BorderLayout());
		
		chatPanel.add(messageListScrollPane);
		chatPanel.add(messageSendPanel);
		
		sendMessageButton.setPreferredSize(new Dimension(80, 30));
		messageSendPanel.add(messageField, BorderLayout.CENTER);
		messageSendPanel.add(sendMessageButton, BorderLayout.EAST);
		
		
		optionsPanel.add(userListButton);
		
		userListButton.addActionListener(e -> {
			JDialog dialog = new JDialog(chatroomFrame, "UserListButton clicked", true);
			dialog.setLayout(new FlowLayout());
			dialog.add(new JLabel("You pressed the UserListButton"));
			
			JButton ok = new JButton("Ok");
			
			ok.addActionListener(event -> dialog.dispose());
			dialog.add(ok);
			
			dialog.setSize(250, 100);
			dialog.setLocationRelativeTo(chatroomFrame);
			dialog.setVisible(true);
			
		});
		
		chatroomFrame.add(chatPanel);
		chatroomFrame.add(optionsPanel);
		
		chatroomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatroomFrame.pack();
		chatroomFrame.setLocationRelativeTo(null);
		chatroomFrame.setVisible(true);
		
	}
	
	public static void main(String args[]) {
		new ChatRoom();
	}
}
