package PhaseOne.FriendManagement.Frontend;

import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
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
    public FriendRequestsWindow(String userId , DataManager<User>userDataManager , DataManager<UserRelations> userRelationsDataManager) {
        this.userId = userId;
        friendRequestsWindow = this;
        this.userRelationsDataManager = userRelationsDataManager;
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Friend requests");

        requestsContainer.setLayout(new BoxLayout(requestsContainer, BoxLayout.Y_AXIS));
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
            RequestsPanel requestsPanel = new RequestsPanel(userRelationsDataManager , userId , senderId , friendRequestsWindow , userDataManager);

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
