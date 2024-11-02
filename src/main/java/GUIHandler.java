import javax.swing.*;  // For creating GUI elements
import java.awt.*;    // For additional GUI components

public class GUIHandler {
    private JFrame loginFrame;    // Window for login
    private JFrame chatFrame;     // Window for chat
    
    // Reference to the client for communication
    private Client client;

    // Constructor - called when a new GUIHandler is created
    public GUIHandler(Client client) {
        this.client = client;
    }

    // Creates and shows the login interface
    public void setupLoginInterface() {
        /*
        IMPLEMENT:
        1. Create a window (JFrame) for login
        */
    }

    // Creates and shows the chat interface
    public void setupChatInterface() {
        /*
        IMPLEMENT:
        1. Create a window (JFrame) for chat
        */
    }
}