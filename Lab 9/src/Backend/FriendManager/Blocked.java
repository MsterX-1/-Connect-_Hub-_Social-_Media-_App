package Backend.FriendManager;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Blocked {
    @JsonProperty
    private String ownerId;
    @JsonProperty
    private ArrayList<String> blockedIds;

    public Blocked(String ownerId){
        this.ownerId = ownerId;
        blockedIds = new ArrayList<>();
    }
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<String> getBlockedIds() {
        return blockedIds;
    }

    public void addBlockedIds(String blockedId) {
        blockedIds.add(blockedId);
    }
    public void removeBlockedIds(String blockedId) {
        blockedIds.remove(blockedIds.indexOf(blockedId));
    }

}
