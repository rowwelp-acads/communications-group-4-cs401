import java.io.*;      // For input/output operations
import java.net.*;     // For network/socket operations
import java.util.UUID; // For generating unique IDs

// ClientHandler manages each individual client connection
// Extends Thread so each client runs on its own thread
public class ClientHandler extends Thread {
    // Socket for this specific client's connection
    private Socket socket;
    
    // Unique identifier for this client
    private String clientId;

    // Constructor 
    public ClientHandler(Socket socket) {
        this.socket = socket;
        // Generate a random, unique ID for this client
        this.clientId = UUID.randomUUID().toString();
    }


    public void run() {
        try {
            // Register this client with the server
            Server.addClient(clientId, this);
            System.out.println("New client connected - ID: " + clientId);
            
            // Keep the connection alive
            // Checks every second if the socket is still connected
            while (!socket.isClosed()) {
                Thread.sleep(1000);  // Wait for 1 second
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // When the connection ends (or if there's an error):
            // 1. Remove the client from the server's list
            Server.removeClient(clientId);
            try {
                // 2. Close the socket connection
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}