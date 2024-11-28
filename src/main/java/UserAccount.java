package main.java;

import java.io.Serializable;

public abstract class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int count = 0;
    private ChatList chatList;
    private String id;
    private String username;
    private String password;
    // abstract method -> Retrieve data from ITUser and RegularUser
    public abstract int getAccessLevel();

    // Constructors
    public UserAccount(String username, String password) {
        this.id = String.valueOf(count++);
        this.username = username;
        this.password = password;
        //this.chatList = new ChatList(this); // Ensure ChatList has this constructor
    }

    public UserAccount(String username, String password, String id) {
        this.id = id;
        this.username = username;
        this.password = password;
        //this.chatList = new ChatList(this); // Ensure ChatList has this constructor
    }

    // Default Constructor
    public UserAccount() {}

    // Getter and Setter methods
    public String getID() {
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

    public ChatList getChatList() {
        return chatList;
    }

    // Override toString
    @Override
    public String toString() {
        return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + "]";
    }

    // Login and Logout Methods
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User " + username + " has logged out.");
    }
    
    public static void setCount(int newCount) {
        count = newCount;
    }
    
    public static int getCount() {
        return count;
    }
    
    /*
    public void setChatList(ChatList chatList) {
        this.chatList = chatList;
    }
    */
    
    /*
    public void setChatList() {
        this.chatList = new ChatList(this);
    }
    */
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



