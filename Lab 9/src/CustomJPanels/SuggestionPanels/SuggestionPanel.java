package CustomJPanels.SuggestionPanels;


import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionPanel extends JPanel {

    public SuggestionPanel(String suggestedUserName, String suggestedUserImagePath, String userId, String suggestedUserId, DataManager<UserRelations> userRelationsDataManager ) {

        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps
        setPreferredSize(new Dimension(300, 70)); // Set preferred size for the panel

        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(suggestedUserImagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(suggestedUserName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        // Create buttons for Accept and Decline
        JButton viewProfile = new JButton("View profile");
        JButton addFriend = new JButton("add Friend");
        JButton blockUser = new JButton("Block");
        JButton unblockUser = new JButton("Unblock");


        buttonPanel.add(viewProfile);
        buttonPanel.add(addFriend);
        if (userRelationsDataManager.getDataById(userId).getBlockList().contains(suggestedUserId)) {
            buttonPanel.add(unblockUser);
        } else
            buttonPanel.add(blockUser);

        // Add components to the main panel
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        viewProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new UserProfileWindow();
            }
        });

        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove friend from user's friends list
                userRelationsDataManager.getDataById(userId).sendFriendRequest(suggestedUserId, userRelationsDataManager);
                //refresh panel check requestPanel
                userRelationsDataManager.saveData();
            }
        });
        blockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //block user
                userRelationsDataManager.getDataById(userId).blockUser(suggestedUserId);
                //save data to json
                userRelationsDataManager.saveData();


            }
        });
        unblockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //block user
                userRelationsDataManager.getDataById(userId).unblockUser(suggestedUserId);
                //save data to json
                userRelationsDataManager.saveData();


            }
        });
    }

}
