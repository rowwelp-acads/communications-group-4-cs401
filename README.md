# Group 4: Communications

CS 401: Software Engineering

We are developing a communications system for use in an enterprise environment, utilizing tcp/ip for network connections and provisioning for expansion according to the organization's size.

# Links

You may read more about our Software Requirements Specification here:
[Software Requirements Specification](https://github.com/rowwelp-acads/communications-group-4-cs401/blob/7a08868677f5d69d662e2dc536f509b804b8c8d9/Documentation/Group%204%20-%20SRS%20Document.pdf)

You may find our Design Specifications here:
[Design Document](https://github.com/rowwelp-acads/communications-group-4-cs401/blob/7a08868677f5d69d662e2dc536f509b804b8c8d9/Documentation/Group%204%20-%20Design%20Document.pdf)

# Multi-Client Chat Application

## Project Structure
This application consists of four main components that work together to create a chat system.

### Class Overview

#### Server
The main server that handles all client connections.
```
Server
├── Fields:
│   ├── PORT: int (static final)
│   └── clients: Map<String, ClientHandler> (static)
├── Methods:
│   ├── main(String[] args): void
│   ├── addClient(String id, ClientHandler client): void (static)
│   └── removeClient(String id): void (static)
```

#### ClientHandler
Manages individual client connections on the server side.
```
ClientHandler (extends Thread)
├── Fields:
│   ├── socket: Socket
│   └── clientId: String
├── Constructor:
│   └── ClientHandler(Socket socket)
├── Methods:
│   └── run(): void
```

#### Client
The main client application that users will run.
```
Client
├── Fields:
│   ├── socket: Socket
│   ├── isConnected: boolean
│   └── guiHandler: GUIHandler
├── Constructor:
│   └── Client()
├── Methods:
│   ├── getLocalIP(): String
│   ├── setupGUI(): void
│   ├── connectToServer(): void
│   └── main(String[] args): void (static)
```

#### GUIHandler
Manages all graphical user interface components.
```
GUIHandler
├── Fields:
│   ├── loginFrame: JFrame
│   ├── chatFrame: JFrame
│   └── client: Client
├── Constructor:
│   └── GUIHandler(Client client)
├── Methods:
│   ├── setupLoginInterface(): void
│   └── setupChatInterface(): void
```