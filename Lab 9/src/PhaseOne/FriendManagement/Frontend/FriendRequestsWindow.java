package PhaseOne.FriendManagement.Frontend;

import Databases.DataManager;
import PhaseOne.UserAccountManagement.Backend.User;
import CustomJPanels.RequestsPanel;

import javax.swing.*;

public class FriendRequestsWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane requestsScrollPane;
    private JPanel requestsContainer;
    private String userId;
    private FriendMangerWindow1 friendMangerWindow1;
    private FriendRequestsWindow friendRequestsWindow;
    public FriendRequestsWindow(String userId , DataManager<User>userDataManager , FriendMangerWindow1 friendMangerWindow1) {
        this.userId = userId;
        this.friendMangerWindow1 = friendMangerWindow1;
        friendRequestsWindow = this;
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
        if(userDataManager.getDataById(userId).getFriendsRequestsIds() == null)
            return;
        requestsContainer.removeAll();
        for (int i = 0; i < userDataManager.getDataById(userId).getFriendsRequestsIds().size(); i++) {
            String senderId = userDataManager.getDataById(userId).getFriendsRequestsIds().get(i);
            String senderName = userDataManager.getDataById(senderId).getUsername();
            String imagePaths = userDataManager.getDataById(senderId).getProfilePhotoPath();

            // Create a PostPanel for each post
            RequestsPanel requestsPanel = new RequestsPanel(senderName,imagePaths,friendMangerWindow1 , userId , senderId , friendRequestsWindow , userDataManager);

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
