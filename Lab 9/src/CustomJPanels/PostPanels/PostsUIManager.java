package CustomJPanels.PostPanels;

import Databases.DataManager;
import Interfaces.UIManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.util.ArrayList;

public class PostsUIManager {
    private String userId;
    private DataManager<Post> postDataManager;
    private DataManager<UserRelations> userRelationsDataManager;

    public PostsUIManager(String userId, DataManager<Post> postDataManager, DataManager<UserRelations> userRelationsDataManager) {
        this.userId = userId;
        this.postDataManager = postDataManager;
        this.userRelationsDataManager = userRelationsDataManager;
    }


    public void refreshList(JPanel postContainer, JScrollPane postScrollPane, String type) {
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populateList(postContainer, type);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void populateList(JPanel postContainer, String type) {
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

            // Add the PostPanel to the container
            if (type.equalsIgnoreCase("newsfeed")) {
                String authorId = postDataManager.getAllData().get(i).getAuthorId();
                if (userRelationsDataManager.getDataById(userId).getFriendsList().contains(authorId) ||
                        postDataManager.getAllData().get(i).getAuthorId().equals(userId)) {

                    postContainer.add(postPanel);
                }
            } else if (type.equalsIgnoreCase("profile")) {
                if (postDataManager.getAllData().get(i).getAuthorId().equals(userId)) {
                    postContainer.add(postPanel);
                }

            }


        }

        // Revalidate and repaint the container to apply updates
        postContainer.revalidate();
        postContainer.repaint();
    }
}
