package main.java;

import java.io.*;      // For input/output operations
import java.net.*;     // For network/socket operations
import javax.swing.*;  // For creating GUI elements

import main.java.gui.LogInFrame;

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
    private static final String SERVER_IP = "134.154.44.242"; /////////////////////// Change this to your server's IP
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
        
        // Set up the GUI (login screen)
        setupGUI();
        
        // Connect to the server
        connectToServer();
    }
    // Sets up the initial GUI
    private void setupGUI() {
        // Tell the GUI handler to create the login interface
    	guiHandler.setupLoginInterface();
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
            e.printStackTrace();
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
