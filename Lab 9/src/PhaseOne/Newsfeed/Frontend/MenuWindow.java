package PhaseOne.Newsfeed.Frontend;

import CustomJPanels.FriendPanels.FriendsUIManager;
import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.FriendManagement.Frontend.FriendRequestsWindow;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.SearchFunctionality.Frontend.SearchWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame{
    private JPanel panel1;
    private JButton sendRequestButton;
    private JButton friendsButton;
    private JButton blocksButton;
    private JButton suggestionsButton;
    private JPanel FriendsMangerW;
    private JButton checkRequestsButton;
    private JButton searchButton;
    private JButton notificationsButton;
    private String currentUserId ;
   public MenuWindow( String currentUserId){
       this.currentUserId = currentUserId;
        setVisible(true);
        setContentPane(FriendsMangerW);
        setSize(new Dimension(800, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("FriendsManger");


       checkRequestsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //SOLID
               new FriendRequestsWindow(currentUserId);
           }
       });
       searchButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new SearchWindow(currentUserId);

           }
       });
       notificationsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               
           }
       });
   }

    }

