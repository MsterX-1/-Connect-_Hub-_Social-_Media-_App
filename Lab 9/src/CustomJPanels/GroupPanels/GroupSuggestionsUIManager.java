package CustomJPanels.GroupPanels;

import Databases.DataManager;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;

public class GroupSuggestionsUIManager {
    private String userId;
    private DataManager<Group> groupDataManager;
    private DataManager<GroupRole> groupRoleDataManager;

    public GroupSuggestionsUIManager(String userId, DataManager<Group> groupDataManager, DataManager<GroupRole> groupRoleDataManager) {
        this.userId = userId;
        this.groupDataManager = groupDataManager;
        this.groupRoleDataManager = groupRoleDataManager;
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
            if(!groupDataManager.getDataByName(groupName).getGroupMembers().contains(userId)){
                // Create a group Panel for each post
                GroupSuggestionsPanel groupSuggestionsPanel = new GroupSuggestionsPanel(groupName, groupImagePath,userId, groupDataManager,groupRoleDataManager);

                // Add padding and border to each PostPanel
                groupSuggestionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Add the PostPanel to the container
                groupContainer.add(groupSuggestionsPanel);
            }


        }

        // Revalidate and repaint the container to apply updates
        groupContainer.revalidate();
        groupContainer.repaint();
    }
}
