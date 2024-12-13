package CustomJPanels.GroupPanels;

import Databases.DataManager;
import Interfaces.UIManager;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;
import java.util.ArrayList;


public class GroupUIManager {
    private String userId;
    private DataManager<Group> groupDataManager;
    private DataManager<GroupRole> groupRoleDataManager;
    private DataManager<Profile> profileDataManager;
    private  DataManager<User> userDataManager;
    private Newsfeed newsfeed;

    public GroupUIManager(String userId, DataManager<Group> groupDataManager, DataManager<GroupRole> groupRoleDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, Newsfeed newsfeed) {
        this.userId = userId;
        this.groupDataManager = groupDataManager;
        this.groupRoleDataManager = groupRoleDataManager;
        this.userDataManager = userDataManager;
        this.profileDataManager = profileDataManager;
        this.newsfeed = newsfeed;
    }

    public void refreshList(JPanel groupContainer, JScrollPane groupScrollPane) {
        groupContainer.setLayout(new BoxLayout(groupContainer, BoxLayout.Y_AXIS));
        populateList(groupContainer);
        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        groupScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void populateList(JPanel groupContainer) {
        groupContainer.removeAll();
        // Simulate data for demonstration
        if(groupDataManager.getAllData() == null)
            return;
        for (int i = 0; i < groupDataManager.getAllData().size(); i++) {

            String groupName = groupDataManager.getAllData().get(i).getGroupName();
            String groupImagePath = groupDataManager.getAllData().get(i).getGroupPhotoPath();
            if(groupDataManager.getDataByName(groupName).getGroupMembers().contains(userId)){

                // Create a group Panel for each post
                GroupPanel groupPanel = new GroupPanel(groupName, groupImagePath,userId, groupDataManager,groupRoleDataManager,userDataManager,profileDataManager,newsfeed);

                // Add padding and border to each PostPanel
                groupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Add the PostPanel to the container
                groupContainer.add(groupPanel);
            }



        }

        // Revalidate and repaint the container to apply updates
        groupContainer.revalidate();
        groupContainer.repaint();
    }
}
