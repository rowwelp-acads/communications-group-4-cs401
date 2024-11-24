package main.java;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Default Content
	
	private UserAccount sender;
	private String content;
	private Date timestamp;
	private MESSAGETYPE type;
	private List<UserAccount> participants;
	private int chatID;
	
	// Content for Log In Messages
	
	private String username;
	private String password;
	
	// CONSTRUCTORS
	// Regular Chat Messages
	public Message(String content, UserAccount sender, int chatID) {
		this.sender = sender;
		this.content = content;
		this.timestamp = new Date();
		this.type = MESSAGETYPE.MESSAGETOSEND;
		this.chatID = chatID;
	}
	
	// CONSTRUCTORS
	// Regular Chat Messages with recipients
	// added by Johnny
	// mostly for debugging for now
	public Message(String content) { // chatID here
		this.content = content;
		this.timestamp = new Date();
	}
	
	// CONSTRUCTORS
	// Log In Messages
	public Message(String username, String password) {
		this.username = username;
		this.password = password;
		this.timestamp = new Date();
		this.type = MESSAGETYPE.LOGINTOSEND;
		this.content = "Login Request";
	}
	
	public UserAccount getSender() {
		return sender;
	}
	
	public String getContent() {
		return content;
	}
	
	public List<UserAccount> getParticipants() {
		return participants;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public MESSAGETYPE getType() {
		return type;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public boolean isChatMessage() {
		return type == MESSAGETYPE.MESSAGETOSEND;
	}
	
	public boolean isLoginMessage() {
		return type == MESSAGETYPE.LOGINTOSEND;
	}
	
	public void setMessageType(MESSAGETYPE type) {
		this.type = type;
	}
	
	
	
	
}
