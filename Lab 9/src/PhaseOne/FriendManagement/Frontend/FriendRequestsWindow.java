package PhaseOne.FriendManagement.Frontend;

import Databases.DataManager;
import Databases.DataManagerFactory;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import CustomJPanels.RequestsPanel;

import javax.swing.*;

public class FriendRequestsWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane requestsScrollPane;
    private JPanel requestsContainer;
    private String userId;
    private FriendRequestsWindow friendRequestsWindow;
    private DataManager<UserRelations> userRelationsDataManager;
    private DataManager<Profile> profileManager;
    public FriendRequestsWindow(String userId) {
        this.userId = userId;
        friendRequestsWindow = this;
        this.userRelationsDataManager = DataManagerFactory.getDataManager("relations");
        this.profileManager = DataManagerFactory.getDataManager("profile");
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Friend requests");

        requestsContainer.setLayout(new BoxLayout(requestsContainer, BoxLayout.Y_AXIS));
        DataManager<User> userDataManager = DataManagerFactory.getDataManager("user");
        populateRequestsList(userDataManager);
        requestsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        requestsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVisible(true);

    }
    public void populateRequestsList(DataManager<User> userDataManager) {
        // Simulate data for demonstration
        if(userRelationsDataManager.getDataById(userId).getFriendRequestsList() == null)
            return;
        requestsContainer.removeAll();
        for (int i = 0; i < userRelationsDataManager.getDataById(userId).getFriendRequestsList().size(); i++) {
            String senderId = userRelationsDataManager.getDataById(userId).getFriendRequestsList().get(i);
            // Create a PostPanel for each post
            RequestsPanel requestsPanel = new RequestsPanel(userRelationsDataManager , userId , senderId , friendRequestsWindow , userDataManager,profileManager);

            // Add padding and border to each PostPanel
            requestsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            requestsContainer.add(requestsPanel);

        }

        // Revalidate and repaint the container to apply updates
        requestsContainer.revalidate();
        requestsContainer.repaint();
    }




}
