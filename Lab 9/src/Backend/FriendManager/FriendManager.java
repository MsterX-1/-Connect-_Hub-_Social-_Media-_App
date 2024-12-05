package Backend.FriendManager;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FriendManager {
    private ArrayList<Friend> friends;
//    private ArrayList<User> friendRequests;
//    private ArrayList<User> blockedUsers;
//    private ArrayList<User> suggestedUsers;

    private File friendsFile = new File("Lab 9/src/Backend/ContentCreation/friendsDB.json");
//    private File friendRequestsFile = new File("Lab 9/src/Backend/ContentCreation/friendRequestsDB.json");
//    private File blockedUsersFile = new File("Lab 9/src/Backend/ContentCreation/blockedUsersDB.json");
//    private File suggestedFile = new File("Lab 9/src/Backend/ContentCreation/suggestedDB.json");

    public FriendManager() {
        friends = new ArrayList<>();
//        friendRequests = new ArrayList<>();
//        blockedUsers = new ArrayList<>();
//        suggestedUsers = new ArrayList<>();
    }
//    public void sendFriendRequest(User user) {
//        if (!friends.contains(user) || !blockedUsers.contains(user)) {
//            suggestedUsers.remove(user);
//            friendRequests.add(user);
//        }
//    }

    public void acceptFriendRequest(String senderId , String receiverId) {
        //friendRequests.remove(newFriend);

        Friend friend = new Friend(receiverId);
        friend.addFriendsIds(senderId);
        if (!friends.contains(friend) )
            friends.add(friend);
    }

//    public void declineFriendRequest(User user) {
//        friendRequests.remove(user);
//        suggestedUsers.add(user);
//    }
//
//    public void removeFriend(User user) {
//        if (friends.contains(user))
//            friends.remove(user);
//    }
//
//
//    public void blockUser(User user) {
//        suggestedUsers.remove(user);
//        blockedUsers.add(user);
//    }
//
//    public void unblockUser(User user) {
//        blockedUsers.remove(user);
//    }

    public void writeToDatabase(String databaseType) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            if (databaseType.equals("friend")) {
                objectMapper.writeValue(friendsFile, friends);
            }
//            if (databaseType.equals("friendRequest"))
//                objectMapper.writeValue(friendRequestsFile, friendRequests);
//            if (databaseType.equals("blocked"))
//                objectMapper.writeValue(blockedUsersFile, blockedUsers);
//            if (databaseType.equals("suggestion"))
//                objectMapper.writeValue(suggestedFile, suggestedUsers);
        } catch (IOException e) {
            System.out.println("Error while writing to json");
        }

    }

    public void loadFromDatabase(String databaseType) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            if (databaseType.equals("friend")) {
                if (!friendsFile.exists() || friendsFile.length() == 0) {
                }
                Friend[] friendsArray = objectMapper.readValue(friendsFile,Friend[].class);
                friends.addAll(Arrays.asList(friendsArray));
            }
//            if (databaseType.equals("friendRequest")) {
//                if (!friendRequestsFile.exists() || friendRequestsFile.length() == 0) {
//                }
//                User[] requestsArray = objectMapper.readValue(friendRequestsFile,User[].class);
//                friendRequests.addAll(Arrays.asList(requestsArray));
//            }
//            if (databaseType.equals("blocked")) {
//                if (!blockedUsersFile.exists() || blockedUsersFile.length() == 0) {
//                }
//                User[] blockedUsersArray = objectMapper.readValue(blockedUsersFile,User[].class);
//                blockedUsers.addAll(Arrays.asList(blockedUsersArray));
//            }
//            if (databaseType.equals("suggestion")) {
//                if (!suggestedFile.exists() || suggestedFile.length() == 0) {
//                }
//                User[] suggestionArray = objectMapper.readValue(suggestedFile,User[].class);
//                suggestedUsers.addAll(Arrays.asList(suggestionArray));
//            }
        } catch (IOException e) {
            System.out.println("Error in loading posts from json file");
        }

    }


}
