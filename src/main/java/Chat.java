package main.java;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Chat implements Serializable{
    private int uniqueID;
    private static int count = 0;
    private List<UserAccount> participants;
    private String[] members;
    private ConversationHistory history;
    private String name;
    private UserAccount creator;

    // CONSTRUCTOR 
	public Chat(UserAccount owner, int newUniqueID) {
		creator = owner;
		uniqueID = newUniqueID;
		history = new ConversationHistory(uniqueID);
		participants = new ArrayList<>();
		// TEST HERE
		history.getHistoryFromServer();
	}
	
	public Chat(UserAccount owner, int newUniqueID, boolean test) {
		creator = owner;
		uniqueID = newUniqueID;
		history = new ConversationHistory(uniqueID);
		participants = new ArrayList<>();
		// TEST HERE
	}

	public Chat(UserAccount owner) {
		creator = owner;
		uniqueID = count++;
		history = new ConversationHistory(uniqueID);
	}
	
	// this method is to add the Message to chat history to be displayed in ChatRoom
	// note current design is sending messages between all clients (even back to sender)
	public void addMessageToHistory(Message msgObj) {
		// TODO: add functionality
		history.addMessage(msgObj);
	}
	
	public UserAccount getOwner() {
		return creator;
	}
	
	public int getID() {
		return uniqueID;
	}
	
	public List<UserAccount> getParticipants() {
		return participants;
	}
	
	public void setID(int ID) {
		this.uniqueID = ID;
	}
	
	public Boolean addParticipant(UserAccount user) {
		for (UserAccount participant : participants) {
           if (participant.getUsername().equals(user.getUsername())) {
              System.out.println("User " + user.getUsername() + " is already a participant.");
                return false; // The method will return false if it cannot add the participant.
           }
		}
		
		participants.add(user); 
		
		return true; // The method will return true is the participant was successfully added.
	}
		
	public Boolean removeParticipant(String username) {
		
	    UserAccount userToRemove = null;
	    
	    for (UserAccount user : participants) {
	        if (user.getUsername().equals(username)) {
	            userToRemove = user;
	            break;
	        }
	    }
	    
	    if (userToRemove != null) {
	        participants.remove(userToRemove);

	        // Broadcast Message that User has left a chat
	        System.out.println(username + " has left the chat.");
	        
	        System.out.println("User " + username + " removed from chat.");
	        return true; // Participant has been found in list and removed. 
	    } else {
	        System.out.println("User " + username + " not found in participants.");
	        return false; // Participant has not been removed.
	    }
		
	}
	
	public ConversationHistory getConversationHistory() {
		return history;
	}
}


