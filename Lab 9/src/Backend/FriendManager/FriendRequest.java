package Backend.FriendManager;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class FriendRequest {
    @JsonProperty
    private String ownerId;
    @JsonProperty
    private ArrayList<String> friendRequestsIds;

    public FriendRequest(String ownerId){
        this.ownerId = ownerId;
        friendRequestsIds = new ArrayList<>();
    }
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
}
