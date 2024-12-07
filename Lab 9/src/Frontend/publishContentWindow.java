package Frontend;

import Backend.ContentCreation.Post;
import Backend.ContentCreation.Story;
import Backend.Databases.DataManager;
import Backend.Databases.DatabaseFactory;
import Backend.Interfaces.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class publishContentWindow extends JFrame {
    private JPanel main;
    private JButton publishButton;
    private JTextArea description;
    private JButton addImageButton;
    private JLabel textAreaLabel;
    private JPanel buttonPanel;
    private JPanel textPanel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private String imagePath;
    private Post newPost;
    private Story newStory;
    public publishContentWindow(String userId, String windowMode, DataManager<Post> postDatabaseDataManager , DataManager<Story> storyDatabaseDataManager) {
        newPost = new Post();
        newStory = new Story();
        setContentPane(main);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Share your thoughts");
        setResizable(false);
        setVisible(true);

        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(windowMode.equalsIgnoreCase("post")) {
                    Post.setPostCounter(postDatabaseDataManager.getData().size());
                    //add text to newPost and publish it
                    newPost.addText(description.getText());
                    newPost.publishContent(userId);
                    //add a new post to database
                    postDatabaseDataManager.insertData(newPost);
                    setVisible(false);
                    dispose();
                }
                if(windowMode.equalsIgnoreCase("story")) {
                    //set story counter
                    if(!storyDatabaseDataManager.getData().isEmpty()) {     //check for empty story database
                        String id = storyDatabaseDataManager.getData().getLast().getContentId();    //get last id of story
                        String[] split = id.split(" ");
                        Story.setStoryCounter(Integer.parseInt(split[1]));  //sets counter as last storyId count
                    }
                    //add text to newStory and publish it
                    newStory.addText(description.getText());
                    newStory.publishContent(userId);
                    //add a new story to database
                    storyDatabaseDataManager.insertData(newStory);
                    setVisible(false);
                    dispose();
                }

            }
        });
        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File image = chooser.getSelectedFile();
                    imagePath = image.getAbsolutePath();
                    if(windowMode.equals("newPost")) {
                        newPost.addImage(imagePath);
                        displayImages(newPost.getContent().getImagePaths());
                    }
                    if(windowMode.equals("newStory")) {
                        newStory.addImage(imagePath);
                        displayImages(newStory.getContent().getImagePaths());
                    }
                }
            }
        });

    }

    private void displayImages(ArrayList<String> imagePaths) {
        // Clear the panel (optional, if you want to refresh the panel each time)
        imagePanel.removeAll();

        // Loop through each addImageButton path and add a JLabel with the addImageButton
        for (String imagePath : imagePaths) {
            // Load and resize the addImageButton
            ImageIcon imageIcon = resizeImage(new ImageIcon(imagePath), 120, 120);

            // Create a JLabel to display the addImageButton
            JLabel imageLabel = new JLabel(imageIcon);

            // Set the preferred size for the JLabel
            imageLabel.setPreferredSize(new Dimension(120, 120));

            // Add the label to the JPanel
            imagePanel.add(imageLabel);
        }

        // Revalidate and repaint the panel to update the display
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    // Resize addImageButton helper function
    private ImageIcon resizeImage(ImageIcon originalImageIcon, int width, int height) {
        Image image = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

}
