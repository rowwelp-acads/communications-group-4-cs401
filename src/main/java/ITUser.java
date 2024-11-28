package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ITUser extends UserAccount implements Serializable{
	private static final long serialVersionUID = 1L;
    private int accessLevel;
    private UserManagement userManagement;
    
    // Constructors
    public ITUser(String username, String password, UserManagement userManagement) {
        super(username, password);
        this.accessLevel = 2;
        this.userManagement = userManagement;
    }
    
    public ITUser(String username, String password, String id, UserManagement userManagement) {
        super(username, password, id);
        this.accessLevel = 2;
        this.userManagement = userManagement;
    }

    
    // Getters and Setters
    public int getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    // User Management Methods
    public UserAccount getUser(String username) {
        return userManagement.getUserAccount(username);
    }
    
    public List<UserAccount> getAllUsers() {
    	Set<String> usernames = userManagement.getAllUsernames();
        List<UserAccount> userList = new ArrayList<>();
        
        for(String username : usernames) {
            UserAccount user = userManagement.getUserAccount(username);
            if(user != null) {
                userList.add(user);
            }
        }
        
        return userList;
    }
    
    public boolean removeUser(String username) {
        return userManagement.removeUser(username);
    }
    
    public boolean newUser(String username, String password) {
        if (userManagement.userExists(username)) {
            return false;
        }
        
        return userManagement.addUser(username, password);
    }
    
    @Override
    public String toString() {
        return super.toString() + " [accessLevel=" + accessLevel + "]";
    }
}

//TODO: The class below needs Conversation History done But this should use the handle above to 
// get to conversation history and allow us to get the conversation history we want.
//public List<ConversationHistory> viewAllConversationHistories() {
//
//    return null;
//}