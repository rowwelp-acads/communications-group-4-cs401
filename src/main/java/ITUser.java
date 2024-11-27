package main.java;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

//TODO: Write JUnit tests! @BryanMadrigal
public class ITUser extends UserAccount {
    // Attributes
	private int accessLevel;
	private UserManagement userManagement;
    
    // Serves as the handle to the Conversation HistoryLogs
    //private ConversationHistory convoLogs;
	
	// Constructor
    public ITUser(String username, String password) {
        super(username,password);
        this.accessLevel = 2;
    }
    
	// Constructor
    public ITUser(String username, String password, String id) {
        super(username,password,id);
        this.accessLevel = 2;
    }
    
    // Getters and Setters:
    public int getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    // Methods
    public void manageUsers() {
        UserManagement key = this.userManagement;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nUser Management Menu:");
            System.out.println("1. Get All Users");
            System.out.println("2. Get Specific User");
            System.out.println("3. Remove User");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");


            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Set<String> allUsers = key.getAllUsernames();
                    System.out.println("\nAll users in the system:");
                    for (String user : allUsers) {
                        System.out.println("- " + user);
                    }
                    break;

                case "2":
                    System.out.print("Enter username to find: ");
                    String userToFind = scanner.nextLine().trim();
                    if (key.userExists(userToFind)) {
                        System.out.println("User found: " + userToFind);
                    } else {
                        System.out.println("User not found: " + userToFind);
                    }
                    break;

                case "3":
                    System.out.print("Enter username to remove: ");
                    String userToRemove = scanner.nextLine().trim();
                    if (key.removeUser(userToRemove)) {
                        System.out.println("Successfully removed user: " + userToRemove);
                    } else {
                        System.out.println("Failed to remove user: " + userToRemove);
                    }
                    break;

                case "4":
                    System.out.println("Exiting user management...");
                    return;

                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }
    
    
    //TODO: The class below needs Conversation History done But this should use the handle above to 
    // get to conversation history and allow us to get the conversation history we want.
//    public List<ConversationHistory> viewAllConversationHistories() {
//    
//        return null;
//    }
    
    public UserAccount createUserAccount(String accountType, String username, String password) {
        // TODO: brainstorm modification to how we want the user to assighn profile attributes
    	if (accountType.equals("Regular")) {
        	return new RegularUser(username, password);
        }
        else if (accountType.equals("IT")) {
            return new ITUser(username, password);
        }
        return null;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [accessLevel=" + accessLevel + "]";
    }
}