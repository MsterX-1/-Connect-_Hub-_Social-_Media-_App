package Frontend;

import Backend.ContentCreation.ContentDatabase;
import Backend.FriendManager.FriendMangerWindow1;
import Backend.UserDatabase;
import Frontend.CustomPanels.PostPanel;
import Frontend.CustomPanels.ProfilePanel;

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
    private JButton FriendMangerbot;
    private JPanel postContainer;
    private JScrollPane postScrollPane;
    private JScrollPane friendScrollPane;
    private JPanel freindsContainer;
    private JScrollPane suggestionsScrollPane;
    private JPanel friendsSuggestions;
    private JPanel imagePlace;
    private UserDatabase userDatabase;
    private String userId;

    public Newsfeed(UserDatabase userDatabase, String userId,MainWindow mainWindow) {
        this.userDatabase = userDatabase;
        this.userId = userId;
        ContentDatabase contentDatabase = new ContentDatabase();
         contentDatabase.loadContentFromDatabase("post");
        //managing posts
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(contentDatabase);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //managing friendsList
        freindsContainer.setLayout(new BoxLayout(freindsContainer, BoxLayout.Y_AXIS));
        populateFriendsList(userDatabase);
        freindsContainer.setSize(new Dimension(200,100));
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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

                new PRofileManagementPage(userId, userDatabase,newsfeed,mainWindow).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId,"story", contentDatabase);
                //contentDatabase.loadContentFromDatabase("story");
            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId,"post", contentDatabase);
               // contentDatabase.loadContentFromDatabase("post");
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update posts window
                refreshPosts(contentDatabase);
                refreshFriendsList(userDatabase);

                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto();
                imagelabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        FriendMangerbot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendMangerWindow1(userDatabase , userId);

            }
        });
    }
    public Image updateNewsFeedPhoto(){
        int index = userDatabase.getUserIndexById(userId);
        String pathPhotoProfile = userDatabase.getUserById(userId).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imagelabel.getWidth();
        int scaledHeight = imagelabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }
    public void refreshPosts(ContentDatabase contentDatabase){
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(contentDatabase);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    public void refreshFriendsList(UserDatabase userDatabase){
        freindsContainer.setLayout(new BoxLayout(freindsContainer, BoxLayout.Y_AXIS));
        populateFriendsList(userDatabase);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private void populateFriendsList(UserDatabase userDatabase) {
        freindsContainer.removeAll();
        // Simulate data for demonstration
        for(int i =0 ; i<userDatabase.getUserById(userId).getFriendsIds().size();i++) {

            String friendId = userDatabase.getUserById(userId).getFriendsIds().get(i);
            String friendName = userDatabase.getUserById(friendId).getUsername();
            String friendStatus = userDatabase.getUserById(friendId).getStatus();
            String imagePaths = userDatabase.getUserById(friendId).getProfilePhotoPath();

            // Create a PostPanel for each post
            ProfilePanel friendPanel = new ProfilePanel(friendName+"( " + friendStatus + " )", imagePaths);

            // Add padding and border to each PostPanel
            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            freindsContainer.add(friendPanel);

        }

        // Revalidate and repaint the container to apply updates
        freindsContainer.revalidate();
        freindsContainer.repaint();
    }
    private void populatePosts(ContentDatabase contentDatabase) {
        postContainer.removeAll();
        // Simulate data for demonstration
        for (int i = 0; i < contentDatabase.getContents().size(); i++) {
            String text = contentDatabase.getContents().get(i).getContent().getText();
            ArrayList<String> imagePaths = contentDatabase.getContents().get(i).getContent().getImagePaths();

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

    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.loadFromFile();
        new Newsfeed(userDatabase , "1" , null);
    }
}
