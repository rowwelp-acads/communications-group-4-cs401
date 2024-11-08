package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/*
 * TODO: create buttons and JList
 * add buttons and JList to panels.
 * add panels to JFrame
 * add functionalities
 *  
 */
public class MainHub {
	JFrame mainFrame = new JFrame("Main Hub");
	JButton itButton = new JButton("IT Button");
	JButton createPrivateChatButton = new JButton("Create Private Group Chat");
	JButton createGroupChatButton = new JButton("Create Group Chat");
	JButton deleteChatButton = new JButton("Delete Chat");
	JButton openChatButton = new JButton("Open Chat");
	JButton logoutButton = new JButton("Logout");
	JPanel buttonContainer = new JPanel();
	JPanel chatsContainer = new JPanel();
	
	String chatExample[] = {"2nd Floor group chat",
							"Engineering department group chat",
							"IT department group chat",
							"Coworker-1 private chat",
							"Manager private chat"};
	public MainHub() {
		
		JList chatList = new JList(chatExample);
		chatsContainer.add(new JScrollPane(chatList));
		
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
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
	}
	
	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("logout")){
					// logout method
					JOptionPane.showMessageDialog(mainFrame, "Placeholder. Logout method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
	            }
			else if (event.getActionCommand().equals("Open Chat")) {
				// open chat method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Open chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (event.getActionCommand().equals("Delete Chat")) {
				// delete chat method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Delete chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (event.getActionCommand().equals("Create Group Chat")) {
				// create group chat method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Create group chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (event.getActionCommand().equals("Create Private Group Chat")) {
				// create private group chat method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. Create private group chat method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (event.getActionCommand().equals("IT Button")) {
				// IT Button method
				JOptionPane.showMessageDialog(mainFrame, "Placeholder. IT button method will be call here", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		
	}
	
	private class ChatListSelectionListener implements ListSelectionListener {

		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO work with EventListener buttons
			
		}
		
	}
	
}
