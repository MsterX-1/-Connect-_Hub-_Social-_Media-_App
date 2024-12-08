package PhaseOne.FriendManagement.Frontend;

import Databases.DataManager;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendMangerWindow1 extends JFrame{
    private JPanel panel1;
    private JButton sendRequestButton;
    private JButton friendsButton;
    private JButton blocksButton;
    private JButton suggestionsButton;
    private JPanel FriendsMangerW;
    private JButton checkRequestsButton;
    private String currentUserId ;

   public FriendMangerWindow1(DataManager<User>userDataManager, String currentUserId ){
        this.currentUserId = currentUserId;
        FriendMangerWindow1 friendMangerWindow1 = this;
        setVisible(true);
        setContentPane(FriendsMangerW);
        setSize(new Dimension(800, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("FriendsManger");



       blocksButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String blockdUserId;
               blockdUserId=JOptionPane.showInputDialog(null,"Enter id you want to block: ", "Info", JOptionPane.INFORMATION_MESSAGE);
               if (blockdUserId == null) {
                   // User clicked "Cancel"
                   JOptionPane.showMessageDialog(null, "block is canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
               } else if (blockdUserId.trim().isEmpty()) {
                   // User entered an empty string
                   JOptionPane.showMessageDialog(null, "id can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
               } else {
                   // User entered valid input
                   blockUser(currentUserId , blockdUserId,userDataManager);

               }
           }
       });

       sendRequestButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String RequestedFriendId;
               RequestedFriendId=JOptionPane.showInputDialog(null,"Enter id you want to send a friend request to: ", "Info", JOptionPane.INFORMATION_MESSAGE);
               if (RequestedFriendId == null) {
                   // User clicked "Cancel"
                   JOptionPane.showMessageDialog(null, "friend request is canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
               } else if (RequestedFriendId.trim().isEmpty()) {
                   // User entered an empty string
                   JOptionPane.showMessageDialog(null, "id can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
               } else {
                   // User entered valid input
                   sendFriendRequest(currentUserId , RequestedFriendId,userDataManager);

               }
              
           }
       });
       checkRequestsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
             //  new FriendRequestsWindow(currentUserId,userDatabase,friendMangerWindow1);///////////////////
           }
       });
   }
    public void sendFriendRequest(String currentUserId  , String receiver,DataManager<User> userDataManager) {
        ArrayList<String> receiverFriendRequests = userDataManager.getDataById(receiver).getFriendsRequestsIds();
        if (receiverFriendRequests.contains(currentUserId)) {
            JOptionPane.showMessageDialog(null, "You have already sent friend request to this user.");


        } else if (userDataManager.getDataById(receiver).getFriendsIds().contains(currentUserId)) {
            JOptionPane.showMessageDialog(null, "You are already friends with this user.");

        } else if(userDataManager.getDataById(receiver).getBlockedIds().contains(currentUserId)) {
            JOptionPane.showMessageDialog(null, "You are blocked by this user.");
        }
        else{
            receiverFriendRequests.add(currentUserId);
            userDataManager.getDataById(receiver).setFriendsRequestsIds(receiverFriendRequests);
            JOptionPane.showMessageDialog(null, "Friend request sent successfully.");

        }
        updateSuggestions(currentUserId,userDataManager);
        updateSuggestions(receiver,userDataManager);
        userDataManager.saveData();

    }
        public void acceptFriendRequest(String currentUserId  , String senderId, DataManager<User> userDataManager) {
            ArrayList<String> currentUserFriendRequests = userDataManager.getDataById(currentUserId).getFriendsRequestsIds();
            ArrayList<String> senderIdFriends = userDataManager.getDataById(senderId).getFriendsIds();
            ArrayList<String> currentUserFriends = userDataManager.getDataById(currentUserId).getFriendsIds();
            currentUserFriendRequests.remove(senderId);
            senderIdFriends.add(currentUserId);
            currentUserFriends.add(senderId);
            userDataManager.getDataById(currentUserId).setFriendsIds(currentUserFriends);
            userDataManager.getDataById(currentUserId).setFriendsRequestsIds(currentUserFriendRequests);
            userDataManager.getDataById(senderId).setFriendsIds(senderIdFriends);
            updateSuggestions(currentUserId,userDataManager);
            updateSuggestions(senderId,userDataManager);
            userDataManager.saveData();


            }
    public void declineFriendRequest(String currentUserId  , String sender,DataManager<User> userDataManager) {
        ArrayList<String> currentUserFriendRequests = userDataManager.getDataById(currentUserId).getFriendsRequestsIds();

        currentUserFriendRequests.remove(currentUserFriendRequests.indexOf(sender));
        userDataManager.getDataById(currentUserId).setFriendsRequestsIds(currentUserFriendRequests);
        updateSuggestions(currentUserId,userDataManager);
        updateSuggestions(sender,userDataManager);
        userDataManager.saveData();

    }
    public void blockUser(String currentUserId  , String reciver, DataManager<User> userDataManager) {
        if(!userDataManager.getAllData().contains(userDataManager.getDataById(reciver))) {
            JOptionPane.showMessageDialog(null, "User is not found");
        } else if(currentUserId.equals(reciver)) {
            JOptionPane.showMessageDialog(null, "You can't block yourself");
        } else {
            ArrayList<String> currentUserBlocks = userDataManager.getDataById(currentUserId).getBlockedIds();
            ArrayList<String> reciverUserBlockedBy = userDataManager.getDataById(reciver).getBlockedByIds();

            // Prevent adding current user to blocked lists if they are trying to block themselves
            if (!currentUserBlocks.contains(reciver)) {
                currentUserBlocks.add(reciver);
                userDataManager.getDataById(currentUserId).setBlockedIds(currentUserBlocks);
            }
            if (!reciverUserBlockedBy.contains(currentUserId)) {
                reciverUserBlockedBy.add(currentUserId);
                userDataManager.getDataById(reciver).setBlockedByIds(reciverUserBlockedBy);
            }

            updateSuggestions(currentUserId,userDataManager);
            updateSuggestions(reciver,userDataManager);
            JOptionPane.showMessageDialog(null, "User blocked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            userDataManager.saveData();
        }
    }

    public void updateSuggestions(String currentUserId,DataManager<User> userDataManager) {

        ArrayList<String> suggestionsOfCurrentUser = userDataManager.getDataById(currentUserId).getSuggestedIds();
        ArrayList<String> newSuggestion = new ArrayList<>();
        ArrayList<String> blockedbyOfCurrentUser = userDataManager.getDataById(currentUserId).getBlockedByIds();
        ArrayList<String> blockedOfCurrentUser = userDataManager.getDataById(currentUserId).getBlockedIds();
        ArrayList<String> friendsOfCurrentUser = userDataManager.getDataById(currentUserId).getFriendsIds();
        ArrayList<String> friendsRequestsOfCurrentUser = userDataManager.getDataById(currentUserId).getFriendsRequestsIds();
        for(int i = 0; i < userDataManager.getAllData().size(); i++) {
            if(!blockedbyOfCurrentUser.contains(userDataManager.getAllData().get(i).getUserId())
                    && !friendsOfCurrentUser.contains(userDataManager.getAllData().get(i).getUserId())
                    && !friendsRequestsOfCurrentUser.contains(userDataManager.getAllData().get(i).getUserId())
                    && !blockedOfCurrentUser.contains(userDataManager.getAllData().get(i).getUserId())
                    && !userDataManager.getAllData().get(i).getUserId().equals(currentUserId)) {
                newSuggestion.add(userDataManager.getAllData().get(i).getUserId());
            }
        }

        userDataManager.getDataById(currentUserId).setSuggestedIds(newSuggestion);
        userDataManager.saveData();


    }
    public void unBlockUser(String currentUserId  , String reciver,DataManager<User> userDataManager) {

        ArrayList<String> currentUserBlocks = userDataManager.getDataById(currentUserId).getBlockedIds();
        ArrayList<String> reciverUserBlockedBy = userDataManager.getDataById(currentUserId).getBlockedByIds();

        currentUserBlocks.remove(reciver);
        userDataManager.getDataById(currentUserId).setBlockedIds(currentUserBlocks);
        reciverUserBlockedBy.remove(currentUserId);
        userDataManager.getDataById(currentUserId).setBlockedByIds(reciverUserBlockedBy);
        updateSuggestions(currentUserId,userDataManager);
        updateSuggestions(reciver,userDataManager);

        userDataManager.saveData();


    }




    }

