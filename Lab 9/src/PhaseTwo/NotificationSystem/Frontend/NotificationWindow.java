package PhaseTwo.NotificationSystem.Frontend;

import CustomJPanels.FriendRequestNotificationPanel;
import CustomJPanels.NotificationTextPanel;
import CustomJPanels.RequestsPanel;
import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;

public class NotificationWindow extends JFrame{
    private JPanel panel1;
    private JPanel notificationPanel;
    private JScrollPane friendsScroll;
    private JPanel friendsPanel;
    private DataManager<UserRelations> userRelationsDataManager;
    private DataManager<Notification> notificationDataManager;
    private String userId;
    private DataManager<Profile> profileDataManager;
    private NotificationWindow notificationWindow;

    public NotificationWindow(DataManager<User> userDataManager, DataManager<UserRelations> userRelationsDataManager,DataManager<Notification> notificationDataManager,String userId,DataManager<Profile> profileDataManager){
        this.userId = userId;
        this.profileDataManager = profileDataManager;
        this.notificationDataManager = notificationDataManager;
        this.userRelationsDataManager = userRelationsDataManager;
         notificationWindow = this;
        setContentPane(panel1);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Notifications");

        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        populateRequestsList(userDataManager);
        populateAcceptedNotificationList(userDataManager);
        populateAcceptedGroupNotificationList();
        populateUsersPostsNotificationList(userDataManager);
        populateGroupsPostsNotificationList();

        friendsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        setVisible(true);

    }
    public void populateRequestsList(DataManager<User> userDataManager) {
        // Simulate data for demonstration
        if(userRelationsDataManager.getDataById(userId).getFriendRequestsList() == null)
            return;
        for (int i = 0; i < userRelationsDataManager.getDataById(userId).getFriendRequestsList().size(); i++) {
            String senderId = userRelationsDataManager.getDataById(userId).getFriendRequestsList().get(i);
            // Create a PostPanel for each post
            FriendRequestNotificationPanel requestsPanel = new FriendRequestNotificationPanel(userRelationsDataManager , userId , senderId , notificationWindow , userDataManager,profileDataManager, notificationDataManager);

            // Add padding and border to each PostPanel
            requestsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsPanel.add(requestsPanel);

        }


        // Revalidate and repaint the container to apply updates
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }
    public void populateAcceptedNotificationList(DataManager<User> userDataManager) {
        // Simulate data for demonstration
        if(notificationDataManager.getDataById(userId).getAcceptedUserRequestsNotification() == null)
            return;
        for(int i =0 ; i<notificationDataManager.getDataById(userId).getAcceptedUserRequestsNotification().size();i++) {
            System.out.println(notificationDataManager.getDataById(userId).getAcceptedUserRequestsNotification().get(i) +" accepted your friend request");
            String accepterId =notificationDataManager.getDataById(userId).getAcceptedUserRequestsNotification().get(i);
            String accepterName = userDataManager.getDataById(accepterId).getUsername();

            // Create a PostPanel for each post
            NotificationTextPanel notificationTextPanel = new NotificationTextPanel(accepterName+" have accepted your friend request");

            // Add padding and border to each PostPanel
            notificationTextPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

            // Add the PostPanel to the container
            friendsPanel.add(notificationTextPanel);



        }


        // Revalidate and repaint the container to apply updates
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }
    public void populateAcceptedGroupNotificationList() {
        // Simulate data for demonstration
        if(notificationDataManager.getDataById(userId).getAcceptedInGroupNotification() == null)
            return;
        for(int i =0 ; i<notificationDataManager.getDataById(userId).getAcceptedInGroupNotification().size();i++) {

            String accepterName =notificationDataManager.getDataById(userId).getAcceptedInGroupNotification().get(i);

            // Create a PostPanel for each post
            NotificationTextPanel notificationTextPanel = new NotificationTextPanel(accepterName+" have accepted your join request");

            // Add padding and border to each PostPanel
            notificationTextPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsPanel.add(notificationTextPanel);



        }


        // Revalidate and repaint the container to apply updates
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }
    public void populateUsersPostsNotificationList(DataManager<User> userDataManager) {
        // Simulate data for demonstration
        if(notificationDataManager.getDataById(userId).getPostsNotification() == null)
            return;
        for(int i =0 ; i<notificationDataManager.getDataById(userId).getPostsNotification().size();i++) {

            String accepterid =notificationDataManager.getDataById(userId).getPostsNotification().get(i);
            String accepterName = userDataManager.getDataById(accepterid).getUsername();

            // Create a PostPanel for each post
            NotificationTextPanel notificationTextPanel = new NotificationTextPanel(accepterName+" have posted a new post");

            // Add padding and border to each PostPanel
            notificationTextPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsPanel.add(notificationTextPanel);



        }


        // Revalidate and repaint the container to apply updates
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }
    public void populateGroupsPostsNotificationList() {
        // Simulate data for demonstration
        if(notificationDataManager.getDataById(userId).getGroupsPostsNotification() == null)
            return;
        for(int i =0 ; i<notificationDataManager.getDataById(userId).getGroupsPostsNotification().size();i++) {

            String accepterName =notificationDataManager.getDataById(userId).getGroupsPostsNotification().get(i);

            // Create a PostPanel for each post
            NotificationTextPanel notificationTextPanel = new NotificationTextPanel(accepterName+" have a new post");

            // Add padding and border to each PostPanel
            notificationTextPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            friendsPanel.add(notificationTextPanel);



        }


        // Revalidate and repaint the container to apply updates
        friendsPanel.revalidate();
        friendsPanel.repaint();
    }

}

