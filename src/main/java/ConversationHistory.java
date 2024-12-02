package main.java;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationHistory implements Serializable {
	private int chatID;
	private List<String> Messages;
    private ObjectInputStream in;
    private ObjectOutputStream out;
	
	public ConversationHistory(int id) {
		this.chatID = id;
		//Messages = Server.getChatHistory(id); or getHistoryFromServer(); below
		//Messages = Server.getChatHistory(id);
		// TEST HERE
		Messages = new ArrayList<>();
	}
	
	public ConversationHistory(int id, String overloading) {
		chatID = id;
	}
	
	public ConversationHistory(int id, ObjectInputStream in, ObjectOutputStream out) {
		this.chatID = id;
		this.in = in;
		this.out = out;
		load(id);
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public void getHistoryFromServer() {
		Messages = Server.getChatHistory(chatID);
	}
	
	public void addMessage(Message newMessage) {
		String message = newMessage.getTimestamp() + " || " + newMessage.getSender().getUsername() + ": " + newMessage.getContent();
		Messages.add(message);
	}
	
	public void addMessage(String newMessage) {
		Messages.add(newMessage);
	}
		
	public List<String> getMessageList() {
		return Messages;
	}
	
	
	//for pre-existing chat
	public void load(int chatID) {
		
    	// SENDING A MESSAGE TO SERVER //
    	
    	try {
            Message request = new Message(chatID, MESSAGETYPE.GETHISTORY);
            out.writeObject(request);
            out.flush();
            
            Message response = (Message) in.readObject();
            if (response.getType() == MESSAGETYPE.SENDHISTORY) {
            	Messages = response.getChatHistory();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error updating chat list: " + e.getMessage());
        }
		
	}
	
	
}
