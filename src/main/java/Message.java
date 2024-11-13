package main.java;

import java.util.Date;
import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Default Content
	
	private UserAccount sender;
	private String content;
	private Date timestamp;
	private MESSAGETYPE type;
	private UserAccount[] recipients;
	
	// Content for Log In Messages
	
	private String username;
	private String password;
	
	
	// CONSTRUCTORS
	// Regular Chat Messages
	public Message(String content, UserAccount sender, UserAccount[] recipients) {
		this.sender = sender;
		this.content = content;
		this.timestamp = new Date();
		this.recipients = recipients;
		this.type = MESSAGETYPE.MESSAGETOSEND;
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
	
	public UserAccount[] getRecipients() {
		return recipients;
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
	
	public boolean isChatMessage() {
		return type == MESSAGETYPE.MESSAGETOSEND;
	}
	
	public boolean isLoginMessage() {
		return type == MESSAGETYPE.LOGINTOSEND;
	}
	
	
	
	
	
}
