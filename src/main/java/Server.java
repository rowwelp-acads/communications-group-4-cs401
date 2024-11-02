package src.main.java;

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
    
    public static void main(String[] args) {
        try {
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
                clientHandler.start();
            }
        } catch (IOException e) {
            // If something goes wrong, print the error
            e.printStackTrace();
        }
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
}
