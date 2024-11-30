package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationHistory implements Serializable {
	private int chatID;
	private List<String> Messages;
	
	public ConversationHistory(int id) {
		this.chatID = id;
		Messages = new ArrayList<>();
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public void addMessage(Message newMessage) {
		String message = newMessage.getSender() + "|" + newMessage.getTimestamp() + "|" + newMessage.getContent();
		Messages.add(message);
	}
	
	public void addMessage(String newMessage) {
		Messages.add(newMessage);
	}
		
	public List<String> getMessageList() {
		return Messages;
	}
	
	public void load() {
		
		//to do

	}
	
	
}
