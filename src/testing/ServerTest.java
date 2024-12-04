package testing;
import main.java.ConversationHistory;
import main.java.ITUser;
import main.java.Message;
import main.java.Server;
import main.java.UserManagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ServerTest {
	Server server;
	
	// Should have default useraccount.txt at src/main/java/useraccounts.txt
	@Test
	public void testServerCreatedAndVerifyCredentials() {
		server = new Server();
		server.testStartDatabase();
		Message msg = new Message("ituser", "ituser123");
		assertEquals("true", Server.verify(msg));
	}
	
	@Test
	public void testAddingUserThroughServer() {
		server = new Server();
		server.testStartDatabase();
		server.addUser("testUser", "123");
		Message msg = new Message("testUser", "123");
		assertEquals("true", Server.verify(msg));
	}
	
	@Test
	public void testRemovingUserThroughServer() {
		server = new Server();
		server.testStartDatabase();
		// add the user first then verify they exist
		server.addUser("testUser2", "123");
		Message msg = new Message("testUser2", "123");
		assertEquals("true", Server.verify(msg));
		// then test remove
		server.removeUser("testUser2");
		assertEquals("false", Server.verify(msg));
	}
	
	// should be empty because no user has been created to send messages
	@Test
	public void testGetLogThroughServer() {
		server = new Server();
		server.testStartDatabase();
		// add the user first then verify they exist
		assertEquals(true, server.getLog().isEmpty());
	}
	
	@Test
	public void testGetUserAccountWithID() {
		server = new Server();
		server.testStartDatabase();
		
		// test getting ituser through getting its access level of 2
		assertEquals(2, server.getAccountWithUserIDs("1").getAccessLevel());
	}
	
	@Test
	public void testGetUserAccountWithUsername() {
		server = new Server();
		server.testStartDatabase();
		
		// test getting ituser through getting its access level of 2
		assertEquals(2, server.getAccount("ituser").getAccessLevel());
	}
	
	@Test
	public void testGetChatListThroughServer() {
		server = new Server();
		server.testStartDatabase();
		
		assertEquals(true, server.getUserChatList("1").isEmpty());
	}
	
	@Test
	public void testAddThenRemoveChatThroughServer() {
		server = new Server();
		server.testStartDatabase();
		server.addNewAccount("testUser", "123");
		server.addChatToList("2", 5);
		assertEquals(false, server.getUserChatList("2").isEmpty());
		
		server.removeChatFromList("2", 5);
		assertEquals(true, server.getUserChatList("2").isEmpty());
		
		server.removeAccount("testUser");
	}
	
	// should return empty because chat 99 is not created yet
	@Test
	public void testGetParticipantsThroughServer() {
		server = new Server();
		server.testStartDatabase();
		// add the user first then verify they exist
		assertEquals(true, server.getParticipants(99).isEmpty());
	}
}
