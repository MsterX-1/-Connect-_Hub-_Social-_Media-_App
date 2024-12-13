package PhaseTwo.SearchFunctionality.Frontend;

import CustomJPanels.FriendPanels.FriendPanel;
import CustomJPanels.FriendPanels.FriendsUIManager;
import Databases.DataManager;
import Databases.DataManagerFactory;
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
    private JScrollPane scrollPane;
    private JPanel searchContainer;
    String userInput;


    public SearchWindow(String userId) {
        DataManager<User> userdataManager = DataManagerFactory.getDataManager("user");
        DataManager<Profile> profileDataManager = DataManagerFactory.getDataManager("profile");

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

                    Search search = new Search(userId);
                    ArrayList<String> foundFriendsId = search.searchFormUserFriends(userInput);
                    ArrayList<String> foundNonFriendsId= search.searchFormNonFriends(userInput);
                    ArrayList<String> foundGroups = search.searchFromGroups(userInput);
                    searchContainer.removeAll();

                    if(foundFriendsId !=null && !foundFriendsId.isEmpty()) {

                        for (int i = 0; i < foundFriendsId.size(); i++) {
                            String friendId = foundFriendsId.get(i);
                            String userName = userdataManager.getDataById(friendId).getUsername();
                            String imagePath = profileDataManager.getDataById(friendId).getProfilePhotoPath();
                            FriendPanel friendPanel = new FriendPanel(userName, imagePath, userId, friendId);

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
                            FriendPanel friendPanel = new FriendPanel(userName, imagePath, userId, nonFriendId);

                            // Add padding and border to each PostPanel
                            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                            // Add the PostPanel to the container
                            searchContainer.add(friendPanel);

                        }
                    }


                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                }
            }
        });
    }


}
