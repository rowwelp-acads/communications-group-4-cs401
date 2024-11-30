package main.java;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Default Content
	
	private UserAccount sender;
	private String content;
	private Date timestamp;
	private MESSAGETYPE type;
	private List<UserAccount> participants;
	private int chatID;
	
	// Content for Log In Messages
	
	private String username;
	private String password;
	
	// Content for ChatList
	// NOVEMBER 28 KA
	
    private List<Integer> chatIDs;
    
    // content for chathistory
    private List<String> chatHistory;
	
	// CONSTRUCTORS
	// Regular Chat Messages
	public Message(String content, UserAccount sender, int chatID) {
		this.sender = sender;
		this.content = content;
		this.timestamp = new Date();
		this.type = MESSAGETYPE.MESSAGETOSEND;
		this.chatID = chatID;
	}
	
	// CONSTRUCTORS
	// added by Johnny
	// mostly for debugging for now
//	public Message(String content) {
//		this.content = content;
//		this.timestamp = new Date();
//	}
	
	// CONSTRUCTORS
	// Log In Messages
	public Message(String username, String password) {
		this.username = username;
		this.password = password;
		this.timestamp = new Date();
		this.type = MESSAGETYPE.LOGINTOSEND;
		this.content = "Login Request";
	}
	
	// CONSTRUCTORS
	// Chat List Request 
	// NOVEMBER 28 KA
	public Message(String userID) {
		this.content = userID;
		this.timestamp = new Date();
		this.type = MESSAGETYPE.GET_CHATLIST;
	}
	
	// CONSTRUCTORS
	// Chat List Response
	// NOVEMBER 28 KA
    public Message(List<Integer> chatIDs, MESSAGETYPE type) {
        this.chatIDs = chatIDs;
        this.timestamp = new Date();
        this.type = type;
    }
    
    //constructor
    //request chat history
    public Message(int chatID, MESSAGETYPE type) {
    	this.chatID = chatID;
    	this.type = type;
    }
    
    //send chat History
    public Message(MESSAGETYPE type, List<String> history) {
    	this.type = type;
    	this.chatHistory = history;
    }
	
	public UserAccount getSender() {
		return sender;
	}
	
	public String getContent() {
		return content;
	}
	
	public List<UserAccount> getParticipants() {
		return participants;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public MESSAGETYPE getType() {
		return type;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getChatID() {
		return chatID;
	}
	
	public boolean isChatMessage() {
		return type == MESSAGETYPE.MESSAGETOSEND;
	}
	
	public boolean isLoginMessage() {
		return type == MESSAGETYPE.LOGINTOSEND;
	}
	
	public void setMessageType(MESSAGETYPE type) {
		this.type = type;
	}
	
	// NOVEMBER 28 KA
    public List<Integer> getChatIDs() {
        return chatIDs;
    }
	
    // NOVEMBER 29 JH
    public List<String> getChatHistory() {
    	return chatHistory;
    }
	
	
}
