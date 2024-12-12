package PhaseTwo.SearchFunctionality.Backend;

import Databases.DataManager;
import Databases.DatabaseFactory;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.UserAccountManagement.Backend.User;

import java.util.ArrayList;

public class Search {
    String userInput;
    String userId;
    ArrayList<String> userFriends;
    ArrayList<String> userNonFriends;
    ArrayList<String> found;



    public Search(String userId,String userInput ,DataManager<User> userdataManagr,DataManager<UserRelations> userRelationsDataManager){
        this.userInput = userInput;
        this.userId = userId;
    }
    public void loadfriendsName(DataManager<UserRelations> userRelationsDataManager){
       this.userFriends=userRelationsDataManager.getDataById(userId).getFriendsList();
    }
    public void loadNonfriendsName(DataManager<User> userdataManagr){
        for(int i =0 ; i<userdataManagr.getAllData().size() ; i++)
        {
            String UserName = userdataManagr.getAllData().get(i).getUsername();
            if(!userFriends.contains(UserName))
                userNonFriends.add(UserName);
        }
    }
    public ArrayList<String> sreachFor(){
        sreachFormUserFriends();
        sreachFormNonFriends();
        return found;
    }
    public void sreachFormUserFriends(){
for(int i =0; i<userFriends.size();i++)
{
    if(userFriends.get(i).equals(userInput) || userFriends.get(i).startsWith(userInput))
found.add(userFriends.get(i));
}
    }
    public void sreachFormNonFriends(){
        for(int i =0; i<userNonFriends.size();i++)
        {
            if(userNonFriends.get(i).equals(userInput) || userNonFriends.get(i).startsWith(userInput))
                found.add(userFriends.get(i));
        }
    }
}
