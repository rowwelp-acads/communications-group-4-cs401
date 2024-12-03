package main.java;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class UserManagement implements Serializable{
    // File path for storing user credentials
    private static final String USER_FILE = "src/main/java/useraccounts.txt";
    
    // In-memory storage of user credentials (username -> password mapping)
    private Map<String, String> userCredentials = new HashMap<>();
    
    private List<UserAccount> userList = new ArrayList<>();
    
    // CONSTRUCTOR
    public UserManagement() {
        loadUserCredentials();
        System.out.println(userList);
    }

    private void loadUserCredentials() {
    	// Create a File Object
    	File file = new File(USER_FILE);
    	
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
    		String line;	// Store each line read from the file
    	    boolean firstAccount = true;	// Toggle so that we know we aint reading in an ITuser

    	    // Read each line from the file
    	    while ((line = reader.readLine()) != null) {
    	    	// Skip any empty lines just in case
    	    	if (line.trim().isEmpty()) {
    	    		continue;
    	    	}
    	    	// use the "," as the delimitter
    	    	String[] parts = line.split(",");
    	    	// Check that the line has 3 parts (We are reading in an account)
    	    	if (parts.length == 3) {
    	    		String username = parts[0];	// username
    	    		String password = parts[1];	// password
    	    		String id = parts[2];		// UniqueID
    	    		// Add the credentials to the map
    	    		userCredentials.put(username, password);

    	    		// If its the firstAccount then we make it an ITUser
    	    		if (firstAccount) {
    	    			ITUser newITUser = new ITUser(username, password, id, this);
    	    			userList.add(newITUser);
    	    			firstAccount = false;
    	            } else {
    	            	userList.add(new RegularUser(username, password, id));
    	            }
    	    		
    	        } else if (parts.length == 1) {
    	        	int count = Integer.parseInt(parts[0].trim());	// Get the count Value
    	        	UserAccount.setCount(count);	// Set the count in the UserAccount class
    	        } 
    	   }
    	} catch (IOException e) {
    		System.err.println("Error loading user credentials: " + e.getMessage());
    	    e.printStackTrace();
    	}
}
    
    public void setupChatLists() {
        System.out.println("\nSetting up ChatLists for users...");
        for (UserAccount user : userList) {
            System.out.println("Processing user: " + user.getUsername());
            //user.setChatList(); // Ensure each user initializes their ChatList
            System.out.println("ChatList created for user: " + user.getUsername());}
        } 

    
    public boolean verifyCredentials(String username, String password) {
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
    
    public UserAccount getUserAccount(String username) {
        for (UserAccount user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;  // Return null if no matching user is found
    }
    
    public UserAccount getAccount(String username) {
    	int userAccountIndex = 0;
    	for (int i = 0; i < userList.size(); i++) {
    		if (userList.get(i).getUsername().equals(username)) {
    			userAccountIndex = i;
    			break;
    		}
    	}
    	return userList.get(userAccountIndex);
    }

    public boolean addUser(String username, String password) {
        // Check if user already exists
        if (userCredentials.containsKey(username)) {
            return false; // Cannot add duplicate user
        }
        
        // Create new RegularUser and add to userList
        RegularUser newUser = new RegularUser(username, password);
        userList.add(newUser);
        
        // Append new user to file with ID and add to HashMap
        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            rewriteUserFile();
        	// writer.write(username + "," + password + "," + newUser.getID() + "\n");
            // writer.write(String.valueOf(UserAccount.getCount()) + "\n");  // Write the updated count
            userCredentials.put(username, password);
            
            return true;
        } catch (IOException e) {
            System.err.println("Error adding user: " + e.getMessage());
            userList.remove(newUser);  // Remove from list if file write fails
            return false; 
        }
    }
    

    public boolean removeUser(String username) {
        // Check if user exists before attempting removal
        if (!userCredentials.containsKey(username)) {
            return false;
        }
        
        // Remove from HashMap
        userCredentials.remove(username);
        
        // Remove from userList
        userList.removeIf(user -> user.getUsername().equals(username));
        
        // Update the file to match current state
        try {
            rewriteUserFile();
            return true;
        } catch (IOException e) {
            System.err.println("Error removing user: " + e.getMessage());
            // reload credentials when it fails
            loadUserCredentials();
            return false;
        }
    }
    

    // Helper method that rewrites the entire user credentials file
    // Used after removing a user to ensure file matches memory state
    private void rewriteUserFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            /*
        	for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                
                writer.newLine();
            }
            */
        	
        	for(UserAccount user : userList) {
        		writer.write(user.getUsername() + "," +
        				user.getPassword() + "," +
        				user.getID());
        		writer.newLine();
        	}
            
        	writer.write(String.valueOf(UserAccount.getCount()));
        	writer.newLine();
        }
    }
    
    public Set<String> getAllUsernames() {
        return new HashSet<>(userCredentials.keySet());
    }
    
    public boolean userExists(String username) {
        return userCredentials.containsKey(username);
    }
}