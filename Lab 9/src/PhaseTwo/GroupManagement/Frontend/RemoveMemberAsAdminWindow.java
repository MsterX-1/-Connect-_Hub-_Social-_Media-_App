package PhaseTwo.GroupManagement.Frontend;

import CustomJPanels.RemoveMemberAsAdminPanel;
import CustomJPanels.RemoveMemberAsOwnerPanel;
import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;
import java.util.List;

public class RemoveMemberAsAdminWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane memberScrollPane;
    private JPanel memberContainer;
    private RemoveMemberAsAdminWindow removeMemberAsAdminWindow;

    public RemoveMemberAsAdminWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager) {
        removeMemberAsAdminWindow = this;
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Remove Members");

        memberContainer.setLayout(new BoxLayout(memberContainer, BoxLayout.Y_AXIS));
        populateMembersList(groupDataManager, groupName, userDataManager, profileDataManager, groupRoleDataManager);
        memberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        memberScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVisible(true);
    }

    public void populateMembersList(DataManager<Group> groupDataManager, String groupName, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager) {
        // Check if the group has members
        if (groupDataManager.getDataByName(groupName).getGroupMembers() == null) {
            return;
        }

        // Clear the member container
        memberContainer.removeAll();

        // Retrieve the group data
        Group group = groupDataManager.getDataByName(groupName);
        String groupCreator = groupRoleDataManager.getDataByName(groupName).getGroupCreator();
        List<String> groupAdmins = groupRoleDataManager.getDataByName(groupName).getGroupAdmins();

        // Iterate through group members
        for (String memberId : group.getGroupMembers()) {
            // Skip the group creator and admins
            if (!memberId.equals(groupCreator) && !groupAdmins.contains(memberId)) {
                // Create a RemoveMemberAsAdminPanel for each eligible member
                RemoveMemberAsAdminPanel removeMemberAsAdminPanel = new RemoveMemberAsAdminPanel(memberId, groupDataManager, userDataManager, groupName, removeMemberAsAdminWindow, profileDataManager, groupRoleDataManager);

                // Add padding and border to each panel
                removeMemberAsAdminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Add the panel to the container
                memberContainer.add(removeMemberAsAdminPanel);
            }
        }

        // Revalidate and repaint the container to apply updates
        memberContainer.revalidate();
        memberContainer.repaint();
    }
}
