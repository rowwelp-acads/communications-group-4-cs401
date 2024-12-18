package main.java;

import java.io.Serializable;

// TODO: Write JUnit tests! @BryanMadrigal
public class RegularUser extends UserAccount implements Serializable{
	private static final long serialVersionUID = 3L;
    // Attributes
    private int accessLevel;

    // Constructor
    public RegularUser(String username, String password, String id) {
        // Calls the UserAccount Constructor
    	super(username, password, id);
    	
        this.accessLevel = 1; // Default access level
    } 
    
    public RegularUser(String username, String password, String id, boolean test) {
    	setUsername(username);
    	setPassword(password);
    	setUserId(id);
    	
        this.accessLevel = 1; // Default access level
    } 
    

    // Constructor
    public RegularUser(String username, String password) {
        // Calls the UserAccount Constructor
    	super(username, password);
        this.accessLevel = 1; // Default access level
    }

    // Returns the access level of the UserAccount
    public int getAccessLevel() {
        return accessLevel;
    }

    // Sets the access level of the UserAccount
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel; // Fix: Use the parameter
    }

    @Override
    public String toString() {
        // Call the toString() of the parent class and append extra data
        return super.toString() + " [accessLevel=" + accessLevel + "]";
    }
}