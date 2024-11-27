package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.ChatList;
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
	
	// MAIN HUB PROPERTIES
	ChatList userChatList;
	String selectedChatRoom = "";
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
		owner = userAccount;
		userChatList = owner.getChatList();  // Get user's ChatList
	}
	
	public void openMainHub() {
		
		// CHAT LIST
		// MODIFIED NOVEMBER 26
		JList chatList = new JList(userChatList.getChatListForDisplay());
        chatsContainer.add(new JScrollPane(chatList));
		chatList.addListSelectionListener(new ChatListSelectionListener());
		
		// GUI COMPONENTS
		itButton.setEnabled(false);
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
		
		// MAIN HUB THREAD
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Message msgObject = (Message) msgIn.readObject();
						// check if the chatID matches with user's chat list. Update that chat history messages if matched.
						SwingUtilities.invokeLater(() -> {
							System.out.println(msgObject.getContent());
							/*
							for (int i = 0; i < userChatList.getLength(); i++) {
								if (userChatList.getChat(i).getID() == msgObject.getChatID()) {
									userChatList.getChat(i).addMessageToHistory(msgObject);
								}
							} */
						});
					}
					catch (Exception ex) {
						System.out.println("Error receiving msg from Server in MainHub. at line 98");
						ex.printStackTrace();
						break;
					}
				}
			}
		}).start();
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
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Open chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				ChatRoom chatRoom = new ChatRoom(msgIn, msgOut);
				mainFrame.setVisible(false);
			}
			
			// DELETE CHAT
			else if (event.getActionCommand().equals("Delete Chat")) {
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Delete chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			
			// CREATE GROUP CHAT
			else if (event.getActionCommand().equals("Create Group Chat")) {
				// create group chat method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Create group chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			
			// CREATE PRIVATE CHAT
			else if (event.getActionCommand().equals("Create Private Group Chat")) {
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Create private group chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			
			// OPEN IT BUTTON
			else if (event.getActionCommand().equals("IT Button")) {
				// IT Button method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. IT button method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
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
