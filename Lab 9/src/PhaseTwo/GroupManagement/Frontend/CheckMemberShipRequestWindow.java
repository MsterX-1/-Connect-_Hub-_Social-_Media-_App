package PhaseTwo.GroupManagement.Frontend;

import CustomJPanels.GroupMembershipRequestPanel;
import CustomJPanels.RemoveMemberAsOwnerPanel;
import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;

public class CheckMemberShipRequestWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane memberScrollPane;
    private JPanel memberContainer;
    private CheckMemberShipRequestWindow checkMemberShipRequestWindow;
    private DataManager<Notification> notificationDataManager;
    public CheckMemberShipRequestWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager, DataManager<Notification> notificationDataManager) {
        checkMemberShipRequestWindow =this;
        this.notificationDataManager = notificationDataManager;
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Check Requests");

        memberContainer.setLayout(new BoxLayout(memberContainer, BoxLayout.Y_AXIS));
        populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager,groupRoleDataManager);
        memberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        memberScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVisible(true);
    }
    public void populateMembersList(DataManager<Group> groupDataManager, String groupName, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager) {
        // Simulate data for demonstration
        if(groupDataManager.getDataByName(groupName).getGroupMembers() == null)
            return;
        memberContainer.removeAll();
        for (int i = 0; i < groupDataManager.getDataByName(groupName).getMembershipRequests().size(); i++) {
            String memberId = groupDataManager.getDataByName(groupName).getMembershipRequests().get(i);
                // Create a PostPanel for each post
                GroupMembershipRequestPanel groupMembershipRequestPanel = new GroupMembershipRequestPanel(memberId,groupDataManager,userDataManager,groupName, checkMemberShipRequestWindow,profileDataManager,groupRoleDataManager,notificationDataManager);

                // Add padding and border to each PostPanel
            groupMembershipRequestPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Add the PostPanel to the container
                memberContainer.add(groupMembershipRequestPanel);


        }

        // Revalidate and repaint the container to apply updates
        memberContainer.revalidate();
        memberContainer.repaint();
    }
    }

