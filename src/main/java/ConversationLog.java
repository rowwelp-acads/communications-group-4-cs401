package main.java;

import java.util.ArrayList;
import java.util.List;

public class ConversationLog {

	private List<Integer> allHistories;
	
	public ConversationLog() {
		allHistories = new ArrayList<>();
	}
		
	public void addConversationHistory(int ChatID) {
		allHistories.add(ChatID);
	}
	
	public List<Integer> displayList() {
		return allHistories;
	}
	
	public boolean doesItExist(int chatID) {
		return allHistories.contains(chatID);
	}
	
}
