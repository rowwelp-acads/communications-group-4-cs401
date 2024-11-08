package main.java;

import java.util.Date;

public class Message {
	
	private UserAccount sender;
	private String content;
	private Date timestamp;
	private MESSAGETYPE type;
	
	
	public Message(String content, UserAccount sender) {
		this.sender = sender;
		this.content = content;
		this.timestamp = new Date();
	}
	
	
	private int getMessageID() {
		return messageID;
	}
	private UserAccount getSender() {
		return sender;
	}
	private String getContent() {
		return content;
	}
	private Date getTimestamp() {
		return timestamp;
	}
	
	
	
	
	
}
