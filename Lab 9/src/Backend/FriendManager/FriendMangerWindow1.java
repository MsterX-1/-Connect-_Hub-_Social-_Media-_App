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
    private MainWindow mainWindow ;
    private UserDatabase userDatabase;
    private String currentUserId ;

    FriendMangerWindow1(MainWindow mainWindow, UserDatabase userDatabase, String currentUserId ){
        this.currentUserId = currentUserId;
        this.mainWindow = mainWindow;
        this.userDatabase=userDatabase;
        setVisible(true);
        setContentPane(FriendsMangerW);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("FriendsMangerW");


        friendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        userDatabase.loadFromFile();

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
            userDatabase.loadFromFile();


            }
    public void declineFriendRequest(String currentUserId  , String sender) {
        ArrayList<String> currentUserFriendRequests = userDatabase.getUserById(currentUserId).getFriendsRequestsIds();

        currentUserFriendRequests.remove(currentUserFriendRequests.indexOf(sender));
        userDatabase.getUserById(currentUserId).setFriendsRequestsIds(currentUserFriendRequests);
        updateSuggestions(currentUserId);
        updateSuggestions(sender);
        userDatabase.loadFromFile();

    }
    public void blockUser(String currentUserId  , String reciver) {

        ArrayList<String> currentUserBlocks = userDatabase.getUserById(currentUserId).getBlockedIds();
        ArrayList<String> reciverUserBlockedBy = userDatabase.getUserById(currentUserId).getBlockedByIds();

        currentUserBlocks.add(reciver);
        userDatabase.getUserById(currentUserId).setBlockedIds(currentUserBlocks);
        reciverUserBlockedBy.add(currentUserId);
        userDatabase.getUserById(currentUserId).setBlockedByIds(reciverUserBlockedBy);
        updateSuggestions(currentUserId);
        updateSuggestions(reciver);

        userDatabase.loadFromFile();


    }
    public void updateSuggestions(String currentUserId  ) {

        ArrayList<String> suggestionsOfCurrentUser = userDatabase.getUserById(currentUserId).getSuggestedIds();
        ArrayList<String> blockedbyOfCurrentUser = userDatabase.getUserById(currentUserId).getSuggestedIds();
        ArrayList<String> friendsOfCurrentUser = userDatabase.getUserById(currentUserId).getSuggestedIds();
        ArrayList<String> friendsRequestsOfCurrentUser = userDatabase.getUserById(currentUserId).getSuggestedIds();
        for(int i =0 ;i<userDatabase.getUsers().size() ; i++)
        {
            if(!blockedbyOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId()) && !friendsOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId()) && !friendsRequestsOfCurrentUser.contains(userDatabase.getUsers().get(i).getUserId()))
                suggestionsOfCurrentUser.add(userDatabase.getUsers().get(i).getUserId());

   }
        userDatabase.getUserById(currentUserId).setSuggestedIds(suggestionsOfCurrentUser);
        userDatabase.loadFromFile();


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

        userDatabase.loadFromFile();


    }




    }

