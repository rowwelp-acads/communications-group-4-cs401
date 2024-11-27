package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.ChatList;
import main.java.ConversationHistory;
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
public class MainHub {
	
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
	
	// STREAMS
	ObjectInputStream msgIn;
	ObjectOutputStream msgOut;
	UserAccount user;
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
	public MainHub(ObjectInputStream in, ObjectOutputStream out, UserAccount userAccount) {
		msgIn = in;
		msgOut = out;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					user = (UserAccount) msgIn.readObject();
					
					while (true) {
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
							System.out.println("Error receiving msg from Server in MainHub. at line 98");
							ex.printStackTrace();
							break;
						}
					}
				} 
				catch (Exception ex) {

				}
			}
		}).start();

		owner = userAccount;
		userChatList = owner.getChatList();  // Get user's ChatList

	}
	
	public void openMainHub() {
		

		// CHAT LIST
		// MODIFIED NOVEMBER 26
		JList chatList = new JList(userChatList.getChatListForDisplay());
        chatsContainer.add(new JScrollPane(chatList));
		chatList.addListSelectionListener(new ChatListSelectionListener());
		
		/*
		if (user.getITAuthorized() == false) {
			itButton.setEnabled(false);
		}
		*/
		itButton.setEnabled(false); // replace with above once feature implemented
		
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
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		
		EventListener event = new EventListener();
		itButton.addActionListener(event);
		createPrivateChatButton.addActionListener(event);
		createGroupChatButton.addActionListener(event);
		deleteChatButton.addActionListener(event);
		openChatButton.addActionListener(event);
		logoutButton.addActionListener(event);

		chatList.addListSelectionListener(new ChatListSelectionListener());
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
	}
		

		
	
	// BUTTON LISTENERS
	private class EventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			// LOGOUT
			if (event.getActionCommand().equals("logout")){
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Logout method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
	            }
			
			// OPEN CHAT
			else if (event.getActionCommand().equals("Open Chat")) {

				// open chat method
				//JOptionPane.showMessageDialog(mainFrame, "Placeholder. Open chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				currentChatRoom = new ChatRoom(msgIn, msgOut, user); // add arg name for getting chat history
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
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. IT button method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
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
