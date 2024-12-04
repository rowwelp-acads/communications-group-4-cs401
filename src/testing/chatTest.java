package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.runners.Suite;
import main.java.UserAccount;
import main.java.RegularUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.*;

public class chatTest {
	private Chat chat;
	private UserAccount newAcc;
	private int ID;
	
	
	@BeforeEach
	public void setUp() {
		String username = "Rowwel";
		String password = "pass123";
		ID = 1;
		
		newAcc = new RegularUser(username, password, Integer.toString(ID), true);
		
		chat = new Chat(newAcc, ID, true);
		chat.addParticipant(newAcc);
	}
	
	@Test
	public void addParticipantsTesting() {
		
		
		UserAccount nextAcc = new RegularUser("Bryan", "pass123", "2", true);
		
		assertTrue(chat.addParticipant(nextAcc));
		
	}
	
	@Test
    public void removeParticipantsTesting() {
        UserAccount nextAcc = new RegularUser("Bryan", "pass123", "2", true);
        chat.addParticipant(nextAcc);
        assertTrue(chat.removeParticipant("Bryan"));
        List<UserAccount> participants = chat.getParticipants();
        assertEquals(1, participants.size());
    }

	
	@Test
	public void TestGetParticipants() {
		List<UserAccount> participants = chat.getParticipants();
		
		assertNotNull(participants);
		
	}
	
	@Test
	public void getConversationHistory() {
		ConversationHistory history;
		
		history = chat.getConversationHistory();
		assertNotNull(history);
	}

}
