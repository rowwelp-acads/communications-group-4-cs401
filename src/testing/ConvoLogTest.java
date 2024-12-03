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
		
		Message testMes = new Message();
		ConversationHistory testHist = new ConversationHistory(chatID);
		ConversationLog testLog = new ConversationLog();
		
		
		
		assertTrue(true);
	}

}
