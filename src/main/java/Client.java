package main.java;

import java.io.*;      // For input/output operations
import java.net.*;     // For network/socket operations
import javax.swing.*;  // For creating GUI elements

import main.java.gui.LogInFrame;

/* 
 * Server's is expecting:
 * A user will send the message as a Message Object through the ChatRoom GUI, via the send message button. (ObjectOutputStream is passes along into ChatRoom)
 * Server will receive Message Object and broadcast it to all Clients (including back to sender for now) 
 * ((TODO) Still figuring out how to differentiate which clients is which UserAcount to send message to specifically) 
 * 
 * Once logged in and MainHub is started, MainHub will spawn background thread to continuously checking for incoming Messages from Server 
 * 
 * If a Message is received, check if the Message's chatID matches with user's list of chats
 * If matching then update the Message's content to that Chat's conversation history
 * 
 * ChatRoom GUI should be updating the conversation history in the background continuously to display new messages maybe?
 * Only opened ChatRoom GUI is updating
 * 
 * But MainHub will continuously update all chat's conversationHistory still
 * 
 * 
 * Changes: change the ChatRoom class in main.java to ChatList instead to more correctly reflect its function. (It has duplicate name with ChatRoom GUI too)
 * Setup LogInFrame class to connect to Server class for logging in
 * 
 * 
 * Current actual function: chatRoom can send a message to server and MainHub will receive it but only display it to console until we figure out how to implement displaying chat history
 * To test: run the Server and Client -> log-in -> select a chat in the chat list -> Open Chat -> type in text field -> send message -> message will be displayed in the Client's console
 * 
 * Design question: 
 * I'm assuming that Chat class is used for holding information of a chat (i.e. chat id, chat history)?
 * 
 */
public class Client {
    // Socket for connecting to the server
    private Socket socket;
    
    // Tracks if we're connected to the server
    private boolean isConnected;
    
    // Handles all GUI operations
    private GUIHandler guiHandler;
    
/////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// 
/////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// 
/////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// 
    // Add server IP as a constant
    //private static final String SERVER_IP = "134.154.45.100"; /////////////////////// Change this to your server's IP
    private static final String SERVER_IP = "134.154.41.208";
    // Rowell IS dedicated SERVER 
/////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// 
/////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// 
/////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// /////////////////////// 
    
    // Add PORT as a constant
    private static final int PORT = 8888;

    
    // Constructor
    public Client() {
        // Create the GUI handler first
        this.guiHandler = new GUIHandler(this);
        
        // Connect to the server
        connectToServer();
        //New Thread();
        // Set up the GUI (login screen)
        setupGUI();
       
    }
    // Sets up the initial GUI
    private void setupGUI() {
        // Tell the GUI handler to create the login interface
    	guiHandler.setupLoginInterface(socket);
    }

    // Connects to the server
    private void connectToServer() {
        try {
            System.out.println("Connecting to: " + SERVER_IP);
            
            // Create a socket connection to the server
            socket = new Socket(SERVER_IP, 8888);
            

    		
            // Mark that we're connected
            isConnected = true;
            System.out.println("Connected to server!");
        } catch (IOException e) {
            // If connection fails, print the error
            System.out.println("Connection refused. Server not running.");
        }
    }
    
    public static void main(String[] args) {
        // Start the client on the Event Dispatch Thread
        // This is important for GUI applications
        SwingUtilities.invokeLater(() -> {
            new Client();
        });
    }
    
    
}
