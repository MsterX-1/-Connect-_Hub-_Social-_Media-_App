package Backend.ContentCreation;

import Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class CreatePostWindow extends JFrame {
    private JPanel main;
    private JButton publishButton;
    private JButton storyButton;
    private JTextArea description;
    private JButton story;
    private JButton image;
    private JLabel textAreaLabel;
    private JPanel buttonPanel;
    private JPanel textPanel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private String imagePath;
    private Post post;
    public CreatePostWindow(User user) {
        ContentDatabase contentDatabase = new ContentDatabase();
        Post.setPostCounter(contentDatabase.loadContentFromDatabase());
        post = new Post();
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Share your thoughts");
        setVisible(true);
        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                post.addText(description.getText());
                contentDatabase.addContentToDatabase(post);
                post.publishContent();
                contentDatabase.writeContentToDatabase();
                setVisible(false);
                dispose();
            }
        });
        image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File image = chooser.getSelectedFile();
                    imagePath = image.getAbsolutePath();
                    post.addImage(imagePath);
                    displayImages(post.getContent().getImagePaths());
                }
            }
        });
    }

    private void displayImages(ArrayList<String> imagePaths) {
        // Clear the panel (optional, if you want to refresh the panel each time)
        imagePanel.removeAll();

        // Loop through each image path and add a JLabel with the image
        for (String imagePath : imagePaths) {
            // Load and resize the image
            ImageIcon imageIcon = resizeImage(new ImageIcon(imagePath), 120, 120);

            // Create a JLabel to display the image
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

    // Resize image helper function
    private ImageIcon resizeImage(ImageIcon originalImageIcon, int width, int height) {
        Image image = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static void main(String[] args) {

        new CreatePostWindow(new User());
    }
}
