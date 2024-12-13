package CustomJPanels.GroupPanels;

import Databases.DataManager;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
import PhaseTwo.GroupManagement.Frontend.GroupWindow;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupPanel extends JPanel {
    public GroupPanel(String groupName, String groupImagePath, String userId, DataManager<Group> groupDataManager, DataManager<GroupRole> groupRoleDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, Newsfeed newsfeed, DataManager<Notification> notificationDataManager) {


        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps
        setPreferredSize(new Dimension(300, 70)); // Set preferred size for the panel

        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(groupImagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(groupName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        // Create buttons for Accept and Decline
        JButton viewGroup = new JButton("View Group");
        JButton leaveGroup = new JButton("Leave Group");
        buttonPanel.add(viewGroup);


        // Check if the user is a member of the group and if their role is normal
        if (groupDataManager.getDataByName(groupName).getGroupMembers().contains(userId) &&
                groupRoleDataManager.getDataByName(groupName).getGroupMembers().contains(userId) &&
                !groupRoleDataManager.getDataByName(groupName).getGroupAdmins().contains(userId) &&
                !groupRoleDataManager.getDataByName(groupName).getGroupCreator().contains(userId)) {
            // User is a normal member, show the Leave Group button
            buttonPanel.add(leaveGroup);
        }


        // Add components to the main panel
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        viewGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GroupWindow(groupName, groupDataManager, userDataManager, profileDataManager, groupRoleDataManager, userId, newsfeed,notificationDataManager);
                newsfeed.setVisible(false);
            }
        });

        leaveGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to Leave the group?", "Confirm Leaving the Group",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    // Logic for leaving from the group
                    groupDataManager.getDataByName(groupName).getGroupMembers().remove(userId);
                    groupRoleDataManager.getDataByName(groupName).getGroupMembers().remove(userId);
                    groupDataManager.saveData();
                    groupRoleDataManager.saveData();


                    JOptionPane.showMessageDialog(null, "You Left the Group!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Group Leaving Canceled", "Canceled", JOptionPane.ERROR_MESSAGE);
                }
            }


        });

    }
}

