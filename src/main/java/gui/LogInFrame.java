package main.java.gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.MESSAGETYPE;
import main.java.Message;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class LogInFrame extends JFrame{

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    
    private Socket socket;
    private OutputStream out;
    private ObjectOutputStream msgOut;
    private InputStream in;
    private ObjectInputStream msgIn;
    private MainHub mainHub;
    
    public LogInFrame() {
    	openLoginWindow();
    }
    
    public LogInFrame(Socket socket) {
    	this.socket = socket;
    	establishStream();
   
    	openLoginWindow(); 
    }
    
    private void establishStream() {
    	try {
			out = socket.getOutputStream();
			msgOut = new ObjectOutputStream(out);
			
			in = socket.getInputStream();
			msgIn = new ObjectInputStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void openLoginWindow() {
    	LogInFrameUI();
    }

    private void LogInFrameUI() {
        // Create the main frame
    	frame = new JFrame("Log-In Frame");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);			// Set the size of the frame on open
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
        //System.out.println("Here");
        // Main Panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);							// Set the background of the mainPanel
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));			// Set the border of the panels inside of the mainPanel
        frame.setMaximumSize(new Dimension(Integer.MAX_VALUE,600));		//Set the max size of the Panel

//     // Loads img buttons into the Jframe that will stored inside of a JscrollPane
//    private void loadImgButtons(final JPanel imgPanel) {
//    		// Elements of the new Panel
//    		String imgPath;
//    		JPanel imagePanel = imgPanel;
//
//    		// Load images into the jFrame
//    		for (int i = 0; i < dvdNames.size(); i++) {
//    			
//    			String dvdName = dvdNames.get(i);	
//    			String dvdRating = dvdRatings.get(i);
//    			String dvdRuntime = dvdRuntimes.get(i);
//    			imgPath = dvdNames.get(i) + ".jpg";
//
//    			// Create a File Object so we can make sure that the image file exists in the root dir.
//    			File imgFile = new File(imgPath);
//    			
//    			// If the file does not exist, we link the dvd to any of the default images available. 
//    			if (!imgFile.exists()) {
//    				imgPath = "unkownRating.jpg";
//    			}
//
//    			// Create the Content that will be stored inside of the imagePanel.
//    			// Load the image from the file path.
//    			ImageIcon dvdImgIcon = new ImageIcon(imgPath);
//    			// Get the image from the icon.
//    			Image dvdImg = dvdImgIcon.getImage();
//    			//Scale the image (ENSURES ALL IMAGES ARE THE SAME SIZE)
//    			dvdImg = dvdImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//    			// Create a new ImageIcon with the scaled image
//    			ImageIcon scaledImg = new ImageIcon(dvdImg);
//    			
//    			//Create the Content that will be placed inside of the imagePanel
//    			JLabel imageLabel = new JLabel(scaledImg);					// Will allow us to make the image a button
//    			JLabel imageName = new JLabel(dvdName);						// Will be placed beside the image
//    			//Place the imageLabel inside of a JButton
//    			JButton imgButton = new JButton(imageLabel.getIcon());		// Puts the imageLabel inside of the imgButton JButton 
//    			
//    			
//
//    			imgButton.addActionListener(new ActionListener() {
//    				public void actionPerformed(ActionEvent e) {
//    					// Creating new Frame
//    					JFrame DVDContentFrame = new JFrame(dvdName);
//    					DVDContentFrame.setSize(300, 300);
//    					DVDContentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    					DVDContentFrame.setLocationRelativeTo(imgPanel); // Center on screen
//    					
//    					// Create new File Panel
//    					JPanel FilePanel = new JPanel();
//    					FilePanel.setLayout(new GridLayout(0, 1));
//
//    					// Create Content for the Panel
//    					JLabel title = new JLabel("Title: " + dvdName);
//    					JLabel rating = new JLabel("Rating: " + dvdRating);
//    					JLabel runtime = new JLabel("Runtime: " + dvdRuntime);// + "minutes");
//    					
//    					/*
//    					 * Any Buttons to modify the Current DVD must be placed here 
//    					 * Followed by actionListeners for those Buttons (with enough time)
//    					 */
//
//    					// Add the content to the FilePanel.
//    					FilePanel.add(title);
//    					FilePanel.add(rating);
//    					FilePanel.add(runtime);
//
//    					// Add the Panel to the Content Frame.
//    					DVDContentFrame.add(FilePanel);
//    					DVDContentFrame.setVisible(true);
//    				}
//
//    			});
//    			//Add the components to the imagePanel
//    			imagePanel.add(imgButton);
//    			imagePanel.add(imageName);
//    		}
//    	}
        
        
        
        /*
         * Input Panel:
         * Holds 2 sub-panels usernameSubPanel & passwordSubPanel. 
         * Both panels will contain twp components the JLabel text, and the 
         * JTextField for usernames and JPasswordField for passwords.
         */
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        // Create JLabels that will be held in the according SubPanel	-	-	-	-	-
        JLabel usernameLabel = new JLabel("Username");	// Username Label
        JLabel passwordLabel = new JLabel("Password");	// Password Label
        
        // Create textField for Usernames and Passwords	-	-	-	-	-	-	-
        // Username TextField
        //usernameField = new JTextField("Rowl");
        usernameField = new JTextField("");
        //usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,25));
        // Password TextField
        //passwordField = new JPasswordField("pass123");
        passwordField = new JPasswordField("");
        //passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE,25));
        
        // Create SubPanels to hold the Lables and textFields
        JPanel img = new JPanel();
        String imgPath = "comcom.png";
        File imgFile = new File(imgPath);
        ImageIcon dvdImgIcon = new ImageIcon(imgPath);
        Image dvdImg = dvdImgIcon.getImage();
        dvdImg = dvdImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledImg = new ImageIcon(dvdImg);
        img.add(new JLabel(scaledImg));
        
        
        // Username SubPanel
        JPanel usernameSubPanel = new JPanel();
        usernameSubPanel.setLayout(new BoxLayout(usernameSubPanel, BoxLayout.Y_AXIS));
        usernameSubPanel.setBorder(new EmptyBorder(25, 40, 20, 40));
        // Password SubPanel
        JPanel passwordSubPanel = new JPanel();
        passwordSubPanel.setLayout(new BoxLayout(passwordSubPanel, BoxLayout.Y_AXIS));
        passwordSubPanel.setBorder(new EmptyBorder(10, 40, 40, 40));
        
        // Add the Components to the SubPanels
        usernameSubPanel.add(usernameLabel);
        usernameSubPanel.add(usernameField);
        passwordSubPanel.add(passwordLabel);
        passwordSubPanel.add(passwordField);
        
        // Add the SubPanels to the inputPanel
        inputPanel.add(img);
        inputPanel.add(usernameSubPanel);
        inputPanel.add(passwordSubPanel);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 4, 4));
        buttonPanel.setBorder(new EmptyBorder(10, 20, 40, 20));

        // Create Login and Cancel buttons
        loginButton = new JButton("Log In");        
        cancelButton = new JButton("Cancel");
        
        // Add actions to Login & Cancel buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle login
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Insert login logic here
                JOptionPane.showMessageDialog(frame, "Login clicked: [" + username + "] Entered the password [" + password + "]");
                
                // Server connection for log-in
                // send a Msg obj then wait for a return Msg obj from server
                // check if returned Msg's username is "true" (placeholder for verification result)
                try {
                	msgOut.writeObject(new Message(username, password)); // sending message to server
                	msgOut.flush();
                	
                	Message serverRespond = (Message) msgIn.readObject(); // return message from server
                	//System.out.println(serverRespond.getContent());
                	if (serverRespond.getType() == MESSAGETYPE.LOGINTOSEND && serverRespond.getContent().equals("true")) { // check return message
						mainHub = new MainHub(socket, msgIn, msgOut);
						mainHub.openMainHub();
						//frame.setVisible(false);
						/*
						mainHub.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								try {
									InetAddress ipAddress = socket.getInetAddress();
									int port = socket.getPort();
									
									msgIn.close();
									msgOut.close();
									socket.close();
									
									socket = new Socket(ipAddress, port);
									establishStream();
									
									SwingUtilities.invokeLater(()->{
										System.out.println("test");
										frame.setVisible(true);
										frame.toFront();
									});
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
						});
						*/
						frame.dispose();
                	}
                	else if (serverRespond.getType() == MESSAGETYPE.LOGINTOSEND && serverRespond.getContent().equals("loggedIn")) {
                		JOptionPane.showMessageDialog(frame, "Error: Log-in failed. User already logged in. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); // if return message is "LoggedIn"
                	}
                	else {
                		JOptionPane.showMessageDialog(frame, "Error: Log-in failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); // if return message is "false"
                	}
                }
                catch (Exception ex) {
                	System.out.println("Sending/receiving login msg error at line 108 LogInFrame, server not connected.");
                	ex.printStackTrace();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        // Add panels to the mainpanel
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        // Add mainpanel to the frame
        frame.add(mainPanel);
        //System.out.println("Here");
    }

//    public static void main(String[] args) {
//    	LogInFrame logIn = new LogInFrame();
//    	logIn.openLoginWindow();
//    }
}
