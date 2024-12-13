package CustomJPanels.SuggestionPanels;

import CustomJPanels.FriendPanels.FriendPanel;
import Databases.DataManager;
import Databases.DataManagerFactory;
import Interfaces.UIManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;

public class SuggestionsUiManager implements UIManager {
    private String userId;
    private DataManager<UserRelations> userRelationsDataManager;
    private DataManager<User> userDataManager;
    private DataManager<Profile> profileDataManager;

    public SuggestionsUiManager(String userId) {
        this.userId = userId;
        this.userRelationsDataManager = DataManagerFactory.getDataManager("relations");
        this.userDataManager = DataManagerFactory.getDataManager("user");
        this.profileDataManager = DataManagerFactory.getDataManager("profile");
    }

    public void refreshList(JPanel suggestionsContainer , JScrollPane suggestionsScrollPane) {
        userRelationsDataManager.loadData();
        userDataManager.loadData();
        profileDataManager.loadData();
        //exit if database is empty
        if(userRelationsDataManager == null || userDataManager == null || profileDataManager == null)
            return;
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateList(suggestionsContainer);
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }




    public void populateList(JPanel suggestionsContainer) {
        suggestionsContainer.removeAll();

        if(userRelationsDataManager.getAllData() == null || userDataManager.getAllData() == null || profileDataManager.getAllData() == null)
            return;

        // Simulate data for demonstration
        if(userRelationsDataManager.getDataById(userId).getSuggestionsList() == null )
            return;
        for (int i = 0; i < userRelationsDataManager.getDataById(userId).getSuggestionsList().size(); i++) {
            String suggestedId = userRelationsDataManager.getDataById(userId).getSuggestionsList().get(i);
            String suggestedName = userDataManager.getDataById(suggestedId).getUsername();
            String imagePaths = profileDataManager.getDataById(suggestedId).getProfilePhotoPath();

            // Create a PostPanel for each post
            FriendPanel profilePanel = new FriendPanel(suggestedName, imagePaths , userId , suggestedId );

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
