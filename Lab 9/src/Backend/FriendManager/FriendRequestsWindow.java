package Backend.FriendManager;

import Backend.UserDatabase;
import Frontend.CustomPanels.ProfilePanel;
import Frontend.CustomPanels.RequestsPanel;

import javax.swing.*;

public class FriendRequestsWindow extends JFrame {
    private JPanel mainPanel;
    private JScrollPane requestsScrollPane;
    private JPanel requestsContainer;
    private String userId;
    private UserDatabase userDatabase;
    private FriendMangerWindow1 friendMangerWindow1;
    private FriendRequestsWindow friendRequestsWindow;
    public FriendRequestsWindow(String userId , UserDatabase userDatabase , FriendMangerWindow1 friendMangerWindow1) {
        this.userDatabase = userDatabase;
        this.userId = userId;
        this.friendMangerWindow1 = friendMangerWindow1;
        friendRequestsWindow = this;
        setContentPane(mainPanel);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Friend requests");

        requestsContainer.setLayout(new BoxLayout(requestsContainer, BoxLayout.Y_AXIS));
        populateRequestsList(userDatabase);
        requestsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        requestsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVisible(true);

    }
    public void populateRequestsList(UserDatabase userDatabase) {
        // Simulate data for demonstration
        if(userDatabase.getUserById(userId).getFriendsRequestsIds() == null)
            return;
        requestsContainer.removeAll();
        for (int i = 0; i < userDatabase.getUserById(userId).getFriendsRequestsIds().size(); i++) {
            String senderId = userDatabase.getUserById(userId).getFriendsRequestsIds().get(i);
            String senderName = userDatabase.getUserById(senderId).getUsername();
            String imagePaths = userDatabase.getUserById(senderId).getProfilePhotoPath();

            // Create a PostPanel for each post
            RequestsPanel requestsPanel = new RequestsPanel(senderName,imagePaths,friendMangerWindow1 , userId , senderId , friendRequestsWindow , userDatabase);

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
