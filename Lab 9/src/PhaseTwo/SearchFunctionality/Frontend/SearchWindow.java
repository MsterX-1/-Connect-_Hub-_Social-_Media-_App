package PhaseTwo.SearchFunctionality.Frontend;

import CustomJPanels.FriendPanels.FriendPanel;
import CustomJPanels.FriendPanels.FriendsUIManager;
import CustomJPanels.GroupPanels.GroupPanel;
import CustomJPanels.GroupPanels.GroupSuggestionsPanel;
import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
import PhaseTwo.SearchFunctionality.Backend.Search;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class SearchWindow extends JFrame{


    private JButton searchButton;
    private JTextField searchField;
    private JPanel searchPanel;
    private JPanel main;
    private JScrollPane scrollPane;
    private JPanel searchContainer;
    String userInput;


    public SearchWindow(String userId , DataManager<User> userdataManager, DataManager<UserRelations> userRelationsDataManager, DataManager<Group> groupDataManager , FriendsUIManager friendsUIManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager, Newsfeed newsfeed) {

        setContentPane(main);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("search");
        setResizable(false);
        setVisible(true);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = searchField.getText();
                if(userInput == null)
                {
                    JOptionPane.showMessageDialog(null,"the search bar is empty" );
                } else if (userInput.isEmpty()){
                    JOptionPane.showMessageDialog(null,"the search bar is empty" );
                }else {
                    searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.Y_AXIS));

                    Search search = new Search(userId,userdataManager,userRelationsDataManager,groupDataManager);
                    ArrayList<String> foundFriendsId = search.searchFormUserFriends(userInput);
                    ArrayList<String> foundNonFriendsId= search.searchFormNonFriends(userInput);
                    ArrayList<String> foundGroups = search.searchFromGroups(userInput);
                    searchContainer.removeAll();

                    if(foundFriendsId !=null && !foundFriendsId.isEmpty()) {

                        for (int i = 0; i < foundFriendsId.size(); i++) {
                            String friendId = foundFriendsId.get(i);
                            String userName = userdataManager.getDataById(friendId).getUsername();
                            String imagePath = profileDataManager.getDataById(friendId).getProfilePhotoPath();
                            FriendPanel friendPanel = new FriendPanel(userName, imagePath, userId, friendId, userRelationsDataManager, userdataManager,profileDataManager);

                            // Add padding and border to each PostPanel
                            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                            // Add the PostPanel to the container
                            searchContainer.add(friendPanel);

                        }

                    }
                    if(foundNonFriendsId !=null && !foundNonFriendsId.isEmpty()) {
                        for (int i = 0; i < foundNonFriendsId.size(); i++) {
                            String nonFriendId = foundNonFriendsId.get(i);
                            String userName = userdataManager.getDataById(nonFriendId).getUsername();
                            String imagePath = profileDataManager.getDataById(nonFriendId).getProfilePhotoPath();
                            FriendPanel friendPanel = new FriendPanel(userName, imagePath, userId, nonFriendId, userRelationsDataManager,userdataManager,profileDataManager);

                            // Add padding and border to each PostPanel
                            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                            // Add the PostPanel to the container
                            searchContainer.add(friendPanel);

                        }
                    }
                    if(foundGroups !=null && !foundGroups.isEmpty()) {
                        for (int i = 0; i < foundGroups.size(); i++) {
                            String groupName = foundGroups.get(i);
                            String groupImagePath = groupDataManager.getDataByName(groupName).getGroupPhotoPath();
                            if(groupDataManager.getDataByName(groupName).getGroupMembers().contains(userId)){

                                // Create a group Panel for each post
                                GroupPanel groupPanel = new GroupPanel(groupName, groupImagePath,userId, groupDataManager,groupRoleDataManager,userdataManager,profileDataManager,newsfeed);

                                // Add padding and border to each PostPanel
                                groupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                                // Add the PostPanel to the container
                                searchContainer.add(groupPanel);
                            }
                        }
                    }
                    if(foundGroups !=null && !foundGroups.isEmpty()) {
                        for (int i = 0; i < foundGroups.size(); i++) {
                            String groupName = foundGroups.get(i);
                            String groupImagePath = groupDataManager.getDataByName(groupName).getGroupPhotoPath();
                            if(!groupDataManager.getDataByName(groupName).getGroupMembers().contains(userId)){

                                // Create a group Panel for each post
                                GroupSuggestionsPanel groupSuggestionsPanel = new GroupSuggestionsPanel(groupName, groupImagePath,userId, groupDataManager,groupRoleDataManager);

                                // Add padding and border to each PostPanel
                                groupSuggestionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                                // Add the PostPanel to the container
                                searchContainer.add(groupSuggestionsPanel);
                            }
                        }
                    }


                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
            }
        });
    }


}
