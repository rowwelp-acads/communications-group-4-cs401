package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// This class is to hold a list of Chats
// Meant to be displayed through the JList located in Main Hub
public class ChatList implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String ID;
    private UserAccount owner;
    private List<Chat> listOfChats;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // CONSTRUCTOR
    public ChatList(UserAccount userAccount, ObjectInputStream in, ObjectOutputStream out) {
        ID = userAccount.getID();
        owner = userAccount;
        listOfChats = new ArrayList<>();
        this.out = out;
        this.in = in;
        updateListOfChats();
    }

    // METHODS:
    
    // 1. Creates a new Chat
    public void createChat() {
        Chat newChat = new Chat(owner);
        // PROBLEM ::: WHAT IF CHAT IS CREATED AND USES THE SAME ID AS OTHERS
        listOfChats.add(newChat);
        
        //Server.addChatToList(ID, newChat.getID());
        
        // SENDING A MESSAGE TO SERVER //
        
        try {
            // - content: "Create Chat: X" where X is the chat's ID
            // - sender: UserAccount that created the chat
            // - chatID: The ID of the newly created chat
            Message request = new Message("Create Chat: " + newChat.getID(), owner, newChat.getID());
            
            request.setMessageType(MESSAGETYPE.ADD_CHAT);
            
            out.writeObject(request);
            out.flush();
            
        } catch (IOException e) {
            System.err.println("Error creating chat: " + e.getMessage());
        }
    }

    // 2. Removes a Chat
    public void removeChat(int chatUniqueID) {
    	// for each chat "chat"
    	// get its id "-> chat.getID()"
    	// then check if its equal to the chatUniqueID passed "== chatUniqueID"
    	// if it is equals, then remove it from listOfChats ".removeIf"
        listOfChats.removeIf(chat -> chat.getID() == chatUniqueID);
        // then update the server side
        
        // Server.removeChatFromList(ID, chatUniqueID);
        
        // SENDING A MESSAGE TO SERVER //
        
        try {
        	// Parameters:
            // - content: "Remove Chat: X" where X is the chat's ID
            // - sender: UserAccount that's removing the chat
            // - chatID: The ID of the chat to remove
            Message request = new Message("Remove Chat: " + chatUniqueID, owner, chatUniqueID);
            
            request.setMessageType(MESSAGETYPE.REMOVE_CHAT);
            
            out.writeObject(request);
            out.flush();
            
        } catch (IOException e) {
            System.err.println("Error removing chat: " + e.getMessage());
        }
        
    }

    // 3. Updates the userAccount's listOfChats by grabbing the data from the server
    public void updateListOfChats() {
        // List<Integer> chatIDs = Server.getUserChatList(ID);
        
    	// SENDING A MESSAGE TO SERVER //
    	
    	try {
            Message request = new Message(ID);
            request.setMessageType(MESSAGETYPE.GET_CHATLIST);
            out.writeObject(request);
            out.flush();
            
            Message response = (Message) in.readObject();
            if (response.getType() == MESSAGETYPE.CHATLIST_RESPONSE) {
            	List<Integer> chatIDs = response.getChatIDs();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error updating chat list: " + e.getMessage());
        }
    	
    	/////////////////////////////////
    	
    	listOfChats.clear();
        
        // for every chatID inside the list of chatIDs a userAccount has taken from the Server,
        for (Integer chatID : chatIDs) {
        	// Create a new Chat
            Chat newChat = new Chat(owner, chatID, in, out);
            // Then set the ID of that chat so it gets it's conversation history
//            newChat.setID(chatID);
            // Then update this userAccount's listofChats
            listOfChats.add(newChat);
        }
    }

    // 4. 
    public String[] getChatListForDisplay() {
    	// Check if the ChatList belongs to the IT USER
        if (ID.equals("1")) {
        	// IF it does, then returns an empty array (nothing to display)
            return new String[0];
        }
        // Create a displayList the same size as this user's listOfChats
        String[] displayList = new String[listOfChats.size()];
        
        // for loop to create titles
        for (int i = 0; i < listOfChats.size(); i++) {
            displayList[i] = "Chat " + listOfChats.get(i).getID();
        }
        return displayList;
    }
    
    
    // GETTERS
    public int getLength() {
        return listOfChats.size();
    }

    public Chat getChat(int chatID) {
        for (Chat chat : listOfChats) {
            if (chat.getID() == chatID) {
                return chat;
            }
        }
        return null; 
    }

    public String getID() {
        return this.ID;
    }
    
    public List<Chat> getListOfChats() {
        return this.listOfChats;
    }
}