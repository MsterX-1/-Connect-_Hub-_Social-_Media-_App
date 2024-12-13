package CustomJPanels.PostPanels;

import Databases.DataManager;
import Databases.DataManagerFactory;
import Interfaces.UIManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.util.ArrayList;

public class ProfilePostsUIManager implements UIManager {
    private String userId;
    private DataManager<Post> postDataManager;
    private DataManager<UserRelations> userRelationsDataManager;

    public ProfilePostsUIManager(String userId) {
        this.userId = userId;
        this.postDataManager = DataManagerFactory.getDataManager("post");
        this.userRelationsDataManager = DataManagerFactory.getDataManager("relations");
    }

    public void refreshList(JPanel postContainer, JScrollPane postScrollPane) {
        postDataManager.loadData();
        userRelationsDataManager.loadData();
        if(postDataManager == null || userRelationsDataManager == null)
            return;
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populateList(postContainer);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void populateList(JPanel postContainer) {
        postContainer.removeAll();
        // Simulate data for demonstration
        if (postDataManager.getAllData() == null)
            return;

        for (int i = 0; i < postDataManager.getAllData().size(); i++) {


            String text = postDataManager.getAllData().get(i).getContent().getText();
            ArrayList<String> imagePaths = postDataManager.getAllData().get(i).getContent().getImagePaths();


            // Create a PostPanel for each post
            PostPanel postPanel = new PostPanel(text, imagePaths);

            // Add padding and border to each PostPanel
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container and checks for user posts
                if (postDataManager.getAllData().get(i).getAuthorId().equals(userId)) {
                    postContainer.add(postPanel);
                }



        }

        // Revalidate and repaint the container to apply updates
        postContainer.revalidate();
        postContainer.repaint();
    }
}
