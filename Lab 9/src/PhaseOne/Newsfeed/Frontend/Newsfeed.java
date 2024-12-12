package PhaseOne.Newsfeed.Frontend;


import CustomJPanels.PanelManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.ContentCreation.Backend.Story;
import Databases.DataManager;
import Databases.DatabaseFactory;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.FriendManagement.Frontend.FriendMangerWindow;
import Interfaces.Database;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.ProfileManagement.Frontend.ProfileManagementPage;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseOne.ContentCreation.Frontend.publishContentWindow;
import CustomJPanels.ProfilePanel;
import RunProgram.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Newsfeed extends JFrame {
    private JPanel mainContainer;
    private JButton refreshButton;
    private JButton profileManagmentButton;
    private JButton createPostButton;
    private JLabel imageLabel;
    private JButton createStoryButton;
    private JPanel postContainer;
    private JScrollPane postScrollPane;
    private JScrollPane friendScrollPane;
    private JPanel friendsContainer;
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
        //create user relation database and manager
        Database<UserRelations> userRelationsDatabase = DatabaseFactory.createDatabase("relations");
        DataManager<UserRelations> userRelationsDataManager = new DataManager<>(userRelationsDatabase);
        userRelationsDataManager.loadData();
        //create Profile database and manager
        Database<Profile> profileDatabase = DatabaseFactory.createDatabase("profile");
        DataManager<Profile> profileManager = new DataManager<>(profileDatabase);
        profileManager.loadData();

        PanelManager panelManager = new PanelManager(userId,userRelationsDataManager,userDataManager , profileManager , postManager);

        //managing posts
        panelManager.getPostsUIManager().refreshList(postContainer,postScrollPane);

        //managing friendsList
        panelManager.getFriendsUIManager().refreshList(friendsContainer, friendScrollPane);

        //managing suggestions
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateSuggestionsList(userDataManager , userRelationsDataManager,profileManager);
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


        imageLabel.setIcon(new ImageIcon(updateNewsFeedPhoto(profileManager)));
        usernameLabel.setText(userDataManager.getDataById(userId).getUsername());

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //SOLID profile management should take both posts and stories
                new ProfileManagementPage(userId, userDataManager, newsfeed, mainWindow,postManager , storyManager,profileManager).setVisible(true);
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
                panelManager.getPostsUIManager().refreshList(postContainer,postScrollPane);
                panelManager.getFriendsUIManager().refreshList(friendsContainer, friendScrollPane);
                refreshSuggestionsList(userDataManager , userRelationsDataManager,profileManager);

                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto(profileManager);
                imageLabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        friendManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendMangerWindow(userDataManager, userId , userRelationsDataManager,profileManager);

            }
        });
    }

    public Image updateNewsFeedPhoto(DataManager<Profile> profileManager) {

        String pathPhotoProfile = profileManager.getDataById(userId).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imageLabel.getWidth();
        int scaledHeight = imageLabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }




    public void refreshSuggestionsList(DataManager<User> userDataManager , DataManager<UserRelations> userRelationsDataManager,DataManager<Profile> profileManager) {
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateSuggestionsList(userDataManager , userRelationsDataManager,profileManager);
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }




    private void populateSuggestionsList(DataManager<User> userDataManager , DataManager<UserRelations> userRelationsDataManager,DataManager<Profile> profileManager) {
        suggestionsContainer.removeAll();
        // Simulate data for demonstration
        if(userRelationsDataManager.getDataById(userId).getSuggestionsList() == null)
            return;
        for (int i = 0; i < userRelationsDataManager.getDataById(userId).getSuggestionsList().size(); i++) {
            String suggestedId = userRelationsDataManager.getDataById(userId).getSuggestionsList().get(i);
            String suggestedName = userDataManager.getDataById(suggestedId).getUsername();
            String imagePaths = profileManager.getDataById(suggestedId).getProfilePhotoPath();

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
