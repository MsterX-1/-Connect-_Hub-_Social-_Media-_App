package Frontend;

import Backend.ContentCreation.ContentDatabase;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Newsfeed extends JFrame {
    private JPanel panel1;
    private JButton refreshButton;
    private JButton profileManagmentButton;
    private JButton createPostButton;
    private JLabel imagelabel;
    private JButton createStoryButton;
    private JScrollPane PostandStory;
    private JPanel postpanel;
    private JPanel imagePlace;
    private UserDatabase userDatabase;
    private String userId;
    private ContentDatabase contentdatabase;

    public Newsfeed(UserDatabase userDatabase, String userId, MainWindow mainWindow) {
        this.userDatabase = userDatabase;

        this.userId = userId;
        ContentDatabase contentdatabase = new ContentDatabase();

        contentdatabase.loadContentFromDatabase("post");

        JPanel Panel = new JPanel();

        PostandStory.add(Panel);
        Newsfeed newsfeed = this;
        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel1);


        imagelabel.setIcon(new ImageIcon(updateNewsFeedPhoto()));


        imagelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                new PRofileManagementPage(userId, userDatabase, newsfeed, mainWindow).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "story", PostandStory);


            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "post", PostandStory);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto();
                imagelabel.setIcon(new ImageIcon(updatedImage));
            }
        });

    }

    public Image updateNewsFeedPhoto() {
        int index = userDatabase.getUserIndexById(userId);
        String pathPhotoProfile = userDatabase.getUsers().get(index).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imagelabel.getWidth();
        int scaledHeight = imagelabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    public void showposts(int NoOfPosts) {
        content
        postpanel.removeAll();
        JLabel imagelabel = new JLabel();
        JLabel Textlabel = new JLabel();
        postpanel.add(imagelabel);
        postpanel.add(Textlabel);


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
}
