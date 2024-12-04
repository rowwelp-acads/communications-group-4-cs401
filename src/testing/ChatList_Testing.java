package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import main.java.*;
import java.io.File;
import java.io.IOException;

public class ChatList_Testing {
    
    @BeforeAll
    static void setUp() {
        // Create the required server components
        try {
            new File("chatListTextFile.txt").createNewFile();
            Server.chatListManager = new ChatListManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 1. CONSTRUCTOR 1
    @Test
    void testConstructor1() {
        UserAccount testUser = new RegularUser("testUser", "password", "1", true);
        ChatList chatList = new ChatList(testUser);
        
        assertNotNull(chatList);
        assertEquals("1", chatList.getID());
    }

    // 2. CONSTRUCTOR 2
    @Test
    void testConstructor2() {
        UserAccount testUser = new RegularUser("testUser", "password", "1", true);
        ChatList chatList = new ChatList(testUser, null, null);
        
        assertNotNull(chatList);
        assertEquals("1", chatList.getID());
    }
}