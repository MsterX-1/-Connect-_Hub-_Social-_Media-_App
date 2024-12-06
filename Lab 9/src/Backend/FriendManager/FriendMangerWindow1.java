package Backend.FriendManager;

import Backend.UserDatabase;
import Frontend.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendMangerWindow1 extends JFrame{
    private JPanel panel1;
    private JButton friendsRequestsButton;
    private JButton friendsButton;
    private JButton blocksButton;
    private JButton suggestionsButton;
    private JPanel FriendsMangerW;

    private UserDatabase userDatabase;
    private String currentUserId ;

   public FriendMangerWindow1( UserDatabase userDatabase, String currentUserId ){
        this.currentUserId = currentUserId;
FriendMangerWindow1 friendMangerWindow1 = this;
        this.userDatabase=userDatabase;
        setVisible(true);
        setContentPane(FriendsMangerW);
        setSize(new Dimension(800, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("FriendsManger");


        friendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new friendsWindow(userDatabase , currentUserId , friendMangerWindow1);
                setVisible(false);
            }
        });
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
                   blockUser(currentUserId , blockdUserId);

               }
           }
       });
       suggestionsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               for(int i =0 ; i<userDatabase.getUsers().size() ; i++)
                   updateSuggestions(userDatabase.getUsers().get(i).getUserId());
               new Suggestions(userDatabase , currentUserId , friendMangerWindow1);
               setVisible(false);

           }
       });
       friendsRequestsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new FriendRequesrs1(userDatabase , currentUserId , friendMangerWindow1);
               setVisible(false);
           }
       });
   }
    public void sendFriendRequest(String currentUserId  , String reciver) {
        ArrayList<String> reciverFriendRequests = userDatabase.getUserById(reciver).getFriendsRequestsIds();
        if (reciverFriendRequests.contains(currentUserId)) {
            JOptionPane.showMessageDialog(null, "You are already sent friend request with this user.");


        } else if (userDatabase.getUserById(reciver).getFriendsIds().contains(currentUserId)) {
            JOptionPane.showMessageDialog(null, "You are already friend with this user.");

        } else {
            reciverFriendRequests.add(currentUserId);
            userDatabase.getUserById(reciver).setFriendsRequestsIds(reciverFriendRequests);
            JOptionPane.showMessageDialog(null, "Friend request sent succesfully.");

        }
        updateSuggestions(currentUserId);
        updateSuggestions(reciver);
        userDatabase.saveToFile();

    }
        public void acceptFriendRequest(String currentUserId  , String sender) {
            ArrayList<String> currentUserFriendRequests = userDatabase.getUserById(currentUserId).getFriendsRequestsIds();
            ArrayList<String> senderFriends = userDatabase.getUserById(sender).getFriendsRequestsIds();
            ArrayList<String> currentUserFriends = userDatabase.getUserById(currentUserId).getFriendsRequestsIds();
            currentUserFriendRequests.remove(currentUserFriendRequests.indexOf(sender));
            senderFriends.add(currentUserId);
            currentUserFriends.add(sender);
            userDatabase.getUserById(currentUserId).setFriendsIds(currentUserFriends);
            userDatabase.getUserById(currentUserId).setFriendsRequestsIds(currentUserFriendRequests);
            userDatabase.getUserById(sender).setFriendsIds(senderFriends);
            updateSuggestions(currentUserId);
            updateSuggestions(sender);
            userDatabase.saveToFile();


            }
    public void declineFriendRequest(String currentUserId  , String sender) {
        ArrayList<String> currentUserFriendRequests = userDatabase.getUserById(currentUserId).getFriendsRequestsIds();

        currentUserFriendRequests.remove(currentUserFriendRequests.indexOf(sender));
        userDatabase.getUserById(currentUserId).setFriendsRequestsIds(currentUserFriendRequests);
        updateSuggestions(currentUserId);
        updateSuggestions(sender);
        userDatabase.saveToFile();

    }
    public void blockUser(String currentUserId  , String reciver) {
        if(!userDatabase.getUsers().contains(userDatabase.getUserById(reciver))) {
            JOptionPane.showMessageDialog(null, "User is not found");
        } else if(currentUserId.equals(reciver)) {
            JOptionPane.showMessageDialog(null, "You can't block yourself");
        } else {
            ArrayList<String> currentUserBlocks = userDatabase.getUserById(currentUserId).getBlockedIds();
            ArrayList<String> reciverUserBlockedBy = userDatabase.getUserById(reciver).getBlockedByIds();

            // Prevent adding current user to blocked lists if they are trying to block themselves
            if (!currentUserBlocks.contains(reciver)) {
                currentUserBlocks.add(reciver);
                userDatabase.getUserById(currentUserId).setBlockedIds(currentUserBlocks);
            }
            if (!reciverUserBlockedBy.contains(currentUserId)) {
                reciverUserBlockedBy.add(currentUserId);
                userDatabase.getUserById(reciver).setBlockedByIds(reciverUserBlockedBy);
            }

            updateSuggestions(currentUserId);
            updateSuggestions(reciver);
            JOptionPane.showMessageDialog(null, "User blocked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            userDatabase.saveToFile();
        }
    }

    public void updateSuggestions(String currentUserId  ) {

        ArrayList<String> suggestionsOfCurrentUser = userDatabase.getUserById(currentUserId).getSuggestedIds();
        ArrayList<String> newSuggestion = new ArrayList<>();
        ArrayList<String> blockedbyOfCurrentUser = userDatabase.getUserById(currentUserId).getBlockedByIds();
        ArrayList<String> blockedOfCurrentUser = userDatabase.getUserById(currentUserId).getBlockedIds();
        ArrayList<String> friendsOfCurrentUser = userDatabase.getUserById(currentUserId).getFriendsIds();
        ArrayList<String> friendsRequestsOfCurrentUser = userDatabase.getUserById(currentUserId).getFriendsRequestsIds();
        for(int i = 0; i < userDatabase.getUsers().size(); i++) {
            if(!blockedbyOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId())
                    && !friendsOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId())
                    && !friendsRequestsOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId())
                    && !blockedOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId())
                    && !userDatabase.getUsers().get(i).getUserId().equals(currentUserId)) {
                newSuggestion.add(userDatabase.getUsers().get(i).getUserId());
            }
        }

        userDatabase.getUserById(currentUserId).setSuggestedIds(newSuggestion);
        userDatabase.saveToFile();


    }
    public void unBlockUser(String currentUserId  , String reciver) {

        ArrayList<String> currentUserBlocks = userDatabase.getUserById(currentUserId).getBlockedIds();
        ArrayList<String> reciverUserBlockedBy = userDatabase.getUserById(currentUserId).getBlockedByIds();

        currentUserBlocks.remove(reciver);
        userDatabase.getUserById(currentUserId).setBlockedIds(currentUserBlocks);
        reciverUserBlockedBy.remove(currentUserId);
        userDatabase.getUserById(currentUserId).setBlockedByIds(reciverUserBlockedBy);
        updateSuggestions(currentUserId);
        updateSuggestions(reciver);

        userDatabase.saveToFile();


    }




    }

