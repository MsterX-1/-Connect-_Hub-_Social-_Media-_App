package CustomJPanels.FriendPanels;


import Databases.DataManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.ProfileViewer.ProfileViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendPanel extends JPanel {

    public FriendPanel(String friendName, String friendImagePath, String userId, String friendId, DataManager<UserRelations> userRelationsDataManager , DataManager<User> userDataManager , DataManager<Profile> profileDataManager   ) {


        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps
        setPreferredSize(new Dimension(300, 90)); // Set preferred size for the panel

        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(friendImagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(friendName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        // Create buttons for Accept and Decline
        JButton viewProfile = new JButton("View profile");
        JButton removeFriend = new JButton("Remove Friend");
        JButton addFriend = new JButton("Add Friend");
        JButton blockUser = new JButton("Block");
        JButton unblockUser = new JButton("Unblock");

        buttonPanel.add(viewProfile);

        if (userRelationsDataManager.getDataById(userId).getFriendsList().contains(friendId))
            buttonPanel.add(removeFriend);
        else
            buttonPanel.add(addFriend);

        if (userRelationsDataManager.getDataById(userId).getBlockList().contains(friendId)) {
            buttonPanel.add(unblockUser);
        } else
            buttonPanel.add(blockUser);

        if(userRelationsDataManager.getDataById(userId).getPendingRequests().containsKey(friendId))
            addFriend.setText("Pending");

        // Add components to the main panel
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        viewProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileViewer(friendId,userDataManager,profileDataManager,userRelationsDataManager);
            }
        });
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove friend from user's friends list
                userRelationsDataManager.getDataById(userId).sendFriendRequest(friendId, userRelationsDataManager);

                //save to relation database
                userRelationsDataManager.saveData();


            }
        });

        removeFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove friend from user's friends list
                userRelationsDataManager.getDataById(userId).removeFriend(friendId, userRelationsDataManager);
                //save data to json
                userRelationsDataManager.saveData();


            }
        });
        blockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //block user
                userRelationsDataManager.getDataById(userId).blockUser(friendId,userRelationsDataManager);
                //save data to json
                userRelationsDataManager.saveData();


            }
        });
        unblockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //block user
                userRelationsDataManager.getDataById(userId).unblockUser(friendId,userRelationsDataManager);
                //save data to json
                userRelationsDataManager.saveData();


            }
        });
    }

}
