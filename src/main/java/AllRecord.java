package main.java;

import java.io.Serializable;
import java.util.Map;

public class AllRecord implements Serializable{
	private Map<Integer, ConversationHistory> allHistories;
	
	public AllRecord(Map<Integer, ConversationHistory> histories) {
		allHistories = histories;
	}
	
	public Map<Integer, ConversationHistory> getHistories() {
		return allHistories;
	}
	
}
