package CustomJPanels.GroupPanels;

import Databases.DataManager;
import Interfaces.UIManager;
import PhaseTwo.GroupManagement.Backend.Group;

import javax.swing.*;
import java.util.ArrayList;


public class GroupUIManager implements UIManager {
    private String userId;
    private DataManager<Group> groupDataManager;

    public GroupUIManager(String userId, DataManager<Group> groupDataManager) {
        this.userId = userId;
        this.groupDataManager = groupDataManager;
    }

    @Override
    public void refreshList(JPanel groupContainer, JScrollPane groupScrollPane) {
        groupContainer.setLayout(new BoxLayout(groupContainer, BoxLayout.Y_AXIS));
        populateList(groupContainer);
        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        groupScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public void populateList(JPanel groupContainer) {
        groupContainer.removeAll();
        // Simulate data for demonstration
        if(groupDataManager.getAllData() == null)
            return;
        for (int i = 0; i < groupDataManager.getAllData().size(); i++) {

            String groupName = groupDataManager.getAllData().get(i).getGroupName();
            String groupImagePath = groupDataManager.getAllData().get(i).getGroupPhotoPath();

            // Create a group Panel for each post
            GroupPanel groupPanel = new GroupPanel(groupName, groupImagePath,userId, groupDataManager);

            // Add padding and border to each PostPanel
            groupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            groupContainer.add(groupPanel);

        }

        // Revalidate and repaint the container to apply updates
        groupContainer.revalidate();
        groupContainer.repaint();
    }
}
