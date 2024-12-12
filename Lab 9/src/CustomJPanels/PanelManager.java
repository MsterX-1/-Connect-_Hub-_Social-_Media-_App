package CustomJPanels;

import CustomJPanels.FriendPanels.FriendsUIManager;
import CustomJPanels.PostPanels.PostsUIManager;
import Databases.DataManager;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.util.ArrayList;

public class PanelManager {
    private FriendsUIManager friendsUIManager;
    private PostsUIManager postsUIManager;


    public PanelManager(String userId , DataManager<UserRelations> userRelationsManager, DataManager<User> userDataManager, DataManager<Profile> profileManager ,DataManager<Post> postManager ) {
        friendsUIManager = new FriendsUIManager(userId , userRelationsManager, userDataManager, profileManager);
        postsUIManager = new PostsUIManager(userId , postManager);
    }

    //responsible for friend management
    public FriendsUIManager getFriendsUIManager() {
        return friendsUIManager;
    }

    public PostsUIManager getPostsUIManager() {
        return postsUIManager;
    }


//    private void populateFriendsSearchList(JPanel friendsContainer, String userId) {
//        friendsContainer.removeAll();
//        // Simulate data for demonstration
//        if (userRelationsManager.getDataById(userId).getFriendsList() == null)
//            return;
//        for (int i = 0; i < userRelationsManager.getDataById(userId).getFriendsList().size(); i++) {
//
//            String friendId = userRelationsManager.getDataById(userId).getFriendsList().get(i);
//            String friendName = userDataManager.getDataById(friendId).getUsername();
//            String imagePath = profileManager.getDataById(friendId).getProfilePhotoPath();
//
//            // Create a PostPanel for each post
//            FriendPanel friendPanel = new FriendPanel(friendName, imagePath, userRelationsManager);
//
//            // Add padding and border to each PostPanel
//            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//            // Add the PostPanel to the container
//            friendsContainer.add(friendPanel);
//
//        }
//    }


}
