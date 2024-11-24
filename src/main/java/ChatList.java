package main.java;

import java.util.List;

// This class is to hold a list of Chats
// Meant to be displayed through the JList located in Main Hub
public class ChatList {
	private int uniqueID;
	private static int count;
	private List<Chat> listOfChats; 
	private UserAccount owner;
	
	
	public ChatList(UserAccount username) {
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
	
	public boolean verifyChat(int chatID) {
		return false;
	}
	
	public int getLength() {
		return listOfChats.size();
	}
	
	public Chat getChat(int index) {
		return listOfChats.get(index);
	}
}

// Methods to add:
// 1. addChat to List
// 2. removeChat from List
// 3. getChat; returns a Chat
// 4. OpenChat; Displays in the GUI (Opening a Chat)
// 5. getChatName; returns name

