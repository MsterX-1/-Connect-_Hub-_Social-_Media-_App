package Frontend;

import Backend.ContentCreation.ContentDatabase;
import Backend.ContentCreation.Post;
import Backend.ContentCreation.Story;
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
    private Post post;
    private Story story;
    public publishContentWindow(String userId, String contentType,ContentDatabase contentDatabase) {
        post = new Post();
        story = new Story();
        setContentPane(main);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Share your thoughts");
        setResizable(false);
        setVisible(true);

        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(contentType.equals("post")) {
//                    Post.setPostCounter(contentDatabase.loadContentFromDatabase("post"));
                    post.addText(description.getText());
                    contentDatabase.addContentToDatabase(post);
                    System.out.println(contentDatabase.getContents().size());
                    //Post.setPostCounter(contentDatabase.getContents().size());
                    post.publishContent();
                    post.setAuthorId(userId);
                    contentDatabase.writeContentToDatabase("post");
                    setVisible(false);
                    dispose();
                }
                if(contentType.equals("story")) {
                    contentDatabase.loadContentFromDatabase("story");
//                    Story.setStoryCounter(contentDatabase.getLastStoryId());
                    story.addText(description.getText());
                    contentDatabase.addContentToDatabase(story);
                    story.publishContent();
                    story.setAuthorId(userId);
                    contentDatabase.writeContentToDatabase("story");
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
                    if(contentType.equals("post")) {
                        post.addImage(imagePath);
                        displayImages(post.getContent().getImagePaths());
                    }
                    if(contentType.equals("story")) {
                        story.addImage(imagePath);
                        displayImages(story.getContent().getImagePaths());
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
