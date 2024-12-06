package Frontend.CustomPanels;

import Backend.FriendManager.FriendMangerWindow1;
import Backend.FriendManager.FriendRequestsWindow;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestsPanel extends JPanel {

    public RequestsPanel(String senderName, String imagePath, FriendMangerWindow1 friendMangerWindow1 , String currentUserId ,String senderId , FriendRequestsWindow window , UserDatabase userDatabase ) {
        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps

        setPreferredSize(new Dimension(600, 70)); // Set preferred size for the panel

        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(senderName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        // Create buttons for Accept and Decline
        JButton acceptButton = new JButton("Accept");
        JButton declineButton = new JButton("Decline");
        buttonPanel.add(acceptButton);
        buttonPanel.add(declineButton);

        // Add components to the main panel
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for Accept button
                friendMangerWindow1.acceptFriendRequest(currentUserId , senderId);
                JOptionPane.showMessageDialog(RequestsPanel.this, "Accepted: " + senderName);
                window.populateRequestsList(userDatabase);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for Decline button
                friendMangerWindow1.declineFriendRequest(currentUserId , senderId);
                JOptionPane.showMessageDialog(RequestsPanel.this, "Declined: " + senderName);
                window.populateRequestsList(userDatabase);
                // Add logic here for friendMangerWindow1.declineFriendRequest() if applicable
            }
        });
    }
}
