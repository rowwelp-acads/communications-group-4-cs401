package main.java;

import java.io.*; // For input/output operations

import java.net.*; // For network/socket operations
import java.util.List;
import java.util.UUID; // For generating unique IDs
/*
 * CHATLIST, CONVOHISTORY, CHAT, CONVOLOG
 */

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

	// Constructor
	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		// Generate a random, unique ID for this client
		// this.clientId = UUID.randomUUID().toString();

		// initialize I/O
		outputStream = socket.getOutputStream();
		objectOutputStream = new ObjectOutputStream(outputStream);

		inputStream = socket.getInputStream();
		objectInputStream = new ObjectInputStream(inputStream);

	}

	// getter for ID
	public UserAccount getAccount() {
		return user;
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

						// if user provided correct credentials, check if user already logged in
						if (verificationResult == "true") {
							if (Server.clientExist(objectIn.getUsername())) { // if user already exist, send back a
																				// failed message.
								Message returnMsgFailed = new Message("loggedIn");
								returnMsgFailed.setMessageType(MESSAGETYPE.LOGINTOSEND);
								objectOutputStream.writeObject(returnMsgFailed);
								objectOutputStream.flush();
							} else {
								// add client to hashmap using username
								clientId = objectIn.getUsername();
								Server.addClient(objectIn.getUsername(), this);
								System.out.println("New client connected - userName: " + objectIn.getUsername());
								// message to be return to sender if correct credentials and user not already
								// logged in
								Message returnVerification = new Message("true");
								returnVerification.setMessageType(MESSAGETYPE.LOGINTOSEND);
								// return log-in result
								objectOutputStream.writeObject(returnVerification);
								objectOutputStream.flush();
								// grab user account from userDatabase
								user = Server.getAccount(objectIn.getUsername());

								// return UserAccount to user for displaying chats
								objectOutputStream.writeObject(user);
								objectOutputStream.flush();
							}

						}
					}
					/*
					 * else if (objectIn.getType() == MESSAGETYPE.ADD_USER) {
					 * Server.addUser(objectIn.getUsername(), objectIn.getPassword()); } else if
					 * (objectIn.getType() == MESSAGETYPE.REMOVE_USER) {
					 * Server.removeUser(objectIn.getUsername()); }
					 */
					// send message request
					else if (objectIn.getType() == MESSAGETYPE.MESSAGETOSEND) {
						// hand message object back to server in order to execute sending to recipient's
						// ClientHandler
						// System.out.println(objectIn.getContent());
						Server.broadcastMessageToClient(objectIn);
						
					}

					// send chat list request
					else if (objectIn.getType() == MESSAGETYPE.GET_CHATLIST) {
						String userID = objectIn.getContent(); // Get userID from content

						// Get chat IDs from server's ChatListManager
						List<Integer> chatIDs = Server.getUserChatList(userID);

						// Create and send response
						Message response = new Message(chatIDs, MESSAGETYPE.CHATLIST_RESPONSE);
						objectOutputStream.writeObject(response);
						objectOutputStream.flush();
					}
					// send chat history request
					else if (objectIn.getType() == MESSAGETYPE.GETHISTORY) {
						int chatID = objectIn.getChatID();

						// Get chat IDs from server's ChatListManager
						List<String> chatHistory = Server.getChatHistory(chatID);

						// Create and send response
						Message response = new Message(MESSAGETYPE.SENDHISTORY, chatHistory);
						objectOutputStream.writeObject(response);
						objectOutputStream.flush();
					}
					// send chat creation request
					else if (objectIn.getType() == MESSAGETYPE.ADD_CHAT) {
						Server.addChatToList(objectIn.getSender().getID(), objectIn.getChatID());
						user = Server.getAccount(objectIn.getUsername());
						user.updateList();
						objectOutputStream.writeObject(user.getChatList());
						objectOutputStream.flush();
					}
					// send chat removal request
					else if (objectIn.getType() == MESSAGETYPE.REMOVE_CHAT) {
						Server.removeChatFromList(objectIn.getSender().getID(), objectIn.getChatID());
						user = Server.getAccount(objectIn.getUsername());
						user.updateList();
						objectOutputStream.writeObject(user.getChatList());
						objectOutputStream.flush();
					} else if (objectIn.getType() == MESSAGETYPE.ADD_ACCOUNT) {
						Server.addNewAccount(objectIn.getUsername(), objectIn.getPassword());
					} else if (objectIn.getType() == MESSAGETYPE.REMOVE_ACCOUNT) {
						Server.removeAccount(objectIn.getUsername());
					} else if (objectIn.getType() == MESSAGETYPE.GET_LOG) {
						AllRecord log = new AllRecord(Server.getLog());
						objectOutputStream.writeObject(log);
						objectOutputStream.flush();
					}
					// disconnect request
					else if (objectIn.getType() == MESSAGETYPE.DISCONNECT) {
						for (int i = 0; i < user.getChatList().getListOfChats().size(); i++) {
							Server.writeHistory(user.getChatList().getListOfChats().get(i).getID());
							Server.removeClient(objectIn.getUsername());
						}
						System.out.println("User: " + user.getUsername() + " logged out.");
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
				objectOutputStream.close();
				objectInputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
