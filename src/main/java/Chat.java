package main.java;

import java.util.*;

public class Chat {
	private String chatID;
	private List<UserAccount> participants;
	private List<UserAccount> activeParticipants;
	private List<Message> messages;
	
	public Chat() {
		
	}
	
	public void addParticipant(String username) {
		// Implement it here...
		// Go through participant database
		// if username == UserAcount.getUsername()
		
		for (UserAccount participant : participants) {
            if (participant.getUsername().equals(username)) {
                System.out.println("User " + username + " is already a participant.");
                return;
            }
        }
		
		// Somehow call server's UserManagement to get the useraccount based on the userID
		// Use a Message object to send UserAccount over the network? Must somehow find a way 
		// to pass UserAccount to client or construct a new one based on a Message passed over the network.
		
		/* Something along these lines...
		UserAccount user = userManagement.getUserByUsername(username);
        if (user != null) {
            // Add the user to the participants list
            participants.add(user);
            System.out.println("User " + username + " added to the chat.");
        } else {
            System.out.println("User " + username + " does not exist.");
        }
		*/
		
		
	}
	public void removeParticipant(String username) {
		// Implement it here...
		
	    UserAccount userToRemove = null;
	    for (UserAccount user : participants) {
	        if (user.getUsername().equals(username)) {
	            userToRemove = user;
	            break;
	        }
	    }
	    
	    if (userToRemove != null) {
	        participants.remove(userToRemove);
	        activeParticipants.remove(userToRemove);
	        
	        String systemMessageContent = username + " has left the chat.";
	        Message systemMessage = new Message(systemMessageContent, null);
	        messages.add(systemMessage);
	        
	        System.out.println("User " + username + " removed from chat.");
	    } else {
	        System.out.println("User " + username + " not found in participants.");
	    }
		
	}
	public void sendMessage(Message message) {
		// Implement it here...
		
	}
	
	public List<Message> loadConversationHistory() {
		// Implement it here...
		// String filePath = chatID + ".txt";
		
		// For testing:
		String filePath = "conversationhistory.txt";
		
		List<Message> messages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3); // Split into at most 3 parts
                if (parts.length == 3) {
                    String sender = parts[0];       // First part: sender
                    String timestamp = parts[1];   // Second part: timestamp
                    String content = parts[2];     // Third part: full message content
                    
                    /*
                    This might change since right now the only things I have to be saved
                    in the conversationhistory text file is their username, time and date sent
                    and the message content.
                    
                    When loading the textfile not all userAccount info is stored on there, but
                    creating a Message requires a userAccount as a "sender". Thus a userAccount
                    must be created here based on the username stored in the file. I don't really
                    know how to deal with this entirely yet and I don't want to make major changes
                    to the way the program works without first consulting the team.
                     */
                    
                    UserAccount senderAccount = new UserAccount(sender, sender, "", "");
                    
                    // Create the Message object
                    Message message = new Message(content, senderAccount);
                    
                    messages.add(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
	}
}
