package main.java;

import java.io.*;
import java.util.*;

public class ChatListManager {
    private static final String CHAT_LIST_FILE = "chatListTextFile.txt";
    private Map<String, List<Integer>> chatLists;  // Key: UserID, Value: List of Chat IDs
    
    public ChatListManager() {
        chatLists = new HashMap<>();
        loadChatListData();
        //System.out.println(chatLists);
    }
    
    // Method that loads the chat list data
    public void loadChatListData() {
    	try {
            chatLists.clear();
            BufferedReader reader = new BufferedReader(new FileReader(CHAT_LIST_FILE));
            String line;
            // Loop that goes through each line in the file
        	// Start of the loop
            while ((line = reader.readLine()) != null) {
            	
            	// If the line starts with "ChatListID:" then get the ID
                if (line.startsWith("ChatListID:")) {
                    String userID = line.substring(11).trim();  // Get ID after "ChatListID:"
                    // The next line should be the chatIDs
                    String chatIDsLine = reader.readLine();
                    
                    // If the chatIDs line is NOT empty
                    if (chatIDsLine != null) {
                    	// Create a new List to hold the chatIDs
                        List<Integer> chatIDsList = new ArrayList<>();
                        // Handle IT User case (ID:1)
                        if (userID.equals("1")) {
                            chatLists.put(userID, chatIDsList);
                            // We've found the IT user, go back to the start of the loop
                            continue;
                        }
                        
                        // Handle regular users
                        // If the chatIDs line is NOT empty and is NOT equals to 0 (not IT user)
                        if (!chatIDsLine.trim().isEmpty() && !chatIDsLine.equals("0")) {
                        	// Split the line, then add it to a String array called chatIDs
                            String[] chatIDs = chatIDsLine.split(",");
                            // Loop through the chatIDs array then add it to the chatIDsList
                            for (String id : chatIDs) {
                                chatIDsList.add(Integer.parseInt(id.trim()));
                            }
                        }
                        // Add the userID and the chatIDs List to the HashMap
                        chatLists.put(userID, chatIDsList);
                    }
                }
            }
            reader.close();
            System.out.println("Loaded chat lists for " + chatLists.size() + " users");					// DEBUG CONSOLE

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    	
    // Method that returns the ChatList of the specified userID
    public List<Integer> getUserChatList(String userID) {
    	System.out.println(userID);
    	// If the userID exists in the map, return its list
        // If it doesn't exist, return an empty ArrayList
        return chatLists.getOrDefault(userID, new ArrayList<>());
    }

    
 // Method that ADDS a chat to the list when a userAccount creates a new chat
    public void addChatToList(String userID, int chatID) {
        // Get user's current chat list, or create new one if user not in map
        List<Integer> userChats = chatLists.getOrDefault(userID, new ArrayList<>());
        
        // Don't add chats to IT user's list
        if (userID.equals("1")) {
            return;
        }
        if (userChats.contains(chatID)) {
            return;
        }
        // Add the new chatID to the list
        userChats.add(chatID);
        
        // Update the map with new list
        chatLists.put(userID, userChats);
        // Update the file to reflect changes
        updateFile();
    }
    
    // Method that REMOVES a chat from the list if a userAccount deletes a chat
    public void removeChatFromList(String userID, int chatID) {
    	// Get the user's chat list from the map
        List<Integer> userChats = chatLists.get(userID);
        
        // userChats now hold the chatIDs a userAccount has access to
        // Now we can remove the specified chat from the user's chatList
        userChats.remove(Integer.valueOf(chatID));
        
        // Now that we removed it, we now update the hashmap
        chatLists.put(userID, userChats);
        
        // Then we update the TEXT FILE
        updateFile();
    }
    
    
    // Method that updates the TEXT FILE
    private void updateFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CHAT_LIST_FILE));
            for (Map.Entry<String, List<Integer>> entry : chatLists.entrySet()) {
                writer.write("ChatListID:" + entry.getKey());
                writer.newLine();
                
                // Special case for IT User
                if (entry.getKey().equals("1")) {
                    writer.write("0");
                } else {
                    // Write chat IDs as comma-separated list
                    List<Integer> chatIDs = entry.getValue();
                    if (chatIDs.isEmpty()) {
                        writer.write("0");
                    } else {
                        StringBuilder line = new StringBuilder();
                        for (int i = 0; i < chatIDs.size(); i++) {
                            line.append(chatIDs.get(i));
                            if (i < chatIDs.size() - 1) {
                                line.append(",");
                            }
                        }
                        writer.write(line.toString());
                    }
                }
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.err.println("Error updating chat list file: " + e.getMessage());
        }
    }
}