package main.java;
//TODO: Write JUnit tests! @BryanMadrigal
public class RegularUser extends UserAccount {
	// Attributes 
    private int accessLevel;
    
    // Constructor
    public RegularUser(String userId, String username, String email, String password, int accessLevel) {
    	// Calls the UserAccount Constructor
        super(userId, username, email, password);
        this.accessLevel = accessLevel;
    }
    
    
    // Returns the access level of the UserAccount
    public int getAccessLevel() {
        return accessLevel;
    }
    
    // Sets the access level of the UserAccount
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    @Override
    public String toString() {
    	// We can call the toString() of the parent class and then just append the extra data
        return super.toString() + " [accessLevel=" + accessLevel + "]";
    }
}