package main.java.gui;

import java.awt.*;
import javax.swing.*;

public class UserList {
    String[] userListArray = {"Rowwel", "Kai", "Bryan", "John", "Johnny"};

    JFrame userListFrame = new JFrame("User List");
    JPanel userListPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton addUser = new JButton("Add User to Chat");
    JButton removeUser = new JButton("Remove User from Chat");

    public UserList() {
        // User List Panel
        JList<String> userList = new JList<>(userListArray);
        userListPanel.add(userList);
        userListPanel.setLayout(new GridLayout());

        // Button Panel
        buttonPanel.add(addUser);
        buttonPanel.add(removeUser);
        buttonPanel.setLayout(new GridLayout());

        // Add button listeners
        addUser.addActionListener(e -> {
            JDialog dialog = new JDialog(userListFrame, "Button Pressed", true); // true makes it modal
            dialog.setLayout(new FlowLayout());
            dialog.add(new JLabel("You pressed Add User button!"));
            
            // Add OK button to close dialog
            JButton okButton = new JButton("OK");
            // Pressing OK closes it
            okButton.addActionListener(event -> dialog.dispose());
            dialog.add(okButton);
            
            // Set dialog properties
            dialog.setSize(250, 100);
            dialog.setLocationRelativeTo(userListFrame);
            dialog.setVisible(true);
        });

        removeUser.addActionListener(e -> {
            JDialog dialog = new JDialog(userListFrame, "Button Pressed", true);
            dialog.setLayout(new FlowLayout());
            dialog.add(new JLabel("You pressed Remove User button!"));
            
            // Add OK button to close dialog
            JButton okButton = new JButton("OK");
            okButton.addActionListener(event -> dialog.dispose());
            dialog.add(okButton);
            
            // Set dialog properties
            dialog.setSize(250, 100);
            dialog.setLocationRelativeTo(userListFrame);
            dialog.setVisible(true);
        });

        // User List Frame
        userListFrame.add(userListPanel);
        userListFrame.add(buttonPanel);
        userListFrame.getContentPane().setLayout(new BoxLayout(userListFrame.getContentPane(), BoxLayout.Y_AXIS));
        
        // Frame settings
        userListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userListFrame.setSize(400, 300);
        userListFrame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        userListFrame.setVisible(visible);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserList window = new UserList();
            window.setVisible(true);
        });
    }
}