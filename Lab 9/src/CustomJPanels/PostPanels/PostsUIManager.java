package CustomJPanels.PostPanels;

import Databases.DataManager;
import Interfaces.UIManager;
import PhaseOne.ContentCreation.Backend.Post;

import javax.swing.*;
import java.util.ArrayList;

public class PostsUIManager implements UIManager {
    private String userId;
    private DataManager<Post> postDataManager;

    public PostsUIManager(String userId, DataManager<Post> postDataManager) {
        this.userId = userId;
        this.postDataManager = postDataManager;
    }

    @Override
    public void refreshList(JPanel postContainer, JScrollPane postScrollPane) {
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populateList(postContainer);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public void populateList(JPanel postContainer) {
        postContainer.removeAll();
        // Simulate data for demonstration
        if(postDataManager.getAllData() == null)
            return;
        for (int i = 0; i < postDataManager.getAllData().size(); i++) {
            String text = postDataManager.getAllData().get(i).getContent().getText();
            ArrayList<String> imagePaths = postDataManager.getAllData().get(i).getContent().getImagePaths();

            // Create a PostPanel for each post
            PostPanel postPanel = new PostPanel(text, imagePaths);

            // Add padding and border to each PostPanel
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            postContainer.add(postPanel);

        }

        // Revalidate and repaint the container to apply updates
        postContainer.revalidate();
        postContainer.repaint();
    }
}
