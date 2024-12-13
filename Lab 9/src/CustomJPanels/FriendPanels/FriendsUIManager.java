package CustomJPanels.FriendPanels;

import Databases.DataManager;
import Databases.DataManagerFactory;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import Interfaces.UIManager;
import javax.swing.*;

public class FriendsUIManager implements UIManager {
    String userId;
    private DataManager<UserRelations> userRelationsDataManager;
    private DataManager<Profile> profileDataManager;
    private DataManager<User> userDataManager;

    public FriendsUIManager(String userId) {
        this.userId = userId;
        this.userRelationsDataManager = DataManagerFactory.getDataManager("relations");
        this.userDataManager = DataManagerFactory.getDataManager("user");
        this.profileDataManager = DataManagerFactory.getDataManager("profile");
    }


    public void refreshList( JPanel friendsContainer , JScrollPane friendScrollPane) {
        userDataManager.loadData();
        profileDataManager.loadData();
        userRelationsDataManager.loadData();
        //if databases are empty
        if(userRelationsDataManager == null || profileDataManager == null || userDataManager == null )
            return;
        friendsContainer.setLayout(new BoxLayout(friendsContainer, BoxLayout.Y_AXIS));
        populateList(friendsContainer);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    public void populateList( JPanel friendsContainer) {

        if( (userRelationsDataManager.getAllData() == null) || profileDataManager.getAllData() == null || userDataManager.getAllData() == null ) {
            return;
        }
        friendsContainer.removeAll();

        // Simulate data for demonstration
        if (userRelationsDataManager.getDataById(userId).getFriendsList() == null)
            return;
        for (int i = 0; i < userRelationsDataManager.getDataById(userId).getFriendsList().size(); i++) {

            String friendId = userRelationsDataManager.getDataById(userId).getFriendsList().get(i);
            String friendName = userDataManager.getDataById(friendId).getUsername();
            String friendStatus = userDataManager.getDataById(friendId).checkStatus();
            String imagePaths = profileDataManager.getDataById(friendId).getProfilePhotoPath();

            // Create a PostPanel for each post
            FriendPanel friendPanel = new FriendPanel(friendName + "( " + friendStatus + " )", imagePaths , userId, friendId );

            // Add padding and border to each PostPanel
            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsContainer.add(friendPanel);

        }
    }
}
