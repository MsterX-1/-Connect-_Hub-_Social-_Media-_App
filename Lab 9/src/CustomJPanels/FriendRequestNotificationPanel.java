package CustomJPanels;

import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.NotificationSystem.Backend.Notification;
import PhaseTwo.NotificationSystem.Frontend.NotificationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendRequestNotificationPanel extends JPanel{
    public FriendRequestNotificationPanel(DataManager<UserRelations> userRelationsDataManager, String userId, String senderId, NotificationWindow window, DataManager<User> userDataManager,DataManager<Profile> profileDataManager,DataManager<Notification> notificationDataManager){

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps

        setPreferredSize(new Dimension(600, 70)); // Set preferred size for the panel
        String senderName = userDataManager.getDataById(senderId).getUsername();
        String imagePath = profileDataManager.getDataById(senderId).getProfilePhotoPath();
        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(senderName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16));// Set font for the text
        JLabel text =new JLabel("New friend request");
        text.setFont(new Font("Arial", Font.BOLD, 12));

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        // Create buttons for Accept and Decline
        JButton acceptButton = new JButton("Accept");
        JButton declineButton = new JButton("Decline");

        buttonPanel.add(acceptButton);
        buttonPanel.add(declineButton);

        // Add components to the main panel
        add(text);
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for Accept button
                userRelationsDataManager.getDataById(userId).acceptFriendRequest(senderId,userRelationsDataManager);
                JOptionPane.showMessageDialog(null, "Accepted: " + senderName);
                userRelationsDataManager.saveData();
                notificationDataManager.getDataById(senderId).addacceptedUserRequestsNotification(userId);
                notificationDataManager.saveData();
                //to refresh request window
                window.populateRequestsList(userDataManager);

            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for Decline button
                userRelationsDataManager.getDataById(userId).declineFriendRequest(senderId , userRelationsDataManager);
                JOptionPane.showMessageDialog(null, "Declined: " + senderName);
                userRelationsDataManager.saveData();
                //to refresh request window
                window.populateRequestsList(userDataManager);
            }
        });
    }




    }

