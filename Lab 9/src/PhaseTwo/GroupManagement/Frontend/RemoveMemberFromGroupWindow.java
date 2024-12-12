package PhaseTwo.GroupManagement.Frontend;

import CustomJPanels.RemoveMemberFromGroupPanel;
import CustomJPanels.RequestsPanel;
import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;

import javax.swing.*;

public class RemoveMemberFromGroupWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane memberScrollPane;
    private JPanel memberContainer;
    private RemoveMemberFromGroupWindow removeMemberFromGroupWindow;

    public RemoveMemberFromGroupWindow(String groupName,DataManager<Group> groupDataManager,DataManager<User> userDataManager,DataManager<Profile> profileDataManager) {
        removeMemberFromGroupWindow=this;
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Friend requests");

        memberContainer.setLayout(new BoxLayout(memberContainer, BoxLayout.Y_AXIS));
        populateMembersList(groupDataManager,groupName,userDataManager,profileDataManager);
        memberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        memberScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVisible(true);
    }
    public void populateMembersList(DataManager<Group> groupDataManager,String groupName,DataManager<User> userDataManager,DataManager<Profile> profileDataManager) {
        // Simulate data for demonstration
        if(groupDataManager.getDataByName(groupName).getGroupMembers() == null)
            return;
        memberContainer.removeAll();
        for (int i = 0; i < groupDataManager.getDataByName(groupName).getGroupMembers().size(); i++) {
            String memberId = groupDataManager.getDataByName(groupName).getGroupMembers().get(i);
            // Create a PostPanel for each post
            RemoveMemberFromGroupPanel removeMemberFromGroupPanel = new RemoveMemberFromGroupPanel(memberId,groupDataManager,userDataManager,groupName,removeMemberFromGroupWindow,profileDataManager);

            // Add padding and border to each PostPanel
            removeMemberFromGroupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            memberContainer.add(removeMemberFromGroupPanel);

        }

        // Revalidate and repaint the container to apply updates
        memberContainer.revalidate();
        memberContainer.repaint();
    }
}
