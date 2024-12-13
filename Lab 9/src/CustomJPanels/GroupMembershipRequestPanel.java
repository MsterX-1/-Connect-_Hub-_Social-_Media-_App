package CustomJPanels;

import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
import PhaseTwo.GroupManagement.Frontend.CheckMemberShipRequestWindow;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupMembershipRequestPanel extends JPanel {
    public GroupMembershipRequestPanel(String memberId, DataManager<Group> groupDataManager, DataManager<User>  userDataManager, String groupName, CheckMemberShipRequestWindow window, DataManager<Profile>  profileDataManager, DataManager<GroupRole>  groupRoleDataManager, DataManager<Notification> notificationDataManager) {
        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps

        setPreferredSize(new Dimension(600, 70)); // Set preferred size for the panel
        String memberIdName = userDataManager.getDataById(memberId).getUsername();
        String imagePath = profileDataManager.getDataById(memberId).getProfilePhotoPath();
        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(memberIdName);
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
                groupDataManager.getDataByName(groupName).getMembershipRequests().remove(memberId);
                groupDataManager.getDataByName(groupName).getGroupMembers().add(memberId);
                groupRoleDataManager.getDataByName(groupName).getGroupMembers().add(memberId);
                groupDataManager.saveData();
                groupRoleDataManager.saveData();
                notificationDataManager.getDataById(memberId).addacceptedInGroupNotification(groupName);
                notificationDataManager.saveData();
                JOptionPane.showMessageDialog(null, "Membership request accepted");
                //to refresh request window
                window.populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for Decline button
                groupDataManager.getDataByName(groupName).getMembershipRequests().remove(memberId);
                JOptionPane.showMessageDialog(null, "Membership request declined");
                groupDataManager.saveData();
                //to refresh request window
                window.populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
    }
    }

