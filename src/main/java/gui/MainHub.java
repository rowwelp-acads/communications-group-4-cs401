package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.ChatList;
import main.java.Client;
import main.java.ConversationHistory;
import main.java.ITUser;
import main.java.RegularUser;
import main.java.MESSAGETYPE;
import main.java.Message;
import main.java.UserAccount;


/*
 * TODO: create buttons and JList
 * add buttons and JList to panels.
 * add panels to JFrame
 * add functionalities
 * 
 * grey out the IT button
 * change boxlayout gridlayout
 *  
 */
public class MainHub extends JFrame{
	
	// GUI PROPERTIES
	JFrame mainFrame = new JFrame("Main Hub");
	JButton itButton = new JButton("IT Button");
	JButton createPrivateChatButton = new JButton("Create Private Group Chat");
	JButton createGroupChatButton = new JButton("Create Group Chat");
	JButton deleteChatButton = new JButton("Delete Chat");
	JButton openChatButton = new JButton("Open Chat");
	JButton logoutButton = new JButton("Logout");
	JPanel buttonContainer = new JPanel();
	JPanel chatsContainer = new JPanel();
	boolean closing = false;
	
	// STREAMS
	Socket socket;
	ObjectInputStream msgIn;
	ObjectOutputStream msgOut;
	String selectedChatRoom = "";
	ChatRoom currentChatRoom;
	
	// MAIN HUB PROPERTIES
	ChatList userChatList;
    private UserAccount owner; // NOVEMBER 26
	
	
    // DELETED NOVEMBER 26 KA
//	String chatExample[] = {"2nd Floor group chat",
//							"Engineering department group chat",
//							"IT department group chat",
//							"Coworker-1 private chat",
//							"Manager private chat"};
	


	
	// default constructor
	public MainHub() {	
	}
	
	// MODIFIED NOVEMBER 26 KA
	// ADDED USER ACCOUNT
	public MainHub(Socket newSocket, ObjectInputStream in, ObjectOutputStream out) {
		socket = newSocket;
		msgIn = in;
		msgOut = out;
		// block process until MainHub receive UserAccount from Server then proceed
		try {
			Object temp =  msgIn.readObject();
			if (temp instanceof ITUser) {
				owner = (ITUser) temp;
			}
			else {
				owner = (RegularUser) temp;
			}
			userChatList = owner.getChatList();  // Get user's ChatList
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Background thread to listen to Server
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (closing == false) {
						try {
							Message msgObject = (Message) msgIn.readObject();
							// check if the chatID matches with user's chat list. Update that chat history
							// messages if matched.
							SwingUtilities.invokeLater(() -> {
								/*
								user.addMessage(msgObject);
								currentChatRoom.update();
								*/
								System.out.println(msgObject.getContent()); // replace with above once feature implemented
							});
						} catch (Exception ex) {
							if (!closing) {
								System.out.println("Error receiving msg from Server in MainHub. at line 98");
								ex.printStackTrace();
								break;
							}
							else
								break;
						}
					}
				} 
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();

	}

	public void openMainHub() {
		

		// CHAT LIST
		// MODIFIED NOVEMBER 26
		//JList chatList = new JList(userChatList.getChatListForDisplay());
		JList chatList = new JList(new String[] {"Chat rooms to be display here"});
        chatsContainer.add(new JScrollPane(chatList));
		chatList.addListSelectionListener(new ChatListSelectionListener());
		
		if (owner.getAccessLevel() == 1) {
			itButton.setEnabled(false);
		}
		
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
		buttonContainer.add(itButton);
		buttonContainer.add(createPrivateChatButton);
		buttonContainer.add(createGroupChatButton);
		buttonContainer.add(deleteChatButton);
		buttonContainer.add(openChatButton);
		buttonContainer.add(logoutButton);
		
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		mainFrame.add(chatsContainer);
		mainFrame.add(buttonContainer);
		
		EventListener event = new EventListener();
		itButton.addActionListener(event);
		createPrivateChatButton.addActionListener(event);
		createGroupChatButton.addActionListener(event);
		deleteChatButton.addActionListener(event);
		openChatButton.addActionListener(event);
		logoutButton.addActionListener(event);

		chatList.addListSelectionListener(new ChatListSelectionListener());
		
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.pack();
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		
		// Set closing JFrame with X button to be the same as using Logout button
	    mainFrame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            Message logout = new Message("");
	            logout.setMessageType(MESSAGETYPE.DISCONNECT);
	            try {
	                msgOut.writeObject(logout);
	                msgOut.flush();
	                closing = true;
	                mainFrame.dispose();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
	}
		

	
	
	// BUTTON LISTENERS
	private class EventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			// LOGOUT
			if (event.getActionCommand().equals("Logout")) {
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Logout method will be call here", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				Message logout = new Message("");
				logout.setMessageType(MESSAGETYPE.DISCONNECT);
				try {
					msgOut.writeObject(logout);
					msgOut.flush();
					closing = true;
					
					msgIn.close();
					msgOut.close();
					socket.close();
					
					Client newClient = new Client();
					mainFrame.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// OPEN CHAT
			else if (event.getActionCommand().equals("Open Chat")) {

				// open chat method
				//JOptionPane.showMessageDialog(mainFrame, "Placeholder. Open chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				currentChatRoom = new ChatRoom(msgIn, msgOut, owner); // add arg name for getting chat history
				mainFrame.setVisible(false);
			}
			
			// DELETE CHAT
			else if (event.getActionCommand().equals("Delete Chat")) {
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Delete chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				// TODO: send Message obj to server, with name of chat to be deleted (or exit)
			}
			
			// CREATE GROUP CHAT
			else if (event.getActionCommand().equals("Create Group Chat")) {
				// create group chat method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Create group chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				// TODO: send Message obj to server, with name of chat to be created and participant
			}
			
			// CREATE PRIVATE CHAT
			else if (event.getActionCommand().equals("Create Private Group Chat")) {
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Create private group chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				// Would this just be normal group chat but only between two people
			}
			
			// OPEN IT BUTTON
			else if (event.getActionCommand().equals("IT Button")) {
				// IT Button method
				//JOptionPane.showMessageDialog(mainFrame, "Placeholder. IT button method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				AdminPanel adminPanel = new AdminPanel();
				// still need IT GUI
				// delete chat, get all chat history, get all users, create new user credential, delete user credential
				// logic to be done in Server, just send Message obj of what to do to Server. 
			}
		}
	}
	
	// JLIST LISTENER
	private class ChatListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
	        if (!e.getValueIsAdjusting()) { // Avoid multiple events for the same selection
	            JList source = (JList) e.getSource();
	            selectedChatRoom = (String) source.getSelectedValue();
	        }
		}
	}
}
