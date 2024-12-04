package testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.java.ConversationLog;
import main.java.ConversationHistory;
import main.java.Message;

class ConvoLogTest {

	@Test
	void testConvoLog() {
		int chatID = 1;
		
		ConversationHistory testHist = new ConversationHistory(chatID);
		ConversationLog testLog = new ConversationLog();
		
		testLog.addConversationHistory(testHist);
		
		assertTrue(testLog.getHistory(chatID).getChatID() == testHist.getChatID());
	}
	
	@Test
	void testConvoDisplayList() {
		int chatID = 1;
		int secChat = 2;
		
		ConversationHistory testHist = new ConversationHistory(chatID);
		ConversationHistory testHist2 = new ConversationHistory(secChat);
		ConversationLog testLog = new ConversationLog();
		
		testLog.addConversationHistory(testHist);
		testLog.addConversationHistory(testHist2);
		
		
		assertTrue(testLog.displayList().get(0) == chatID && testLog.displayList().get(1) == secChat);
		
	}
	
	@Test
	void testConversationHistory() {
		int chatID = 1;
		
		Message testMes = new Message("John", "pass");
		
		ConversationHistory testHist = new ConversationHistory(chatID);
		testHist.addMessage(testMes);
		
		String test = testMes.getTimestamp() + " || " + testMes.getSender().getUsername() + ": " + testMes.getContent();
		
		assertTrue(test.equals(testHist.getMessageList().get(0)));
	}
	
	@Test
	void testConvoHistoID() {
		int chatID = 1;
		
		ConversationHistory testHist = new ConversationHistory(chatID);
		
		assertTrue(testHist.getChatID() == chatID);
		
	}

}
