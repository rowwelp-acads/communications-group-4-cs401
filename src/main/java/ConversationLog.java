package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversationLog {

	private Map<Integer, ConversationHistory> allHistories;
	
	public ConversationLog() {
		allHistories = new HashMap<>();
	}
		
	public void addConversationHistory(ConversationHistory newHistory) {
		allHistories.put(newHistory.getChatID(), newHistory);
	}
	
	public List<Integer> displayList() {
		Set<Integer> keys = allHistories.keySet();
		List<Integer> logList = new ArrayList<>();
		
		for (int key : keys) {
			logList.add(key);
		}
		
		return logList;
	}
	
	public ConversationHistory getHistory(int chatID) {
		return allHistories.get(chatID);
	}
	
	public boolean doesItExist(int chatID) {
		return allHistories.containsKey(chatID);
	}
	
}
