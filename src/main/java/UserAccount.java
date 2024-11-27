package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class UserAccount implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String id = "";
	private ChatList userChatList;
	private ConversationLog convoLog;

	public UserAccount(String name) {
		id = name;
	}
	
	public UserAccount() {
		
	}
	
	public void openUserAccount(String name) {
		id = name;
		// file of user name hold list of chat, grab it and stored in var chatList
		try {
			File userFile = new File(id);
			Scanner scanner = new Scanner(userFile);
			while (scanner.hasNextLine()) {
				userChatList.addChat(scanner.nextLine());
			}
			
			// with userChatList, grab their conversationHistory from file, each chatRoom has a separate file
			// TODO:
		} 
		catch (FileNotFoundException e) {
			System.out.println("New user, creating new user file");
		}
	}
	
	public void addMessage(Message msg) {
		// add to conversationHistory through accessing conversationLog
	}

	public String getID() {
		return id;
	}
	
	public ChatList getChatList() {
		return userChatList;
	}
	
	public ConversationHistory getHistory(String name) {
		return convoLog.getHistory(name);
	}

	
	/*

// TODO: Write JUnit tests! @BryanMadrigal
// TODO: Comment all class methods!
	public class UserAccount {
		// Attributes.
		private static int num = 0;
		private String username;
		private String userId;
		private String email;
		private String password;

		// will hold this specific user's list of chats
		private Chatroom chatRoom;

		// Constructor.
		public UserAccount(String userId, String username, String email, String password) {
			this.userId = username + Integer.toString(num + 1);
			this.username = username;
			this.email = email;
			this.password = password;
			chatRoom = new Chatroom(this);
		}

		// Getters and Setters.
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		// Methods
		public boolean login(String username, String password) {
			return this.username.equals(username) && this.password.equals(password);
		}

		public void logout() {
			System.out.println("User " + username + " has logged out.");
		}

		public void addChat(Chat chat) {
			// Steps
			// 1. chatRoom.add(chat);
		}

		public void removeChat() {
			// Steps
			// 1. chatRoom.remove(chat);
		}

		// function that gets the current active chat
		public Chat getChat() {
			Chat activeChat;

			return activeChat;
		}

		@Override
		public String toString() {
			return "UserAccount [userId=" + userId + ",username=" + username + ",email=" + email + "]";
		}
	}
*/	
}
