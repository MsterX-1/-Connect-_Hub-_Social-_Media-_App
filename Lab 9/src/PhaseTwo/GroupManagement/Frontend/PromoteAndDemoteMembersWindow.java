package PhaseTwo.GroupManagement.Frontend;

import CustomJPanels.PromoteAndDemotePanel;
import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;

public class PromoteAndDemoteMembersWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane memberScrollPane;
    private JPanel memberContainer;
    private PromoteAndDemoteMembersWindow promoteAndDemoteMembersWindow;
    public PromoteAndDemoteMembersWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager) {
        promoteAndDemoteMembersWindow = this;
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Promote And Demote Members");

        memberContainer.setLayout(new BoxLayout(memberContainer, BoxLayout.Y_AXIS));
        populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager,groupRoleDataManager);
        memberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        memberScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVisible(true);
    }
    public void populateMembersList(DataManager<Group> groupDataManager,String groupName,DataManager<User> userDataManager,DataManager<Profile> profileDataManager,DataManager<GroupRole> groupRoleDataManager) {
        // Simulate data for demonstration
        if(groupDataManager.getDataByName(groupName).getGroupMembers() == null)
            return;
        memberContainer.removeAll();
        for (int i = 0; i < groupDataManager.getDataByName(groupName).getGroupMembers().size(); i++) {
            String memberId = groupDataManager.getDataByName(groupName).getGroupMembers().get(i);
            if(!memberId.equals(groupRoleDataManager.getDataByName(groupName).getGroupCreator())){
                // Create a PostPanel for each post
                PromoteAndDemotePanel promoteAndDemotePanel = new PromoteAndDemotePanel(memberId,groupDataManager,userDataManager,groupName, promoteAndDemoteMembersWindow,profileDataManager,groupRoleDataManager);

                // Add padding and border to each PostPanel
                promoteAndDemotePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Add the PostPanel to the container
                memberContainer.add(promoteAndDemotePanel);
            }

        }

        // Revalidate and repaint the container to apply updates
        memberContainer.revalidate();
        memberContainer.repaint();
    }
    }

