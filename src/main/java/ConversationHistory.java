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
	
	public void addMessage(Message message) {
		
	}
	
	
}
