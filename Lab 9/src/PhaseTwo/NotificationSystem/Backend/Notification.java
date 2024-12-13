package PhaseTwo.NotificationSystem.Backend;

import java.util.ArrayList;

public class Notification {
    private String userId;

    private ArrayList<String> acceptedUserRequestsNotification;
    private ArrayList<String> postsNotification;
    private ArrayList<String> acceptedInGroupNotification;
    private ArrayList<String> groupsPostsNotification;

    public Notification() {
        this.postsNotification = new ArrayList<>();
        this.acceptedUserRequestsNotification = new ArrayList<>();
        this.groupsPostsNotification = new ArrayList<>();
        this.acceptedInGroupNotification = new ArrayList<>();
    }
    public Notification(String userId) {
        this.userId = userId;
        this.postsNotification = new ArrayList<>();
        this.acceptedUserRequestsNotification = new ArrayList<>();
        this.groupsPostsNotification = new ArrayList<>();
        this.acceptedInGroupNotification = new ArrayList<>();
    }

    public ArrayList<String> getAcceptedUserRequestsNotification() {
        return acceptedUserRequestsNotification;
    }

    public void setAcceptedUserRequestsNotification(ArrayList<String> acceptedUserRequestsNotification) {
        this.acceptedUserRequestsNotification = acceptedUserRequestsNotification;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getPostsNotification() {
        return postsNotification;
    }

    public void setPostsNotification(ArrayList<String> postsNotification) {
        this.postsNotification = postsNotification;
    }

    public ArrayList<String> getAcceptedInGroupNotification() {
        return acceptedInGroupNotification;
    }

    public void setAcceptedInGroupNotification(ArrayList<String> acceptedInGroupNotification) {
        this.acceptedInGroupNotification = acceptedInGroupNotification;
    }

    public ArrayList<String> getGroupsPostsNotification() {
        return groupsPostsNotification;
    }

    public void setGroupsPostsNotification(ArrayList<String> groupsPostsNotification) {
        this.groupsPostsNotification = groupsPostsNotification;
    }

    public String getUserId() {
        return userId;
    }

public void addPostToNotification(String userId){

this.postsNotification.add(userId);

    }
    public void addacceptedUserRequestsNotification(String userId){

        this.acceptedUserRequestsNotification.add(userId);

    }
    public void addacceptedInGroupNotification(String userId){

        this.acceptedInGroupNotification.add(userId);

    }
    public void addgroupsPostsNotification(String userId){

        this.groupsPostsNotification.add(userId);

    }


}
