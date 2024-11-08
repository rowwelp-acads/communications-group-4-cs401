package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConvoLog {
	
	JFrame conversationLogFrame = new JFrame("Conversation Log Frame");
	
	JPanel logContainer = new JPanel();
	JPanel buttonContainer = new JPanel();
	
	JButton viewHistoryButton = new JButton("View Conversation History");
	
	String testLog[] = {"From: A- Hi",
						"From: B- Greetings",
						"From: C- When do we meet?",
						"From: A- 5:00pm",
						"From: C- Cool.",
						"From: B- Sounds good.",
						"Log size test -----------------------------------------------------"};
	
	public ConvoLog() {
		
		JList convoList = new JList(testLog);
		logContainer.add(new JScrollPane(convoList));
		convoList.setEnabled(false);
		
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
		buttonContainer.add(viewHistoryButton);
		
		conversationLogFrame.setLayout(new BoxLayout(conversationLogFrame.getContentPane(), BoxLayout.Y_AXIS));
		conversationLogFrame.add(logContainer);
		conversationLogFrame.add(buttonContainer);
		
		EventListener event = new EventListener();
		viewHistoryButton.addActionListener(event);
		
		conversationLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		conversationLogFrame.pack();
		conversationLogFrame.setAlwaysOnTop(true);
		conversationLogFrame.setVisible(true);
		conversationLogFrame.setLocationRelativeTo(null);
	}
	
	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("View Conversation History")){
					//conversation history method
					JOptionPane.showMessageDialog(conversationLogFrame, "View history messsage button hit", "convo history method", JOptionPane.INFORMATION_MESSAGE);
	            }
		}
	}

	//test
	public static void main(String[] args) {
		new ConvoLog();
	}
	
}