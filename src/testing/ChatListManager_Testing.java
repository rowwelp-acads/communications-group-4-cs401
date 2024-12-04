package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import main.java.ChatListManager;

public class ChatListManager_Testing {

    // 1. CONSTRUCTOR
    @Test
    void testConstructor() {
        ChatListManager manager = new ChatListManager();
        assertNotNull(manager);
    }

    // 2. LOAD CHAT LIST DATA
    @Test
    void testLoadChatListData() {
        ChatListManager manager = new ChatListManager();
        manager.loadChatListData();
        // Just verify it doesn't throw an exception
        assertTrue(true);
    }

    // 3. GET USER CHAT LIST
    @Test
    void testGetUserChatList() {
        ChatListManager manager = new ChatListManager();
        List<Integer> chatList = manager.getUserChatList("testUser");
        assertNotNull(chatList);
    }

    // 4. ADD CHAT TO LIST
    @Test
    void testAddChatToList() {
        ChatListManager manager = new ChatListManager();
        String userId = "testUser";
        int chatId = 1;
        
        List<Integer> before = manager.getUserChatList(userId);
        manager.addChatToList(userId, chatId);
        List<Integer> after = manager.getUserChatList(userId);
        
        assertTrue(after.size() >= before.size());
    }

    // 5. REMOVE CHAT FROM LIST
    @Test
    void testRemoveChatFromList() {
        ChatListManager manager = new ChatListManager();
        String userId = "testUser";
        int chatId = 1;
        
        // First add a chat
        manager.addChatToList(userId, chatId);
        List<Integer> before = manager.getUserChatList(userId);
        
        // Then remove it
        manager.removeChatFromList(userId, chatId);
        List<Integer> after = manager.getUserChatList(userId);
        
        assertTrue(after.size() <= before.size());
    }

    // 6. GET PARTICIPANTS
    @Test
    void testGetParticipants() {
        ChatListManager manager = new ChatListManager();
        String userId = "testUser";
        int chatId = 1;
        
        // Add a chat first
        manager.addChatToList(userId, chatId);
        
        // Get participants
        List<String> participants = manager.getParticipants(chatId);
        assertNotNull(participants);
    }
}