package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class UserAccount implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private ChatList userChatList;
	private ConversationLog convoLog;
	private String id;
	private String username;
	private String password;
	private ChatList chatList;

	public UserAccount(String name) {
		id = name;
	}
	
	public UserAccount() {
		
	}
	
	
	public void addMessage(Message msg) {
		// add to conversationHistory through accessing conversationLog
	}
	
	// CONSTRUCTOR
	public UserAccount(String username, String password) {
		id = String.valueOf(count++);
		this.username = username;
		this.password = password;
		chatList = new ChatList(this);
	}
	
	public ChatList getChatList() { // KA
        return chatList;
    }

    public void setChatList(ChatList chatList) { //KA
        this.chatList = chatList;
    }	

	// METHODS
	public boolean login(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}

	public void logout() {
		System.out.println("User " + username + " has logged out.");
	}
	
	// Getters and Setters.
	public String getUserId() {
		return id;
	}

	public void setUserId(String userId) {
		this.id = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getID() {
		return id;
	}
	
	
	public ConversationHistory getHistory(String name) {
		return convoLog.getHistory(name);
	}
}

	
	/*
=======
>>>>>>> main

//	// function that gets the current active chat
//	public Chat getChat() {
//		Chat activeChat;
//
//		return activeChat;
//	}

	@Override
	public String toString() {
		return "UserAccount [userId=" + id + ",username=" + username;
	}
<<<<<<< HEAD
*/	



