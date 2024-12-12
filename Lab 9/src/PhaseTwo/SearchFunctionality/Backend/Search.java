package PhaseTwo.SearchFunctionality.Backend;

import Databases.DataManager;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;

import java.util.ArrayList;

public class Search {
    String userId;
    ArrayList<String> userFriendsIds =new ArrayList<>();
    ArrayList<String> userNonFriendsIds =new ArrayList<>();
    ArrayList<String> groupsName = new ArrayList<>();
    DataManager<User> userdataManagr;




    public Search(String userId ,DataManager<User> userdataManagr,DataManager<UserRelations> userRelationsDataManager,DataManager<Group> groupDataManager){ //constructor
        this.userId = userId;
        this.userdataManagr = userdataManagr;
        loadfriendsIds(userRelationsDataManager);
        loadNonfriendsIds(userdataManagr);
        loadGoupsNames(groupDataManager);
    }
    public void loadfriendsIds(DataManager<UserRelations> userRelationsDataManager){
       this.userFriendsIds =userRelationsDataManager.getDataById(userId).getFriendsList(); // get user's friends ids from user releations database
    }
    public void loadNonfriendsIds(DataManager<User> userdataManagr){// get user's nonfriends ids by comparing user's friends ids with all user database
       if(userFriendsIds!=null && userNonFriendsIds!=null)
        for(int i =0 ; i<userdataManagr.getAllData().size() ; i++)
        {
            String Userid = userdataManagr.getAllData().get(i).getUserId();
            if(!userFriendsIds.contains(Userid))
                userNonFriendsIds.add(Userid);
        }
    }
    public void loadGoupsNames(DataManager<Group> groupDataManager){ // get groups' names from groups database
        if(groupsName!=null)
        for(int i =0 ; i<groupDataManager.getAllData().size();i++)
        this.groupsName.add(groupDataManager.getAllData().get(i).getGroupName());
    }


    public ArrayList<String> sreachFormUserFriends(String userInput){
        ArrayList<String> foundFriendsIds = new ArrayList<>();
        if(userFriendsIds!=null)
for(int i = 0; i< userFriendsIds.size(); i++)
{
   String userName = userdataManagr.getDataById(userFriendsIds.get(i)).getUsername();  // get the user name(user friend) by id from user database
    if(userName.equalsIgnoreCase(userInput)|| userName.toLowerCase().startsWith(userInput.toLowerCase()) )   // checks wheather the user name start with or the same as user input
foundFriendsIds.add(userFriendsIds.get(i));
}
return  foundFriendsIds;
    }
    public ArrayList<String> sreachFormNonFriends(String userInput){
        ArrayList<String> foundNonFriendsIds = new ArrayList<>();
        if(userNonFriendsIds!=null)
        for(int i = 0; i< userNonFriendsIds.size(); i++)
        {
            String userName = userdataManagr.getDataById(userNonFriendsIds.get(i)).getUsername(); // get the user name(user nonfriend) by id from user database
            if(userName.equalsIgnoreCase(userInput) || userName.toLowerCase().startsWith(userInput.toLowerCase()))    // checks wheather the user name start with or the same as user input
                foundNonFriendsIds.add(userFriendsIds.get(i));

        }
        return foundNonFriendsIds;
    }
    public ArrayList<String> sreachFromGoups(String userInput){
        ArrayList<String> foundgroups = new ArrayList<>();
        if(groupsName!=null)
        for(int i =0; i<groupsName.size();i++)
        {
            if(groupsName.get(i).equalsIgnoreCase(userInput) || groupsName.get(i).toLowerCase().startsWith(userInput.toLowerCase()) )  // checks every name in the groups arraylist  wheather start with or the same as user input
                foundgroups.add(groupsName.get(i));
        }
        return foundgroups;
    }

}
