package Backend.FriendManager;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class Suggested {
    @JsonProperty
    private String ownerId;
    @JsonProperty
    private ArrayList<String>  suggestedIds;

    public Suggested(String ownerId){
        this.ownerId = ownerId;
        suggestedIds = new ArrayList<>();
    }
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<String> getsuggestedIds() {
        return suggestedIds;
    }

    public void addSuggestedIds(String suggestedId) {
        suggestedIds.add(suggestedId);
    }
    public void removeSuggestedIds(String suggestedId) {
        suggestedIds.remove(suggestedIds.indexOf(suggestedId));
    }

}
