package CustomJPanels.SuggestionPanels;

import CustomJPanels.FriendPanels.FriendPanel;
import Databases.DataManager;
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

    public SuggestionsUiManager(String userId, DataManager<UserRelations> userRelationsDataManager, DataManager<User> userDataManager , DataManager<Profile> profileDataManager) {
        this.userId = userId;
        this.userRelationsDataManager = userRelationsDataManager;
        this.userDataManager = userDataManager;
        this.profileDataManager = profileDataManager;
    }

    public void refreshList(JPanel suggestionsContainer , JScrollPane suggestionsScrollPane) {
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateList(suggestionsContainer);
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }




    public void populateList(JPanel suggestionsContainer) {
        suggestionsContainer.removeAll();
        // Simulate data for demonstration
        if(userRelationsDataManager.getDataById(userId).getSuggestionsList() == null)
            return;
        for (int i = 0; i < userRelationsDataManager.getDataById(userId).getSuggestionsList().size(); i++) {
            String suggestedId = userRelationsDataManager.getDataById(userId).getSuggestionsList().get(i);
            String suggestedName = userDataManager.getDataById(suggestedId).getUsername();
            String imagePaths = profileDataManager.getDataById(suggestedId).getProfilePhotoPath();

            // Create a PostPanel for each post
            FriendPanel profilePanel = new FriendPanel(suggestedName, imagePaths , userId , suggestedId , userRelationsDataManager);

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
