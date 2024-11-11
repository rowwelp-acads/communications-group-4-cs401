package main.java;

import java.io.*;
import java.util.*;


public class UserManagement {
    // File path for storing user credentials
    private static final String USER_FILE = "useraccounts.txt";
    
    // In-memory storage of user credentials (username -> password mapping)
    private Map<String, String> userCredentials = new HashMap<>();
    
    // CONSTRUCTOR
    public UserManagement() {
        loadUserCredentials();
    }
    

    // Loads user credentials from the text file into the HashMap.
    // Each line in the file should be in the format: username,password
    private void loadUserCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Only process valid lines that have both username and password
                if (parts.length == 2) {
                    userCredentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading user credentials: " + e.getMessage());
        }
    }
    
    public boolean verifyCredentials(String username, String password) {
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
    

    public boolean addUser(String username, String password) {
        // Check if user already exists
        if (userCredentials.containsKey(username)) {
            return false; // Cannot add duplicate user
        }
        
        // Append new user to file and add to HashMap
        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            writer.write(username + "," + password + "\n");
            userCredentials.put(username, password);
            return true;
        } catch (IOException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }
    

    public boolean removeUser(String username) {
        // Check if user exists before attempting removal
        if (!userCredentials.containsKey(username)) {
            return false; // 
        }
        
        // Remove from HashMap
        userCredentials.remove(username);
        
        // Update the file to match hashmap
        try {
            rewriteUserFile();
            return true;
        } catch (IOException e) {
            System.err.println("Error removing user: " + e.getMessage());
            // reloads credentials when it fails
            loadUserCredentials();
            return false;
        }
    }
    

    // Helper method that rewrites the entire user credentials file
    // Used after removing a user to ensure file matches memory state
    private void rewriteUserFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        }
    }
    
    public Set<String> getAllUsernames() {
        return new HashSet<>(userCredentials.keySet());
    }
    
    public boolean userExists(String username) {
        return userCredentials.containsKey(username);
    }
}