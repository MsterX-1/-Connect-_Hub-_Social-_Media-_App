package CustomJPanels.FriendPanels;

import CustomJPanels.ProfilePanel;
import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import Interfaces.UIManager;
import javax.swing.*;

public class FriendsUIManager implements UIManager {
    String userId;
    private DataManager<UserRelations> userRelationsManager;
    private DataManager<Profile> profileManager;
    private DataManager<User> userDataManager;

    public FriendsUIManager(String userId, DataManager<UserRelations> userRelationsManager ,DataManager<User> userDataManager , DataManager<Profile> profileManager) {
        this.userId = userId;
        this.userRelationsManager = userRelationsManager;
        this.userDataManager = userDataManager;
        this.profileManager = profileManager;
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
        if (userRelationsManager.getDataById(userId).getFriendsList() == null)
            return;
        for (int i = 0; i < userRelationsManager.getDataById(userId).getFriendsList().size(); i++) {

            String friendId = userRelationsManager.getDataById(userId).getFriendsList().get(i);
            String friendName = userDataManager.getDataById(friendId).getUsername();
            String friendStatus = userDataManager.getDataById(friendId).checkStatus();
            String imagePaths = profileManager.getDataById(friendId).getProfilePhotoPath();

            // Create a PostPanel for each post
            FriendPanel friendPanel = new FriendPanel(friendName + "( " + friendStatus + " )", imagePaths , userId, friendId ,userRelationsManager);

            // Add padding and border to each PostPanel
            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsContainer.add(friendPanel);

        }
    }
}
