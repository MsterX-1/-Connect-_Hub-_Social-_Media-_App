package PhaseOne.FriendManagement.Backend;


import Databases.DataManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRelations {
    String userId;
    private ArrayList<String> friendsList;
    private ArrayList<String> friendRequestsList;
    private ArrayList<String> blockList;
    private ArrayList<String> suggestionsList;
    private HashMap<String , String> pendingRequests;

    public UserRelations(String userId) {
        this.userId = userId;
        friendsList = new ArrayList<>();
        friendRequestsList = new ArrayList<>();
        blockList = new ArrayList<>();
        suggestionsList = new ArrayList<>();
        pendingRequests = new HashMap<>();
    }

    public void setSuggestionsList(ArrayList<String> suggestionsList) {
        this.suggestionsList = suggestionsList;
    }

    public UserRelations(){}
    public String getUserId() {
        return userId;
    }

    public ArrayList<String> getFriendsList() {
        return friendsList;
    }

    public ArrayList<String> getFriendRequestsList() {
        return friendRequestsList;
    }

    public ArrayList<String> getBlockList() {
        return blockList;
    }

    public ArrayList<String> getSuggestionsList() {
        return suggestionsList;
    }
    public HashMap<String , String> getPendingRequests() {
        return pendingRequests;
    }


    public void blockUser(String userId){
        //prevents user from blocking himself
        if(blockList.contains(this.userId))
            return;

        //block user if he is not already in the blocklist
        if(!blockList.contains(userId)) {
            blockList.add(userId);
        }
    }

    public void unblockUser(String userId){
        //checks blocklist for id to unblock
            blockList.remove(userId);
    }

    public void removeFriend(String friendId){
        //remove friend from user's friends list and add that friend to suggestions list
        if(friendsList.contains(friendId)) {
            friendsList.remove(friendId);
            suggestionsList.add(friendId);
        }
    }

    public void sendFriendRequest(String receiverId , DataManager<UserRelations> userRelationsManager){
            //check if a request was already sent
        if(!pendingRequests.containsKey(receiverId)) {
            //adds user to receivers friend requests list
            userRelationsManager.getDataById(receiverId).getFriendRequestsList().add(userId);
            //adds receiver to users pending requests
            pendingRequests.put(receiverId, "pending");
        }
    }

    public void acceptFriendRequest(String senderId, DataManager<UserRelations> userRelationsManager){
        //add sender to receiver's friend list
        friendsList.add(senderId);
        //add receiver to sender's friend list
        userRelationsManager.getDataById(senderId).friendsList.add(userId);
        //remove sender's friend request
        friendRequestsList.remove(senderId);
        //remove sender from suggestions list
        suggestionsList.remove(senderId);
        //remove receiver from sender's suggestions list
        userRelationsManager.getDataById(senderId).suggestionsList.remove(userId);
        //remove receiver from sender's pending requests
        userRelationsManager.getDataById(senderId).pendingRequests.remove(userId);
    }

    public void declineFriendRequest(String senderId , DataManager<UserRelations> userRelationsManager){
        //remove sender id from receiver's friend requests
        friendRequestsList.remove(senderId);
        //remove receiver id from sender's pending list
        userRelationsManager.getDataById(senderId).pendingRequests.remove(userId);

    }
}
