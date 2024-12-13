package CustomJPanels.FriendPanels;

import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import Interfaces.UIManager;
import javax.swing.*;

public class FriendsUIManager implements UIManager {
    String userId;
    private DataManager<UserRelations> userRelationsDataManagerRelationsManager;
    private DataManager<Profile> profileDataManager;
    private DataManager<User> userDataManager;

    public FriendsUIManager(String userId, DataManager<UserRelations> userRelationsManager ,DataManager<User> userDataManager , DataManager<Profile> profileManager) {
        this.userId = userId;
        this.userRelationsDataManagerRelationsManager = userRelationsManager;
        this.userDataManager = userDataManager;
        this.profileDataManager = profileManager;
    }


    public void refreshList( JPanel friendsContainer , JScrollPane friendScrollPane) {
        friendsContainer.setLayout(new BoxLayout(friendsContainer, BoxLayout.Y_AXIS));
        populateList(friendsContainer);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    public void populateList( JPanel friendsContainer) {
        friendsContainer.removeAll();
        // Simulate data for demonstration
        if (userRelationsDataManagerRelationsManager.getDataById(userId).getFriendsList() == null)
            return;
        for (int i = 0; i < userRelationsDataManagerRelationsManager.getDataById(userId).getFriendsList().size(); i++) {

            String friendId = userRelationsDataManagerRelationsManager.getDataById(userId).getFriendsList().get(i);
            String friendName = userDataManager.getDataById(friendId).getUsername();
            String friendStatus = userDataManager.getDataById(friendId).checkStatus();
            String imagePaths = profileDataManager.getDataById(friendId).getProfilePhotoPath();

            // Create a PostPanel for each post
            FriendPanel friendPanel = new FriendPanel(friendName + "( " + friendStatus + " )", imagePaths , userId, friendId , userRelationsDataManagerRelationsManager , userDataManager , profileDataManager );

            // Add padding and border to each PostPanel
            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsContainer.add(friendPanel);

        }
    }
}
