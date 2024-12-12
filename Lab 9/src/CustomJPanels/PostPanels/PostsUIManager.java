package CustomJPanels.PostPanels;

import Databases.DataManager;
import Interfaces.UIManager;
import PhaseOne.ContentCreation.Backend.Post;

import javax.swing.*;
import java.util.ArrayList;

public class PostsUIManager implements UIManager {
    private String userId;
    private DataManager<Post> postManager;

    public PostsUIManager(String userId, DataManager<Post> postManager) {
        this.userId = userId;
        this.postManager = postManager;
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
        if(postManager.getAllData() == null)
            return;
        for (int i = 0; i < postManager.getAllData().size(); i++) {
            String text = postManager.getAllData().get(i).getContent().getText();
            ArrayList<String> imagePaths = postManager.getAllData().get(i).getContent().getImagePaths();

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
