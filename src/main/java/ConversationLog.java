package main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ConversationLog implements Serializable{

	private Map<Integer, ConversationHistory> allHistories;
	
	public ConversationLog() {
		allHistories = new HashMap<>();
	}
		
	public void addConversationHistory(ConversationHistory newHistory) {
		allHistories.put(newHistory.getChatID(), newHistory);
	}
	
	public void addMessageToLog(int chatID, Message newMessage) {
		allHistories.get(chatID).addMessage(newMessage);;
	}
	
	public List<Integer> displayList() {
		Set<Integer> keys = allHistories.keySet();
		List<Integer> logList = new ArrayList<>();
		
		for (int key : keys) {
			logList.add(key);
		}
		
		return logList;
	}
	
	public Map<Integer, ConversationHistory> getHistories() {
		return allHistories;
	}
	
	public ConversationHistory getHistory(int chatID) {
		
		if(allHistories.containsKey(chatID)) {
			return allHistories.get(chatID);
		}
		
		String chatFile = Integer.toString(chatID);
		String filename = chatFile.concat(".txt");
		
		File openfile = new File(filename);
		
		ConversationHistory convoHistory = new ConversationHistory(chatID);
		
        try {
            Scanner scanner = new Scanner(openfile);
            while (scanner.hasNextLine()) {
            	String data = scanner.nextLine();
            	convoHistory.addMessage(data);
            }
            
            scanner.close();
        } catch (Exception e) {
        System.out.println(e);
        }
        
        allHistories.put(chatID, convoHistory);
        //TEST HERE
         return convoHistory;
		
        ///////////////////////
		
		//return allHistories.get(chatID);
	}
	
	public boolean doesItExist(int chatID) {
		return allHistories.containsKey(chatID);
	}
	
	
	public void write(int chatID) {
		
		String chatFile = Integer.toString(chatID);
		String fileName = chatFile.concat(".txt");
		
		FileWriter myWriter;
		
		try {
			myWriter = new FileWriter(fileName);
			
			List<String> history = allHistories.get(chatID).getMessageList();
			for (int i = 0; i < history.size(); i++) {
			myWriter.write(history.get(i) + " \n");
			}
			
			myWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> load(int chatID) {
		
		if(!allHistories.containsKey(chatID)) {
			ConversationHistory newHistory = new ConversationHistory(chatID);
			allHistories.put(chatID, newHistory);
			return newHistory.getMessageList();
		}
		
		String chatFile = Integer.toString(chatID);
		String filename = chatFile.concat(".txt");
		
		ConversationHistory history = allHistories.get(chatID);
		
		File openfile = new File(filename);
    	
        try {
            Scanner scanner = new Scanner(openfile);
            while (scanner.hasNextLine()) {
            	String data = scanner.nextLine();
            	history.addMessage(data);
            }
            
            scanner.close();
        } catch (Exception e) {
        System.out.println(e);
        }
		
        return history.getMessageList();
        
	}
	
}
