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
		// TEST HERE
		history.getHistoryFromServer();
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
	
	public List<UserAccount> getParticipants(){
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
		
		// Chat does not have a handle to the network, so the chatroom will handle the checking and retrieval of UserAccount from Server.
		// This method will quite literally just add the checked userAccount.
		
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
	        String systemMessageContent = username + " has left the chat.";
	        Message systemMessage = new Message(systemMessageContent, null);
	        history.addMessage(systemMessage);
	        
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


