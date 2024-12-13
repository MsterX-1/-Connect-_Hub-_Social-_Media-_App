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


    public void blockUser(String userId ,  DataManager<UserRelations> userRelationsManager){


        //block user if he is not already in the blocklist
        if(!blockList.contains(userId)) {
            blockList.add(userId);
            //remove from friends list
            friendsList.remove(userId);
            userRelationsManager.getDataById(userId).getFriendsList().remove(this.userId);

            //remove both from suggestions
            suggestionsList.remove(userId);
            userRelationsManager.getDataById(userId).getSuggestionsList().remove(this.userId);
        }
    }

    public void unblockUser(String userId ,  DataManager<UserRelations> userRelationsManager){
        //checks blocklist for id to unblock
            blockList.remove(userId);
        //add to suggestion list
        suggestionsList.add(userId);
        userRelationsManager.getDataById(userId).getSuggestionsList().add(this.userId);

    }

    public void removeFriend(String friendId , DataManager<UserRelations> userRelationsManager ){
        //remove friend both user's friends list and add that friend to both suggestions list
        if(friendsList.contains(friendId)) {
            //remove from friends list
            friendsList.remove(friendId);
            userRelationsManager.getDataById(friendId).getFriendsList().remove(userId);

            //add to suggestion list
            userRelationsManager.getDataById(friendId).getSuggestionsList().add(userId);
            suggestionsList.add(friendId);
        }
    }

    public void sendFriendRequest(String receiverId , DataManager<UserRelations> userRelationsManager){
        //check if user has received a request from the receiver
        if(friendRequestsList.contains(receiverId)) {
            //add both to friends list
            friendsList.add(receiverId);
            userRelationsManager.getDataById(receiverId).getFriendsList().add(userId);

            //remove receiver from requests and remove user from receiver's pending
            userRelationsManager.getDataById(receiverId).getPendingRequests().remove(userId);
            friendRequestsList.remove(receiverId);

            //remove both from suggestions
            suggestionsList.remove(receiverId);
            userRelationsManager.getDataById(receiverId).getSuggestionsList().remove(userId);

            return;
        }


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
