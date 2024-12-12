package PhaseTwo.SearchFunctionality.Frontend;

import CustomJPanels.FriendPanels.FriendPanel;
import CustomJPanels.FriendPanels.FriendsUIManager;
import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
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
    private JTextField searchFIeld;
    private JScrollPane scrollpane;
    private JPanel searchContainer;
    String userInput;


    public SearchWindow(String userId , DataManager<User> userdataManagr, DataManager<UserRelations> userRelationsDataManager, DataManager<Group> groupDataManager , FriendsUIManager friendsUIManager,DataManager<Profile> profileDataManager) {

        setContentPane(main);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("search");
        setResizable(false);
        setVisible(true);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = searchFIeld.getText();
                if(userInput == null)
                {
                    JOptionPane.showMessageDialog(null,"the search bar is empty" );
                } else if (userInput.isEmpty()){
                    JOptionPane.showMessageDialog(null,"the search bar is empty" );
                }else {
                    Search search = new Search(userId,userdataManagr,userRelationsDataManager,groupDataManager);
                    ArrayList<String> foundFriendsId = search.sreachFormUserFriends(userInput);
                    System.out.println(foundFriendsId);
                    ArrayList<String> foundNonFriendsId= search.sreachFormNonFriends(userInput);
                    ArrayList<String> foundgroups = search.sreachFromGoups(userInput);
                    if(foundFriendsId !=null && !foundFriendsId.isEmpty()) {
                        System.out.println("inside if");
                        searchContainer.removeAll();
                        for (int i = 0; i < foundFriendsId.size(); i++) {
                            System.out.println("inside for loop");
                            String friendId = foundFriendsId.get(i);
                            String userName = userdataManagr.getDataById(friendId).getUsername();
                            String imagePath = profileDataManager.getDataById(friendId).getProfilePhotoPath();
                            FriendPanel friendPanel = new FriendPanel(userName, imagePath, userId, friendId, userRelationsDataManager);

                            // Add padding and border to each PostPanel
                            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                            // Add the PostPanel to the container
                            searchContainer.add(friendPanel);

                        }

                    }
                }
            }
        });
    }


}
