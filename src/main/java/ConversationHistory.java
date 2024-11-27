package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationHistory implements Serializable {
	private List<String> Messages;
	private String chatName;
	
	public ConversationHistory() {
		Messages = new ArrayList<>();
	}

	public String getName() {
		return chatName;
	}

	
	/*
	 * not sure if this should be here, delete if not
	 */
//	public List<Message> getLatestMessages(String chatId) {
//		//update method if it does not match with chat loadconvo command
//		return chats.get(chatId).loadConversationalHistory();
//	}
//	
}
