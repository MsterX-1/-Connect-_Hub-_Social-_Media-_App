package PhaseOne.Newsfeed.Frontend;


import CustomJPanels.FriendPanels.FriendsUIManager;


import CustomJPanels.PostPanels.PostsUIManager;
import CustomJPanels.SuggestionPanels.SuggestionsUiManager;
import Databases.DataManagerFactory;
import Databases.DataManager;

import PhaseOne.ContentCreation.Frontend.publishContentWindow;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.ProfileManagement.Frontend.ProfileManagementPage;
import PhaseOne.UserAccountManagement.Backend.User;
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

        //managing posts
        PostsUIManager postsUIManager = new PostsUIManager(userId);

        //managing friendsList
        FriendsUIManager friendsUIManager = new FriendsUIManager(userId);

        //managing suggestions
        SuggestionsUiManager suggestionsUiManager = new SuggestionsUiManager(userId);
//
//        //managing user groups
//        GroupUIManager groupUIManager = new GroupUIManager(userId,groupDataManager);

        postsUIManager.refreshList(postContainer,postScrollPane,"newsfeed");
        friendsUIManager.refreshList(friendsContainer, friendScrollPane);
        suggestionsUiManager.refreshList(friendSuggestionsContainer, friendSuggestionsScrollPane);
//        groupUIManager.refreshList(userGroupsContainer,groupScrollPane);
//        groupUIManager.refreshList(suggestedGroupsContainer,groupSuggestionsScrollpane);



        Newsfeed newsfeed = this;
        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(new Dimension(1500, 800));
        currentUserPanel.setPreferredSize(new Dimension(400, 200));
        friendScrollPane.setSize(new Dimension(600, 200));
        postScrollPane.setSize(new Dimension(600, 500));
        friendSuggestionsScrollPane.setSize(new Dimension(600, 200));
        lowerButtons.setPreferredSize(new Dimension(1000, 100));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(mainContainer);


        imageLabel.setIcon(new ImageIcon(updateNewsFeedPhoto()));
        usernameLabel.setText(userDataManager.getDataById(userId).getUsername());

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //SOLID profile management should take both posts and stories
                new ProfileManagementPage(userId, newsfeed, mainWindow).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "story");
            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "post");
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update all scroll panes window
                postsUIManager.refreshList(postContainer,postScrollPane,"newsfeed");
                friendsUIManager.refreshList(friendsContainer, friendScrollPane);
                suggestionsUiManager.refreshList(friendSuggestionsContainer, friendSuggestionsScrollPane);
//                groupUIManager.refreshList(userGroupsContainer,groupScrollPane);
//                groupUIManager.refreshList(suggestedGroupsContainer,groupSuggestionsScrollpane);

//                 Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto();
                imageLabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        friendManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuWindow( userId);
            }
        });
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private Image updateNewsFeedPhoto() {
        DataManager<Profile> profileManager = DataManagerFactory.getDataManager("profile");
        String pathPhotoProfile = profileManager.getDataById(userId).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imageLabel.getWidth();
        int scaledHeight = imageLabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }

}
