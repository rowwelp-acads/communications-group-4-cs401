package main.java;

import java.io.*;      // For input/output operations
import java.net.*;     // For network/socket operations
import java.util.*;    // For using Map and other collections

public class Server {
    // Port number the server will listen on - must match what clients use
    private static final int PORT = 8888;
    
    // This Map keeps track of all connected clients
    // Key: client ID (String), Value: the client's handler
    // Collections.synchronizedMap makes it thread-safe (prevents errors when multiple clients connect/disconnect)
    private static Map<String, ClientHandler> clients = Collections.synchronizedMap(new HashMap<>());
    
    // Will hold all UserAccount's designated chatList 
    // KA
    private static List<ChatList> chatList;
    // NOVEMBER 25 KA
    private static ChatListManager chatListManager;
    
    
    private static UserManagement userDatabase;
    private static ConversationLog serverLog;
    
    public static void main(String[] args) {
        try {
        	chatList = new ArrayList<>();
        	chatListManager = new ChatListManager();
        	serverLog = new ConversationLog();
        	userDatabase = new UserManagement();
            // Create a ServerSocket that listens for client connections on the specified port
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);
            
            
            // Keep the server running forever
            while (true) {
                // .accept() waits for a client to connect
                // When a client connects, it returns a Socket for that connection
                Socket socket = serverSocket.accept();
                
                // Create a new handler for this client and start it on its own thread
                ClientHandler clientHandler = new ClientHandler(socket);
                // start client thread
                clientHandler.start();
            }
        } catch (IOException e) {
            // If something goes wrong, print the error
            e.printStackTrace();
        }
    }
    
    /*
     * method to broadcast messages
     * called in ClientHandler
     * to be moved into ClientHandler thread if time permits
     */
	public static void broadcastMessageToClient(Message message) throws IOException {
		synchronized (clients) {
			// grab chatID from message
			int chatID = message.getChatID();
			// iterate through all clientHandler and check their chatList for matching chatID
			for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
				ClientHandler clientHandler = entry.getValue(); // grab the ClientHandler
				if (clientHandler.getAccount().getChatList().verifyChat(chatID)) { // check matching ID
					try {
						ObjectOutputStream out = clientHandler.getOut(); // Get the output stream for each client
						out.writeObject(message); // Send the message to the client
						out.flush(); // Ensure the message is sent immediately
						System.out.println("Matching chat found, sending msg");
						serverLog.addMessageToLog(chatID, message); // add message to log

					} 
					catch (Exception ex) {
						System.out.println("Error sending message from Server to Client");
						ex.printStackTrace();
					}
				}
			}
		}

	}

	public static void writeHistory(int chatID) {
		serverLog.write(chatID);
	}
	
	public static void addUser(String username, String password) {
		userDatabase.addUser(username, password);
	}
	
	public static void removeUser(String username) {
		userDatabase.removeUser(username);
	}
    
    /*
     * method to verify credential
     * called in ClientHandler
     */
    public static String verify(Message message) {
    	// verifying credentials
		boolean verification = userDatabase.verifyCredentials(message.getUsername(), message.getPassword());
		// converted boolean of verifyCredientials into String for sending as message
		String verificationResult = String.valueOf(verification);
    	return verificationResult;
    }
    
    public static boolean clientExist(String username) {
    	if (clients.get(username) == null)
    		return false;
    	return true;
    }

    // Method to add a new client to our map of connected clients
    static void addClient(String id, ClientHandler client) {
        // synchronized ensures thread safety when modifying the clients map
    	// SYNCHRONIZED BLOCK
        synchronized(clients) {
            clients.put(id, client);
            System.out.println("Client added. Total clients: " + clients.size());
        }
    }
   

    // Method to remove a client when they disconnect
    static void removeClient(String id) {
        // synchronized ensures thread safety when modifying the clients map
    	// SYNCHRONIZED BLOCK
        synchronized(clients) {
            clients.remove(id);
            System.out.println("Client removed. Total clients: " + clients.size());
        }
    }
    
    public static UserAccount getAccount(String username) {
    	return userDatabase.getAccount(username);
    }
    
    // Method to get user's chat list IDs
    public static List<Integer> getUserChatList(String userID) {
        return chatListManager.getUserChatList(userID);
    }
    
    // Method to add a chat ID to user's list
    public static void addChatToList(String userID, int chatID) {
        chatListManager.addChatToList(userID, chatID);
    }
    
    // Method to remove a chat ID from user's list
    public static void removeChatFromList(String userID, int chatID) {
        chatListManager.removeChatFromList(userID, chatID);
    }
    
    // Gets the ChatListManager instance
    public static ChatListManager getChatListManager() {
        return chatListManager;
    }
    
    //get chathistory
    public static List<String> getChatHistory(int chatID) {
    	return serverLog.getHistory(chatID).getMessageList();
    }
    
    public static void addNewAccount(String username, String pass) {
    	userDatabase.addUser(username, pass);
    }
    
    public static void removeAccount(String username) {
    	userDatabase.removeUser(username);
    }
    
    public static Map<Integer, ConversationHistory> getLog(){
    	return serverLog.getHistories();
    }
    
}
