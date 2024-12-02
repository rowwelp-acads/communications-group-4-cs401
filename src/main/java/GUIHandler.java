package main.java;

import javax.swing.*;  // For creating GUI elements

import main.java.gui.LogInFrame;

import java.awt.*;    // For additional GUI components
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUIHandler {
    private JFrame loginFrame;    // Window for login
    private JFrame chatFrame;     // Window for chat
    
    // Reference to the client for communication
    private Client client;
    private Socket serverSocket;

    // Constructor - called when a new GUIHandler is created
    public GUIHandler(Client client) {
        this.client = client;
        
    }

    // Creates and shows the login interface
    // passes ObjectStreams along for sending messages
    public void setupLoginInterface(Socket socket) {
    	serverSocket = socket;
        /*
        IMPLEMENT:
        1. Create a window (JFrame) for login
        */
    	
    	LogInFrame logIn = new LogInFrame(socket);
    	//logIn.openLoginWindow();
    	
    	
    }

    // Creates and shows the chat interface
    public void setupChatInterface() {
        /*
        IMPLEMENT:
        1. Create a window (JFrame) for chat
        */
    }
}
