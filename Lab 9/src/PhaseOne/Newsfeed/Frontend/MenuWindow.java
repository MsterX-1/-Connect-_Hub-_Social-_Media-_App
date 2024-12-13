package PhaseOne.Newsfeed.Frontend;

import CustomJPanels.FriendPanels.FriendsUIManager;
import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.FriendManagement.Frontend.FriendRequestsWindow;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
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

   public MenuWindow(DataManager<User>userDataManager, String currentUserId , DataManager<UserRelations> userRelationsDataManager, DataManager<Profile> profileManager, DataManager<Group> groupDataManager , FriendsUIManager friendsUIManager, DataManager<GroupRole> groupRoleDataManager, Newsfeed newsfeed){
       this.currentUserId = currentUserId;
        MenuWindow friendMangerWindow1 = this;
        setVisible(true);
        setContentPane(FriendsMangerW);
        setSize(new Dimension(800, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("FriendsManger");


       checkRequestsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
             //  new FriendRequestsWindow(currentUserId,userDataManager,friendMangerWindow1);
               //SOLID
               new FriendRequestsWindow(currentUserId,userDataManager, userRelationsDataManager,profileManager);
           }
       });
       searchButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new SearchWindow(currentUserId,userDataManager,userRelationsDataManager,groupDataManager,friendsUIManager,profileManager,groupRoleDataManager,newsfeed);

           }
       });
       notificationsButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               
           }
       });
   }

    }

