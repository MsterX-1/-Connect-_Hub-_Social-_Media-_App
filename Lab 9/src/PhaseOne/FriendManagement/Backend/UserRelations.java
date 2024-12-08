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
            JOptionPane.showMessageDialog(null, "user added to blocklist!", "Success", JOptionPane.INFORMATION_MESSAGE);

        }
        else
            JOptionPane.showMessageDialog(null, "User already blocked!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void unblockUser(String userId){
        //checks blocklist for id to unblock
        if(blockList.contains(userId)) {
            blockList.remove(userId);
            JOptionPane.showMessageDialog(null, "user removed from blocklist!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "User is not in your blocklist!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void removeFriend(String friendId){
        //remove friend from user's friends list and add that friend to suggestions list
        if(friendsList.contains(friendId)) {
            friendsList.remove(friendId);
            suggestionsList.add(friendId);
            JOptionPane.showMessageDialog(null, "Friend removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "User is not in your friends list!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void sendFriendRequest(String receiverId){
        if(!friendRequestsList.contains(receiverId)) {
            friendRequestsList.add(receiverId);
            pendingRequests.put(receiverId, "pending");
        }
        else
            JOptionPane.showMessageDialog(null, "you have already sent a friend request to this user", "Error", JOptionPane.ERROR_MESSAGE);
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
        userRelationsManager.getDataById(senderId).suggestionsList.add(userId);
        //remove receiver from sender's pending requests
        userRelationsManager.getDataById(senderId).pendingRequests.remove(userId);
        JOptionPane.showMessageDialog(null, "Friend request accepted!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void declineFriendRequest(String senderId){
        friendRequestsList.remove(senderId);
        JOptionPane.showMessageDialog(null, "Friend request declined!", "Success", JOptionPane.INFORMATION_MESSAGE);

    }
}
