package PhaseTwo.SearchFunctionality.Backend;

import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;

import java.util.ArrayList;

public class Search {
    String userId;
    ArrayList<String> userFriendsIds = new ArrayList<>();
    ArrayList<String> userNonFriendsIds = new ArrayList<>();
    ArrayList<String> groupsName = new ArrayList<>();
    DataManager<User> userdataManager;


    public Search(String userId, DataManager<User> userdataManager, DataManager<UserRelations> userRelationsDataManager, DataManager<Group> groupDataManager) { //constructor
        this.userId = userId;
        this.userdataManager = userdataManager;
        loadFriendsIds(userRelationsDataManager);
        loadNonFriendsIds(userdataManager);
        loadGroupsNames(groupDataManager);
    }

    public void loadFriendsIds(DataManager<UserRelations> userRelationsDataManager) {
        this.userFriendsIds = userRelationsDataManager.getDataById(userId).getFriendsList(); // get user's friends ids from user relations database
    }

    public void loadNonFriendsIds(DataManager<User> userdataManager) {// get user's non friends ids by comparing user's friends ids with all user database
        if (userFriendsIds != null && userNonFriendsIds != null)
            for (int i = 0; i < userdataManager.getAllData().size(); i++) {
                String Userid = userdataManager.getAllData().get(i).getUserId();
                if (!userFriendsIds.contains(Userid))
                    userNonFriendsIds.add(Userid);
            }
    }

    public void loadGroupsNames(DataManager<Group> groupDataManager) { // get groups' names from groups database
        if (groupsName != null)
            for (int i = 0; i < groupDataManager.getAllData().size(); i++)
                this.groupsName.add(groupDataManager.getAllData().get(i).getGroupName());
    }


    public ArrayList<String> searchFormUserFriends(String userInput) {
        ArrayList<String> foundFriendsIds = new ArrayList<>();
        if (userFriendsIds != null)
            for (int i = 0; i < userFriendsIds.size(); i++) {
                String userName = userdataManager.getDataById(userFriendsIds.get(i)).getUsername();  // get the user name(user friend) by id from user database
                if (userName.equalsIgnoreCase(userInput) || userName.toLowerCase().startsWith(userInput.toLowerCase()))   // checks whether the username start with or the same as user input
                    foundFriendsIds.add(userFriendsIds.get(i));
            }
        return foundFriendsIds;
    }

    public ArrayList<String> searchFormNonFriends(String userInput) {
        ArrayList<String> foundNonFriendsIds = new ArrayList<>();
        if (userNonFriendsIds != null)
            for (int i = 0; i < userNonFriendsIds.size(); i++) {
                String userName = userdataManager.getDataById(userNonFriendsIds.get(i)).getUsername(); // get the user name(user non friend) by id from user database
                if (userName.equalsIgnoreCase(userInput) || userName.toLowerCase().startsWith(userInput.toLowerCase()))    // checks whether the username start with or the same as user input
                    foundNonFriendsIds.add(userFriendsIds.get(i));

            }
        return foundNonFriendsIds;
    }

    public ArrayList<String> searchFromGroups(String userInput) {
        ArrayList<String> foundGroups = new ArrayList<>();
        if (groupsName != null)
            for (int i = 0; i < groupsName.size(); i++) {
                if (groupsName.get(i).equalsIgnoreCase(userInput) || groupsName.get(i).toLowerCase().startsWith(userInput.toLowerCase()))  // checks every name in the groups arraylist  whether start with or the same as user input
                    foundGroups.add(groupsName.get(i));
            }
        return foundGroups;
    }

}
