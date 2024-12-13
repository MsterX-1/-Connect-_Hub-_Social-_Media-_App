package CustomJPanels.GroupPosts;

import Databases.DataManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseTwo.GroupManagement.Backend.GroupPosts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GroupPostPanel extends javax.swing.JPanel {

    private JPanel imagePanel;

    public GroupPostPanel(String text, String imagePaths, String groupName, Post post, DataManager<GroupPosts> groupPostsDataManager) {
        // Set layout for the main panel (vertical alignment of text and images)
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 400));

        // Image Panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the image
        imagePanel.setBackground(Color.GRAY);
        displayImages(imagePaths); // Add images to the image panel

        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the text
        textPanel.setBackground(Color.WHITE);
        JLabel textLabel = new JLabel(text); // Create label with provided text
        textPanel.add(textLabel); // Add label to text panel

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center align buttons
        buttonPanel.setOpaque(false);

        JButton editText = new JButton("Edit Post's text");
        JButton remove = new JButton("Remove Post");
        JButton editImage  = new JButton("Edit Post's image");

        buttonPanel.add(editText);
        buttonPanel.add(editImage);
        buttonPanel.add(remove);

        // Add the panels to the main panel in order
        add(imagePanel);
        add(textPanel);
        add(buttonPanel);

        // Action Listeners
        editText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Example edit action
                String newText = JOptionPane.showInputDialog("Enter new text for the post:");
                if (newText != null && !newText.trim().isEmpty()) {
                   groupPostsDataManager.getDataByName(groupName).editPostText(post,newText);
                    groupPostsDataManager.saveData();
                }
            }
        });

        editImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Example edit action
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File image = chooser.getSelectedFile();
                    String imagePath = image.getAbsolutePath();
                    groupPostsDataManager.getDataByName(groupName).editPostImage(post,imagePath);
                }
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupPostsDataManager.getDataByName(groupName).removePost(post);
                groupPostsDataManager.saveData();
            }
        });
    }

    private void displayImages(String imagePaths) {
        // Clear the panel (optional, if you want to refresh the panel each time)
        imagePanel.removeAll();

        // Load and resize the image
        ImageIcon imageIcon = resizeImage(new ImageIcon(imagePaths), 200, 200);

        // Create a JLabel to display the image
        JLabel imageLabel = new JLabel(imageIcon);

        // Set the preferred size for the JLabel
        imageLabel.setPreferredSize(new Dimension(200, 200));

        // Add the label to the JPanel
        imagePanel.add(imageLabel);

        // Revalidate and repaint the panel to update the display
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private ImageIcon resizeImage(ImageIcon originalImageIcon, int width, int height) {
        Image image = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
