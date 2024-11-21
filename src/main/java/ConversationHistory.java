package main.java;

import java.util.ArrayList;
import java.util.List;

public class ConversationHistory {
	private String userId;
	private List<Chat> chats;
	
	public ConversationHistory(String userId) {
		this.userId = userId;
		chats = new ArrayList<Chat>();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void addChat(Chat newChat) {
		chats.add(newChat);
	}
	
	public void removeChat(Chat removeChat) {
		chats.remove(removeChat);
	}
	
	/*
	 * not sure if this should be here, delete if not
	 */
	public List<Message> getLatestMessages(String chatId) {
		//update method if it does not match with chat loadconvo command
		return chats.get(chatId).loadConversationalHistory();
	}
	
}
