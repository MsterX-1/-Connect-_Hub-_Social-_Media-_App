package PhaseTwo.NotificationSystem.Backend;

import java.util.ArrayList;

public class Notification {
    private String userId;
    private ArrayList<String> receivedFriendRequestNotification;
    private ArrayList<String> sentFriendRequestNotification;

    public Notification() {
        receivedFriendRequestNotification = new ArrayList<>();
        sentFriendRequestNotification = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void receivedFriendRequest(String senderId) {
        receivedFriendRequestNotification.add(senderId);
    }

    public void sentFriendRequest(String recipientId) {
        sentFriendRequestNotification.add(recipientId);
    }

}
