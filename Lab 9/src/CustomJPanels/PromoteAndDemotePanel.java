package CustomJPanels;

import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
import PhaseTwo.GroupManagement.Frontend.PromoteAndDemoteMembersWindow;
import PhaseTwo.GroupManagement.Frontend.RemoveMemberAsOwnerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromoteAndDemotePanel extends JPanel {
    public PromoteAndDemotePanel(String memberId, DataManager<Group> groupDataManager, DataManager<User> userDataManager, String groupName, PromoteAndDemoteMembersWindow window, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager) {
        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps
        setPreferredSize(new Dimension(600, 70)); // Set preferred size for the panel

        String memberName = userDataManager.getDataById(memberId).getUsername();
        String imagePath = profileDataManager.getDataById(memberId).getProfilePhotoPath();

        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(memberName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        JButton promoteButton = new JButton("Promote to Admin");
        JButton demoteButton = new JButton("Demote to Member");

        // Check if the member is an admin or not
        if (!isAdmin(memberId, groupName, groupRoleDataManager)) {
            buttonPanel.add(promoteButton); // Add promote button for non-admins
        } else {
            buttonPanel.add(demoteButton); // Add demote button for admins
        }

        // Add components to the main panel
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for promote button
                groupRoleDataManager.getDataByName(groupName).getGroupMembers().remove(memberId);
                groupRoleDataManager.getDataByName(groupName).getGroupAdmins().add(memberId);
                JOptionPane.showMessageDialog(PromoteAndDemotePanel.this, memberName+" Promoted to Admin");
                groupRoleDataManager.saveData();
                //to refresh request window
                window.populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
        demoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define action for demote button
                groupRoleDataManager.getDataByName(groupName).getGroupAdmins().remove(memberId);
                groupRoleDataManager.getDataByName(groupName).getGroupMembers().add(memberId);
                JOptionPane.showMessageDialog(PromoteAndDemotePanel.this, memberName+" Demoted to Member");
                groupRoleDataManager.saveData();
                //to refresh request window
                window.populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });

    }
    public boolean isAdmin(String userId , String groupName,DataManager<GroupRole> groupRoleDataManager) {
        for(int i =0; i<groupRoleDataManager.getDataByName(groupName).getGroupAdmins().size(); i++){
            if (userId.equals(groupRoleDataManager.getDataByName(groupName).getGroupAdmins().get(i))){
                return true;
            }

        }
        return false;
    }
    }


