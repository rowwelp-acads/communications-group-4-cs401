# Group 4: Communications

CS 401: Software Engineering

We are developing a communications system for use in an enterprise environment, utilizing tcp/ip for network connections and provisioning for expansion according to the organization's size.

# Key features:

• Real-time messaging between multiple users
• User authentication with IT Admin and Regular User roles  
• Chat room creation and participant management
• Persistent chat history storage and retrieval
• Administrative tools for user and system management

# Links

You may read more about our Software Requirements Specification here:
[Software Requirements Specification](https://github.com/rowwelp-acads/communications-group-4-cs401/blob/7a08868677f5d69d662e2dc536f509b804b8c8d9/Documentation/Group%204%20-%20SRS%20Document.pdf)

You may find our Design Specifications here:
[Design Document](https://github.com/rowwelp-acads/communications-group-4-cs401/blob/7a08868677f5d69d662e2dc536f509b804b8c8d9/Documentation/Group%204%20-%20Design%20Document.pdf)

# Enterprise Communications System
## Project Structure
This application consists of server-side components, client-side components, and GUI elements that work together to create an enterprise communications system.

### Server Components

#### Server
The main server that handles all client connections and data management.
```
Server
├── Fields:
│   ├── PORT: int (static final)
│   ├── clients: Map<String, ClientHandler> (static)
│   ├── chatListManager: ChatListManager (static)
│   ├── userDatabase: UserManagement
│   └── serverLog: ConversationLog
├── Methods:
│   ├── main(String[] args): void
│   ├── broadcastMessageToClient(Message message): void (static)
│   ├── addClient(String id, ClientHandler client): void (static)
│   ├── removeClient(String id): void (static)
│   └── getAccount(String username): UserAccount (static)
```

#### ClientHandler
Manages individual client connections and message processing.
```
ClientHandler (extends Thread)
├── Fields:
│   ├── socket: Socket
│   ├── clientId: String
│   ├── objectInputStream: ObjectInputStream
│   ├── objectOutputStream: ObjectOutputStream
│   └── user: UserAccount
├── Constructor:
│   └── ClientHandler(Socket socket)
├── Methods:
│   ├── run(): void
│   ├── getAccount(): UserAccount
│   └── getOut(): ObjectOutputStream
```

#### ChatListManager
Manages chat lists and their persistence.
```
ChatListManager
├── Fields:
│   ├── CHAT_LIST_FILE: String (static final)
│   └── chatLists: Map<String, List<Integer>>
├── Methods:
│   ├── loadChatListData(): void
│   ├── getUserChatList(String userID): List<Integer>
│   ├── addChatToList(String userID, int chatID): void
│   └── removeChatFromList(String userID, int chatID): void
```

#### ConversationLog
Manages conversation histories and their persistence.
```
ConversationLog (implements Serializable)
├── Fields:
│   └── allHistories: Map<Integer, ConversationHistory>
├── Methods:
│   ├── addConversationHistory(ConversationHistory newHistory): void
│   ├── addMessageToLog(int chatID, Message newMessage): void
│   └── getHistory(int chatID): ConversationHistory
```

#### UserManagement
Handles user account management and authentication.
```
UserManagement (implements Serializable)
├── Fields:
│   ├── USER_FILE: String (static final)
│   ├── userCredentials: Map<String, String>
│   └── userList: List<UserAccount>
├── Methods:
│   ├── loadUserCredentials(): void
│   ├── verifyCredentials(String username, String password): boolean
│   ├── addUser(String username, String password): boolean
│   └── removeUser(String username): boolean
```

### Client Components

#### Client
The main client application that users will run.
```
Client
├── Fields:
│   ├── socket: Socket
│   ├── isConnected: boolean
│   ├── guiHandler: GUIHandler
│   └── SERVER_IP: String (static final)
├── Constructor:
│   └── Client()
├── Methods:
│   ├── connectToServer(): void
│   ├── setupGUI(): void
│   └── main(String[] args): void (static)
```

#### Chat
Manages individual chat rooms and their participants.
```
Chat (implements Serializable)
├── Fields:
│   ├── uniqueID: int
│   ├── participants: List<UserAccount>
│   ├── history: ConversationHistory
│   └── creator: UserAccount
├── Constructor:
│   ├── Chat(UserAccount owner)
│   └── Chat(UserAccount owner, int newUniqueID)
├── Methods:
│   ├── addMessageToHistory(Message msgObj): void
│   ├── addParticipant(UserAccount user): Boolean
│   └── removeParticipant(String username): Boolean
```

#### ChatList
Manages a user's list of chats.
```
ChatList (implements Serializable)
├── Fields:
│   ├── ID: String
│   ├── owner: UserAccount
│   └── listOfChats: List<Chat>
├── Methods:
│   ├── createChat(): void
│   ├── removeChat(int chatUniqueID): void
│   └── updateListOfChatsServerSide(): void
```

#### ConversationHistory
Manages chat history for individual chats.
```
ConversationHistory (implements Serializable)
├── Fields:
│   ├── chatID: int
│   └── Messages: List<String>
├── Methods:
│   ├── addMessage(Message newMessage): void
│   ├── addMessage(String newMessage): void
│   └── getMessageList(): List<String>
```

#### Message
Handles message creation and transmission.
```
Message (implements Serializable)
├── Fields:
│   ├── sender: UserAccount
│   ├── content: String
│   ├── timestamp: Date
│   └── type: MESSAGETYPE
├── Methods:
│   ├── getSender(): UserAccount
│   ├── getContent(): String
│   └── getType(): MESSAGETYPE
```

#### UserAccount (Abstract)
Base class for user types.
```
UserAccount (implements Serializable)
├── Fields:
│   ├── chatList: ChatList
│   ├── id: String
│   ├── username: String
│   └── password: String
├── Methods:
│   ├── getAccessLevel(): int (abstract)
│   ├── updateList(): void
│   └── addMessage(Message msg): void
```

#### ITUser
Specialized user account for IT administrators.
```
ITUser (extends UserAccount)
├── Fields:
│   ├── accessLevel: int
│   └── userManagement: UserManagement
├── Methods:
│   ├── getAccessLevel(): int
│   ├── getUser(String username): UserAccount
│   └── removeUser(String username): boolean
```

#### RegularUser
Standard user account.
```
RegularUser (extends UserAccount)
├── Fields:
│   └── accessLevel: int
├── Methods:
│   ├── getAccessLevel(): int
│   └── setAccessLevel(int accessLevel): void
```

### GUI Components

#### MainHub
Central interface for user interaction.
```
MainHub (extends JFrame)
├── Fields:
│   ├── mainFrame: JFrame
│   ├── chatList: JList<String>
│   ├── owner: UserAccount
│   ├── msgIn: ObjectInputStream
│   └── msgOut: ObjectOutputStream
├── Methods:
│   ├── openMainHub(): void
│   ├── resetCurrentChatRoom(): void
│   └── exit(): void
```

#### ChatRoom
Individual chat interface.
```
ChatRoom
├── Fields:
│   ├── chatroomFrame: JFrame
│   ├── messageContainer: JList<String>
│   ├── chatID: int
│   └── user: UserAccount
├── Methods:
│   ├── updateChatRoom(): void
│   └── getChatID(): int
```

#### LogInFrame
Authentication interface.
```
LogInFrame (extends JFrame)
├── Fields:
│   ├── frame: JFrame
│   ├── usernameField: JTextField
│   ├── passwordField: JPasswordField
│   └── socket: Socket
├── Methods:
│   ├── openLoginWindow(): void
│   └── establishStream(): void
```

#### AdminPanel
IT administration interface.
```
AdminPanel
├── Fields:
│   ├── adminFrame: JFrame
│   └── msgOut: ObjectOutputStream
├── Methods:
│   ├── addAccount(): void
│   ├── removeAccount(): void
│   └── close(): void
```

#### UserList
Chat participant management interface.
```
UserList
├── Fields:
│   ├── userListFrame: JFrame
│   ├── userList: JList<String>
│   └── chatID: int
├── Methods:
│   ├── setVisible(boolean visible): void
│   └── loadParticipants(): void
```
