package PhaseOne.FriendManagement.Frontend;

import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendMangerWindow extends JFrame{
    private JPanel panel1;
    private JButton sendRequestButton;
    private JButton friendsButton;
    private JButton blocksButton;
    private JButton suggestionsButton;
    private JPanel FriendsMangerW;
    private JButton checkRequestsButton;
    private String currentUserId ;

   public FriendMangerWindow(DataManager<User>userDataManager, String currentUserId , DataManager<UserRelations> userRelationsDataManager, DataManager<Profile> profileManager){
       this.currentUserId = currentUserId;
        FriendMangerWindow friendMangerWindow1 = this;
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
//                   blockUser(currentUserId , blockdUserId,userDataManager);
                   //SOLID
                   userRelationsDataManager.getDataById(currentUserId).blockUser(blockdUserId);
                    userRelationsDataManager.saveData();
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
//                   sendFriendRequest(currentUserId , RequestedFriendId,userDataManager);
                   userRelationsDataManager.getDataById(currentUserId).sendFriendRequest(RequestedFriendId , userRelationsDataManager);
                   userRelationsDataManager.saveData();
               }
              
           }
       });
       checkRequestsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
             //  new FriendRequestsWindow(currentUserId,userDataManager,friendMangerWindow1);
               //SOLID
               new FriendRequestsWindow(currentUserId,userDataManager, userRelationsDataManager,profileManager);
           }
       });
   }

    }

