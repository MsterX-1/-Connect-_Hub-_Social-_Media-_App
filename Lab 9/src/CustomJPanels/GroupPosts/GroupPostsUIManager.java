package CustomJPanels.GroupPosts;

import CustomJPanels.PostPanels.PostPanel;
import Databases.DataManager;
import Interfaces.UIManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseTwo.GroupManagement.Backend.GroupPosts;

import javax.swing.*;
import java.util.ArrayList;

public class GroupPostsUIManager implements UIManager {
    private String groupName;
    private DataManager<GroupPosts> groupPostDataManager;
    private DataManager<UserRelations> userRelationsDataManager;

    public GroupPostsUIManager(String groupName, DataManager<GroupPosts> groupPostDataManager, DataManager<UserRelations> userRelationsDataManager) {
        this.groupName = groupName;
        this.groupPostDataManager = groupPostDataManager;
        this.userRelationsDataManager = userRelationsDataManager;
    }


    public void refreshList(JPanel postContainer, JScrollPane postScrollPane) {
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populateList(postContainer);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void populateList(JPanel postContainer) {
        postContainer.removeAll();
        // Simulate data for demonstration
        if (groupPostDataManager.getAllData() == null || groupPostDataManager == null)
            return;

        for (int i = 0; i < groupPostDataManager.getDataByName(groupName).getPosts().size(); i++) {

            String text = groupPostDataManager.getDataByName(groupName).getPosts().get(i).getContent().getText();
            ArrayList<String> imagePaths  = groupPostDataManager.getDataByName(groupName).getPosts().get(i).getContent().getImagePaths();


            // Create a PostPanel for each post
            PostPanel postPanel = new PostPanel(text, imagePaths);

            // Add padding and border to each PostPanel
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


            postContainer.add(postPanel);

        }

        // Revalidate and repaint the container to apply updates
        postContainer.revalidate();
        postContainer.repaint();
    }
}

