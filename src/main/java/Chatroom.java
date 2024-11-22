package main.java;

import java.util.List;

// This class is to hold a list of Chats
// Meant to be displayed through the JList located in Main Hub
public class Chatroom {
	private int uniqueID;
	private static int count;
	private List<Chat> listOfChats; 
	private UserAccount owner;
	
	
	public Chatroom(UserAccount username) {
		owner = username;
		uniqueID = count++;
	}
	
	public void createChat() {
		Chat newChat = new Chat(owner);
		
		addChat(newChat);
	}
	private void addChat(Chat chat) {
		
	}
	
	public void removeChat() {
		
	}
}

// Methods to add:
// 1. addChat to List
// 2. removeChat from List
// 3. getChat; returns a Chat
// 4. OpenChat; Displays in the GUI (Opening a Chat)
// 5. getChatName; returns name

