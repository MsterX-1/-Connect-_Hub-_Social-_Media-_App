package Backend.FriendManager;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Friend{
    @JsonProperty
    private String ownerId;
    @JsonProperty
    private ArrayList<String> friendsIds;

    public Friend(String ownerId){
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
}
