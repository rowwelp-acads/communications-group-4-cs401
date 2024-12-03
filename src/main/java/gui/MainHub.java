package main.java.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.AllRecord;
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
	JButton createGroupChatButton = new JButton("Create Group Chat");
	JButton deleteChatButton = new JButton("Delete Chat");
	JButton openChatButton = new JButton("Open Chat");
	JButton logoutButton = new JButton("Logout");
	JPanel buttonContainer = new JPanel();
	JPanel chatsContainer = new JPanel();
	boolean closing = false;
	boolean roomSelected = false;
	Message msgObject;
	JList<String> chatList = new JList();
	AllRecord record;
	AdminPanel admin;
	JScrollPane scrollPane;
	
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
			System.out.println(owner.getChatList());
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
							Object object = msgIn.readObject();
							
							if (object instanceof Message) {
								msgObject = (Message) object;
								
								if(msgObject.getType() == MESSAGETYPE.SENDPARTICIPANTS ) {
									System.out.println("Participants requested from server and arrived to client, chat ID " + msgObject.getChatID());
									List<UserAccount> participants;
									participants = msgObject.getParticipants();
									
									System.out.println(msgObject.getChatID());
									
									for(UserAccount participant : participants) {
										userChatList.getChat(msgObject.getChatID()).addParticipant(participant);
									}
									continue;
								}
							}
							else if (object instanceof AllRecord) {
								record = (AllRecord) object;
								displayRecord();
								continue;
							}
							else if (object instanceof ChatList) {
								ChatList chat = (ChatList) object;
								owner.setList(chat);
								userChatList = owner.getChatList();
								chatList.setListData(userChatList.getChatListForDisplay());
							}
							// check if the chatID matches with user's chat list. Update that chat history
							// messages if matched.
							SwingUtilities.invokeLater(() -> {
								// add the message to convoHistory
								if(msgObject != null) {
									owner.addMessage(msgObject);
								}
								
								
								// if the message also belong to the current active chat room, update its GUI to display the new history
								if (currentChatRoom != null && msgObject.getChatID() == currentChatRoom.getChatID()) {
									currentChatRoom.updateChatRoom();
								}
								
								//System.out.println(msgObject.getContent()); // replace with above once feature implemented
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
		chatList = new JList(userChatList.getChatListForDisplay());
		scrollPane = new JScrollPane(chatList);
		scrollPane.setPreferredSize(new Dimension(200,200));
		//JList chatList = new JList(new String[] {"Chat rooms to be display here"});
        chatsContainer.add(scrollPane);
		chatList.addListSelectionListener(new ChatListSelectionListener());
		
		if (owner.getAccessLevel() == 1) {
			itButton.setEnabled(false);
		}
		
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
		buttonContainer.add(itButton);
		buttonContainer.add(createGroupChatButton);
	
		buttonContainer.add(openChatButton);
		buttonContainer.add(logoutButton);
		
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		mainFrame.add(chatsContainer);
		mainFrame.add(buttonContainer);
		
		EventListener event = new EventListener();
		itButton.addActionListener(event);
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
				exit();
			}

			// OPEN CHAT
			else if (event.getActionCommand().equals("Open Chat")) {

				// open chat method
				if (roomSelected) {
					currentChatRoom = new ChatRoom(msgIn, msgOut, owner, selectedChatRoom, MainHub.this); // add arg name for getting chat history
					mainFrame.setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(mainFrame, "No chat selected! Please choose one first.", "Warning", JOptionPane.ERROR_MESSAGE);
			}
			
			// DELETE CHAT
			else if (event.getActionCommand().equals("Delete Chat")) {
				if (roomSelected) {
					Message msg = new Message();
					msg.setMessageType(MESSAGETYPE.REMOVE_CHAT);
					try {
						String stringID = selectedChatRoom.substring(selectedChatRoom.length()-1);
						int id = Integer.parseInt(stringID);
						msg.setID(id);
						msg.setUser(owner);
						msgOut.writeObject(msg);
						msgOut.flush();
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(mainFrame, "No chat selected! Please choose one first.", "Warning", JOptionPane.ERROR_MESSAGE);
			}
			
			// CREATE GROUP CHAT
			else if (event.getActionCommand().equals("Create Group Chat")) {
				// create group chat method
				// TODO: send Message obj to server, with name of chat to be created and participant
				String chatId = JOptionPane.showInputDialog(mainFrame, "Enter id of new group chat: ", null);
				if (chatId == null) {
					return;
				} 
				else if (chatId.length() == 0) {
					JOptionPane.showMessageDialog(mainFrame, "No username entered. Please try again.", "Warning",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					Message msg = new Message();
					msg.setMessageType(MESSAGETYPE.ADD_CHAT);
					msg.setUsername(owner.getUsername());
					msg.setUser(owner);
					try {
						msg.setID(Integer.parseInt(chatId));
						msgOut.writeObject(msg);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			
			// OPEN IT BUTTON
			else if (event.getActionCommand().equals("IT Button")) {
				// IT Button method
				//JOptionPane.showMessageDialog(mainFrame, "Placeholder. IT button method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
				admin = new AdminPanel(msgOut);
				// still need IT GUI
				// delete chat, get all chat history, get all users, create new user credential, delete user credential
				// logic to be done in Server, just send Message obj of what to do to Server. 
			}
		}
	}
	
	public void resetCurrentChatRoom() {
		currentChatRoom = null;
		chatList.clearSelection();
		selectedChatRoom = "";
		roomSelected = false;
		
	}
	
	public JFrame getFrame() {
		return mainFrame;
	}
	
	private void displayRecord() {
		JFrame recordFrame = new JFrame();
		
		// grab the history has-map
		Map<Integer, ConversationHistory> histories = record.getHistories();
		// StringBuilder to store all the history as String
		StringBuilder historyList = new StringBuilder();
		// iterate through all key, then append the key as chatID then append its history
		for (Map.Entry<Integer, ConversationHistory> entry : histories.entrySet()) {
			historyList.append("Chat ").append(entry.getKey()).append(":\n");
			for (String msg : entry.getValue().getMessageList()) {
				historyList.append(msg).append("\n");
			}
			historyList.append("\n");
		}
		
		// display the StringBuilder
		JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // read only
        textArea.setText(historyList.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        recordFrame.add(scrollPane);
        
		recordFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		recordFrame.setAlwaysOnTop(true);
        recordFrame.pack();
		recordFrame.setVisible(true);		
		recordFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(false);
		admin.close();
		
		// Closing handling
	    recordFrame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            mainFrame.setVisible(true);
	            recordFrame.dispose();
	        }
	    });
		
	}
	
	private void exit() {
		Message logout = new Message("");
		logout.setMessageType(MESSAGETYPE.DISCONNECT);
		logout.setUsername(owner.getUsername());
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
	
	// JLIST LISTENER
	private class ChatListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
	        if (!e.getValueIsAdjusting()) { // Avoid multiple events for the same selection
	            JList source = (JList) e.getSource();
	            selectedChatRoom = (String) source.getSelectedValue();
	            roomSelected = true;
	        }
		}
	}
}
