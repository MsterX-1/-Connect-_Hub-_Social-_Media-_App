package PhaseOne.Newsfeed.Frontend;


import CustomJPanels.FriendPanels.FriendsUIManager;


import CustomJPanels.GroupPanels.GroupUIManager;
import CustomJPanels.PostPanels.PostsUIManager;
import CustomJPanels.SuggestionPanels.SuggestionsUiManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.ContentCreation.Backend.Story;
import Databases.DataManager;
import Databases.DatabaseFactory;
import PhaseOne.FriendManagement.Backend.UserRelations;
import Interfaces.Database;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.ProfileManagement.Frontend.ProfileManagementPage;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseOne.ContentCreation.Frontend.publishContentWindow;
import PhaseTwo.GroupManagement.Backend.Group;
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
    private JButton createPostButton;
    private JLabel imageLabel;
    private JButton createStoryButton;
    private JPanel postContainer;
    private JScrollPane postScrollPane;
    private JScrollPane friendScrollPane;
    private JPanel friendsContainer;
    private JScrollPane friendSuggestionsScrollPane;
    private JPanel friendSuggestionsContainer;
    private JButton friendManagerButton;
    private JPanel currentUserPanel;
    private JPanel lowerButtons;
    private JLabel usernameLabel;
    private JScrollPane groupScrollPane;
    private JPanel userGroupsContainer;
    private JPanel suggestedGroupsContainer;
    private JButton createGroupButton;
    private JScrollPane groupSuggestionsScrollpane;
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

        //create Profile database and manager
        Database<Group> groupDatabase = DatabaseFactory.createDatabase("group");
        DataManager<Group> groupDataManager = new DataManager<>(groupDatabase);
        profileManager.loadData();

        //managing posts
        PostsUIManager postsUIManager = new PostsUIManager(userId, postManager);

        //managing friendsList
        FriendsUIManager friendsUIManager = new FriendsUIManager(userId, userRelationsDataManager, userDataManager, profileManager);

        //managing suggestions
        SuggestionsUiManager suggestionsUiManager = new SuggestionsUiManager(userId, userRelationsDataManager, userDataManager, profileManager);

        //managing user groups
        GroupUIManager groupUIManager = new GroupUIManager(userId, groupDataManager);

        postsUIManager.refreshList(postContainer, postScrollPane);
        friendsUIManager.refreshList(friendsContainer, friendScrollPane);
        suggestionsUiManager.refreshList(friendSuggestionsContainer, friendSuggestionsScrollPane);
        groupUIManager.refreshList(userGroupsContainer, groupScrollPane);
        groupUIManager.refreshList(suggestedGroupsContainer, groupSuggestionsScrollpane);


        mainContainer.setBackground(Color.orange);
        lowerButtons.setBackground(Color.orange);
        currentUserPanel.setBackground(Color.orange);

        friendManagerButton.setForeground(Color.black);
        friendManagerButton.setBackground(Color.white);
        refreshButton.setBackground(Color.white);
        refreshButton.setForeground(Color.black);
        createPostButton.setForeground(Color.black);
        createPostButton.setBackground(Color.white);
        createGroupButton.setBackground(Color.white);
        createGroupButton.setForeground(Color.black);
        createStoryButton.setForeground(Color.black);
        createStoryButton.setBackground(Color.white);


        Newsfeed newsfeed = this;
        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(new Dimension(1200, 800));
        currentUserPanel.setPreferredSize(new Dimension(400, 200));
        friendScrollPane.setSize(new Dimension(600, 200));
        postScrollPane.setSize(new Dimension(600, 500));
        friendSuggestionsScrollPane.setSize(new Dimension(600, 200));
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
                new ProfileManagementPage(userId, userDataManager, newsfeed, mainWindow, postManager, storyManager, profileManager).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "story", postManager, storyManager);
            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "post", postManager, storyManager);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update all scrollpanes window
                postsUIManager.refreshList(postContainer, postScrollPane);
                friendsUIManager.refreshList(friendsContainer, friendScrollPane);
                suggestionsUiManager.refreshList(friendSuggestionsContainer, friendSuggestionsScrollPane);
                groupUIManager.refreshList(userGroupsContainer, groupScrollPane);
                groupUIManager.refreshList(suggestedGroupsContainer, groupSuggestionsScrollpane);

                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto(profileManager);
                imageLabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        friendManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuWindow(userDataManager, userId, userRelationsDataManager, profileManager);
            }
        });
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private Image updateNewsFeedPhoto(DataManager<Profile> profileManager) {

        String pathPhotoProfile = profileManager.getDataById(userId).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imageLabel.getWidth();
        int scaledHeight = imageLabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }

}
