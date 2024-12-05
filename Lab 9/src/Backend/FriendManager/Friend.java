package Backend.FriendManager;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Friend {
    @JsonProperty
    private String ownerId;
    @JsonProperty
    private ArrayList<String> friendsIds;
    @JsonProperty
    private ArrayList<String> friendRequestsIds;
    @JsonProperty
    private ArrayList<String> blockedIds;
    @JsonProperty
    private ArrayList<String> suggestedIds;


    public Friend(String ownerId) {
        this.ownerId = ownerId;
        friendsIds = new ArrayList<>();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<String> getFriendsIds() {
        return friendsIds;
    }

    public void addFriendsIds(String friendId) {
        friendsIds.add(friendId);
    }

    public void removeFriendsIds(String friendId) {
        friendsIds.remove(friendId);
    }

    public ArrayList<String> getFriendRequestsIds() {
        return friendRequestsIds;
    }

    public void addFriendRequestsIds(String senderId) {
        friendRequestsIds.add(senderId);
    }

    public void removeFriendRequestsIds(String senderId) {
        friendRequestsIds.remove(senderId);
    }

    public ArrayList<String> getBlockedIds() {
        return blockedIds;
    }

    public void addBlockedIds(String blockedId) {
        blockedIds.add(blockedId);
    }

    public void removeBlockedIds(String blockedId) {
        blockedIds.remove(blockedId);
    }

    public ArrayList<String> getSuggestedIds() {
        return suggestedIds;
    }

    public void addSuggestedIds(String suggestedId) {
        suggestedIds.add(suggestedId);
    }

    public void removeSuggestedIds(String suggestedId) {
        suggestedIds.remove(suggestedId);
    }

}
