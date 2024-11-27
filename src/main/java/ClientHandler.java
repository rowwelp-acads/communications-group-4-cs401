package main.java;

import java.io.*; // For input/output operations

import java.net.*; // For network/socket operations
import java.util.UUID; // For generating unique IDs

// ClientHandler manages each individual client connection
// Extends Thread so each client runs on its own thread
public class ClientHandler extends Thread {
	// Socket for this specific client's connection
	private Socket socket;

	// Unique identifier for this client
	private String clientId;

	// attributes for I/O
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	private UserAccount user;
	private ChatList userChatList;

	// Constructor
	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		// Generate a random, unique ID for this client
		//this.clientId = UUID.randomUUID().toString();

		// initialize I/O
		outputStream = socket.getOutputStream();
		objectOutputStream = new ObjectOutputStream(outputStream);

		inputStream = socket.getInputStream();
		objectInputStream = new ObjectInputStream(inputStream);

	}

	// getter for ID
	public String getID() {
		return clientId;
	}

	// getter for ObjectOutputStream 
	public ObjectOutputStream getOut() {
		return objectOutputStream;
	}

	public void run() {
		try {			
			// loop to keeps receiving and sending messages
			while (true) {
				try {
					// read Message object from inputStream
					var objectIn = (Message) objectInputStream.readObject();
					// check what type is message then do the corresponding request
					// login request
					if (objectIn.getType() == MESSAGETYPE.LOGINTOSEND) {
						// string result of verification
						String verificationResult = Server.verify(objectIn);
						// message to be return to sender
						Message returnVerification = new Message(verificationResult);
						returnVerification.setMessageType(MESSAGETYPE.LOGINTOSEND);
						// return message but should return just true for now for debugging first
						//objectOutputStream.writeObject(returnVerification);
						System.out.println("log in success");
						objectOutputStream.writeObject(new Message("true", null));
						objectOutputStream.flush();
						
						verificationResult = "true"; // for debugging only, remove in final version
						// if user is logged in with correct credentials
						if (verificationResult == "true") {
							// add client to hashmap using username
							clientId = objectIn.getUsername();
							Server.addClient(objectIn.getUsername(), this);
							System.out.println("New client connected - userName: " + objectIn.getUsername());
							
							// TODO: check if client is IT
							// for simplicity, a separated method for checking a separated file with list of IT username in it.
							// only check if user exist first.
							// String itVerify = Server.verifyIT(objectIn);
							
							// initialize empty userAccount
							user = new UserAccount();
							// open UserAccount using username
							// UserAccount will open file and grab chatList and chatLog( + chatHistory in it) and saved to its attributes
							user.openUserAccount(objectIn.getUsername());
							
							// return UserAccount to user for displaying chats
							objectOutputStream.writeObject(user);
							objectOutputStream.flush();
							
						}
					}
					// send message request
					else if (objectIn.getType() == MESSAGETYPE.MESSAGETOSEND) {
						// hand message object back to server in order to execute sending to recipient's ClientHandler
						//System.out.println(objectIn.getContent());
						Server.broadcastMessageToClient(objectIn);
						
					}
					// disconnect request
					else if (objectIn.getType() == MESSAGETYPE.DISCONNECT) {
						// send disconnect message back to client
						//Message disconnect = new Message("Disconnecting", objectIn.getSender(), objectIn.getRecipients());
						//disconnect.setMessageType(MESSAGETYPE.DISCONNECT);
						//objectOutputStream.writeObject(disconnect);
						
						//objectOutputStream.flush();
						
						// write chat history and chat list down
						break;
					}
				} catch (Exception e) {
					System.out.println("Server error: ClientHandler message handling");
					break;
				}
			}

			// Keep the connection alive
			// Checks every second if the socket is still connected
			// while (!socket.isClosed()) {
			// Thread.sleep(1000); // Wait for 1 second
			// }
			// } catch (InterruptedException e) {
			// e.printStackTrace();
		} finally {
			// When the connection ends (or if there's an error):
			// 1. Remove the client from the server's list
			Server.removeClient(clientId);
			try {
				// 2. Close the socket connection
				socket.close();
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
