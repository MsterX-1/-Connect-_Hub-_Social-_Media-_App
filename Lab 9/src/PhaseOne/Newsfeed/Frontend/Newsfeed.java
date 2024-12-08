package PhaseOne.Newsfeed.Frontend;


import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.ContentCreation.Backend.Story;
import Databases.DataManager;
import Databases.DatabaseFactory;
import PhaseOne.FriendManagement.Frontend.FriendMangerWindow1;
import Interfaces.Database;
import PhaseOne.ProfileManagement.Frontend.ProfileManagementPage;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseOne.ContentCreation.Frontend.publishContentWindow;
import CustomJPanels.PostPanel;
import CustomJPanels.ProfilePanel;
import RunProgram.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Newsfeed extends JFrame {
    private JPanel mainContainer;
    private JButton refreshButton;
    private JButton profileManagmentButton;
    private JButton createPostButton;
    private JLabel imagelabel;
    private JButton createStoryButton;
    private JPanel postContainer;
    private JScrollPane postScrollPane;
    private JScrollPane friendScrollPane;
    private JPanel freindsContainer;
    private JScrollPane suggestionsScrollPane;
    private JPanel suggestionsContainer;
    private JButton friendManagerButton;
    private JPanel currentUserPanel;
    private JPanel lowerButtons;
    private JLabel usernameLabel;
    private JPanel imagePlace;
    private String userId;

    public Newsfeed(DataManager<User> userDataManager, String userId, MainWindow mainWindow) {
        this.userId = userId;
        //create post database and create a data manager
        Database<Post> postDatabase = DatabaseFactory.createDatabase("post");
        DataManager<Post> postManager = new DataManager<>(postDatabase);
        postManager.loadData();
        //create story database and create a data manager
        Database<Story> storyDatabase = DatabaseFactory.createDatabase("story");
        DataManager<Story> storyManager = new DataManager<>(storyDatabase);
        storyManager.loadData();

        //managing posts
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(postManager);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //managing friendsList
        freindsContainer.setLayout(new BoxLayout(freindsContainer, BoxLayout.Y_AXIS));
        populateFriendsList(userDataManager);
        freindsContainer.setSize(new Dimension(200, 100));
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //managing suggestions
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateSuggestionsList(userDataManager);
        suggestionsContainer.setSize(new Dimension(200, 100));
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Newsfeed newsfeed = this;
        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(new Dimension(1000, 800));
        currentUserPanel.setPreferredSize(new Dimension(400, 200));
        friendScrollPane.setSize(new Dimension(600, 200));
        postScrollPane.setSize(new Dimension(600, 500));
        suggestionsScrollPane.setSize(new Dimension(400, 500));
        lowerButtons.setPreferredSize(new Dimension(1000, 100));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(mainContainer);


        imagelabel.setIcon(new ImageIcon(updateNewsFeedPhoto(userDataManager)));
        usernameLabel.setText(userDataManager.getDataById(userId).getUsername());

        imagelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //SOLID profile management should take both posts and stories
                new ProfileManagementPage(userId, userDataManager, newsfeed, mainWindow,postManager , storyManager).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "story", postManager , storyManager);
            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "post", postManager , storyManager);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update posts window
                refreshPosts(postManager);
                refreshFriendsList(userDataManager);
                refreshSuggestionsList(userDataManager);

                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto(userDataManager);
                imagelabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        friendManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendMangerWindow1(userDataManager, userId);

            }
        });
    }

    public Image updateNewsFeedPhoto(DataManager<User> userDataManager) {

        String pathPhotoProfile = userDataManager.getDataById(userId).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imagelabel.getWidth();
        int scaledHeight = imagelabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    public void refreshPosts(DataManager<Post> postManager) {
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(postManager);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void refreshFriendsList(DataManager<User> userDataManager) {
        freindsContainer.setLayout(new BoxLayout(freindsContainer, BoxLayout.Y_AXIS));
        populateFriendsList(userDataManager);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void refreshSuggestionsList(DataManager<User> userDataManager) {
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateSuggestionsList(userDataManager);
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void populateFriendsList(DataManager<User> userDataManager) {
        freindsContainer.removeAll();
        // Simulate data for demonstration
        if(userDataManager.getDataById(userId).getFriendsIds() == null)
            return;
        for (int i = 0; i < userDataManager.getDataById(userId).getFriendsIds().size(); i++) {

            String friendId = userDataManager.getDataById(userId).getFriendsIds().get(i);
            String friendName = userDataManager.getDataById(friendId).getUsername();
            String friendStatus = userDataManager.getDataById(friendId).checkStatus();
            String imagePaths = userDataManager.getDataById(friendId).getProfilePhotoPath();

            // Create a PostPanel for each post
            ProfilePanel friendPanel = new ProfilePanel(friendName + "( " + friendStatus + " )", imagePaths);

            // Add padding and border to each PostPanel
            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            freindsContainer.add(friendPanel);

        }

        // Revalidate and repaint the container to apply updates
        freindsContainer.revalidate();
        freindsContainer.repaint();
    }

    private void populatePosts(DataManager<Post> postManager) {
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

    private void populateSuggestionsList(DataManager<User> userDataManager) {
        suggestionsContainer.removeAll();
        // Simulate data for demonstration
        if(userDataManager.getDataById(userId).getSuggestedIds() == null)
            return;
        for (int i = 0; i < userDataManager.getDataById(userId).getSuggestedIds().size(); i++) {
            String suggestedId = userDataManager.getDataById(userId).getSuggestedIds().get(i);
            String suggestedName = userDataManager.getDataById(suggestedId).getUsername();
            String imagePaths = userDataManager.getDataById(suggestedId).getProfilePhotoPath();

            // Create a PostPanel for each post
            ProfilePanel profilePanel = new ProfilePanel(suggestedName, imagePaths);

            // Add padding and border to each PostPanel
            profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            suggestionsContainer.add(profilePanel);

        }

        // Revalidate and repaint the container to apply updates
        suggestionsContainer.revalidate();
        suggestionsContainer.repaint();
    }

}
