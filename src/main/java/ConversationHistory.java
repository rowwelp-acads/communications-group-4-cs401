package main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConversationHistory implements Serializable {
	private int chatID;
	private List<String> Messages;
	
	public ConversationHistory(int id) {
		this.chatID = id;
		Messages = new ArrayList<>();
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public void addMessageToHistory(Message newMessage) {
		String message = newMessage.getSender() + "|" + newMessage.getTimestamp() + "|" + newMessage.getContent();
		Messages.add(message);
	}
		
	public List<String> getMessageList() {
		return Messages;
	}
	
	public void write() {
		String chatFile = Integer.toString(chatID);
		String fileName = chatFile.concat(".txt");
		
		FileWriter myWriter;
		
		try {
			myWriter = new FileWriter(fileName);
			
			for (int i = 0; i < Messages.size(); i++) {
			myWriter.write(Messages.get(i));
			}
			
			myWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void load() {
		
		String chatFile = Integer.toString(chatID);
		String filename = chatFile.concat(".txt");
		
    	File openfile = new File(filename);
    	
    	try {
    		//prevents opening a file that doesn't exist
			if (openfile.createNewFile()) return;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        try {
            Scanner scanner = new Scanner(openfile);
            while (scanner.hasNextLine()) {
            	String data = scanner.nextLine();
            	Messages.add(data);
            }
            
            scanner.close();
        } catch (Exception e) {
        System.out.println(e);
        }

	}
	
	public void load(int newID) {
		
		chatID = newID;
		
		String chatFile = Integer.toString(chatID);
		String filename = chatFile.concat(".txt");
		
    	File openfile = new File(filename);
    	
    	try {
    		//prevents opening a file that doesn't exist
			if (openfile.createNewFile()) return;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        try {
            Scanner scanner = new Scanner(openfile);
            while (scanner.hasNextLine()) {
            	String data = scanner.nextLine();
            	Messages.add(data);
            }
            
            scanner.close();
        } catch (Exception e) {
        System.out.println(e);
        }
		
	}
	
}
