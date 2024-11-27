package main.java;

import java.util.ArrayList;
import java.util.List;

public class ConversationLog {

	private String logId;
	private List<ConversationHistory> allHistories;
	
	public ConversationLog(String logId) {
		this.logId = logId;
		allHistories = new ArrayList<ConversationHistory>();
	}
	
	public String getLogId() {
		return logId;
	}
	
	public void addConversationHistory(ConversationHistory hist) {
		allHistories.add(hist);
	}
	
	public List<ConversationHistory> getAllHistories() {
		return allHistories;
	}
	
	public ConversationHistory getHistory(String name) {
		return null;
	}
	
}
