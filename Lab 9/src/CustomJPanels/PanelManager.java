package CustomJPanels;

import CustomJPanels.FriendPanels.FriendsUIManager;
import CustomJPanels.PostPanels.PostsUIManager;
import CustomJPanels.SuggestionPanels.SuggestionsUiManager;
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
    private SuggestionsUiManager suggestionsUIManager;


    public PanelManager(String userId , DataManager<UserRelations> userRelationsManager, DataManager<User> userDataManager, DataManager<Profile> profileManager ,DataManager<Post> postManager ) {
        friendsUIManager = new FriendsUIManager(userId , userRelationsManager, userDataManager, profileManager);
        postsUIManager = new PostsUIManager(userId , postManager);
        suggestionsUIManager = new SuggestionsUiManager(userId , userRelationsManager , userDataManager , profileManager);
    }

    //responsible for friend management
    public FriendsUIManager getFriendsUIManager() {
        return friendsUIManager;
    }

    public PostsUIManager getPostsUIManager() {
        return postsUIManager;
    }

    public SuggestionsUiManager getSuggestionsUIManager() {
        return suggestionsUIManager;
    }



}
