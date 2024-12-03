package main.java.gui;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import main.java.Chat;
import main.java.ConversationHistory;
import main.java.MESSAGETYPE;
import main.java.Message;
import main.java.UserAccount;
import java.util.List;

public class ChatRoom {
	JFrame chatroomFrame = new JFrame("Chatroom");
	
	// hold information of this chat room ? is this needed?
	// Chat thisChatInfo;
	
	// A
	JPanel chatPanel = new JPanel();
	// B
	JPanel optionsPanel = new JPanel();
	// b
	JPanel messageSendPanel = new JPanel();
	
	JTextField messageField = new JTextField();
	
	JButton userListButton = new JButton("Manage Chat...");
	JButton sendMessageButton = new JButton("Send");
	int chatID;
	ObjectInputStream msgIn;
	ObjectOutputStream msgOut;
	String[] convoHistoryArray;
	UserAccount user;
	ConversationHistory convoHistory;
	MainHub mainHub;
	JList<String> messageContainer;
	
	public ChatRoom(ObjectInputStream in, ObjectOutputStream out, UserAccount user, String room, MainHub hub) {
		this.user = user;
		msgIn = in;
		msgOut = out;
		mainHub = hub;
		String stringID = room.substring(room.length()-1);
		try {
			chatID = Integer.parseInt(stringID);
			List<String> messageList = this.user.getChatList().getChat(chatID).getConversationHistory().getMessageList();
			convoHistoryArray = messageList.toArray(new String[0]);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		

		
		//thisChatInfo = chatInfo;
		// convoHistory = user.getConversationHistory();
		// messageList = convoHistory.toString;
		
		messageContainer = new JList<String>(convoHistoryArray);
		// a
		JScrollPane messageListScrollPane = new JScrollPane(messageContainer);
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
		chatroomFrame.setPreferredSize(new Dimension(500,400));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		messageSendPanel.setLayout(new BorderLayout());
		
		chatPanel.add(messageListScrollPane);
		chatPanel.add(messageSendPanel);
		
		sendMessageButton.setPreferredSize(new Dimension(80, 30));
		messageSendPanel.add(messageField, BorderLayout.CENTER);
		messageSendPanel.add(sendMessageButton, BorderLayout.EAST);
		
		
		optionsPanel.add(userListButton);
		
		// send message to server
		// get the text field into a message object then send. Should not close msgOut.
		sendMessageButton.addActionListener(e -> {
			if (messageField.getText() != null) {
				Message msg = new Message(messageField.getText(), user, chatID);
				messageField.setText("");
				try {
					msgOut.writeObject(msg);
					msgOut.flush();
				} catch (IOException e1) {
					System.out.println("ChatRoom send message error starting at line 82");
					e1.printStackTrace();
				}
			}
		});
		
		userListButton.addActionListener(e -> {
			/*
			JDialog dialog = new JDialog(chatroomFrame, "UserListButton clicked", true);
			dialog.setLayout(new FlowLayout());
			dialog.add(new JLabel("You pressed the UserListButton"));
			
			JButton ok = new JButton("Ok");
			
			ok.addActionListener(event -> dialog.dispose());
			dialog.add(ok);
			
			dialog.setSize(250, 100);
			dialog.setLocationRelativeTo(chatroomFrame);
			dialog.setVisible(true);
			*/
			
			UserList thisChatUserList = new UserList(msgIn, msgOut, chatID, this.user);
			//thisChatUserList.openUserList();
		});
		
		chatroomFrame.add(chatPanel);
		chatroomFrame.add(optionsPanel);
		
		chatroomFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		chatroomFrame.pack();
		chatroomFrame.setLocationRelativeTo(null);
		chatroomFrame.setVisible(true);
		
		chatroomFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        mainHub.getFrame().setVisible(true); // Show the previous frame
		        mainHub.resetCurrentChatRoom();
		        chatroomFrame.dispose(); // Close the current frame
		    }
		});

		
		
	}
	
	public int getChatID() {
		return chatID;
	}
	
	// update the chatRoom GUI in the background every (1 second?) for new incoming messages. 
	// Johnny: I'm assuming we are using ConversationHistory to show previous messages in the chat room
	public void updateChatRoom() {
		List<String> messageList = this.user.getChatList().getChat(chatID).getConversationHistory().getMessageList();
		convoHistoryArray = messageList.toArray(new String[0]);
		// TODO: add functionality
		// grab the convoHistory belonging to this chat room
	    SwingUtilities.invokeLater(() -> {
	        messageContainer.setListData(convoHistoryArray);
	    });
	}
	/*
	public void openChatroom() {
		new ChatRoom();
	}
	
	
	public static void main(String args[]) {
		ChatRoom chatroom = new ChatRoom();
		//chatroom.openChatroom();
	}
	*/
}
