package Databases;

import Databases.Content.PostDatabase;
import Databases.Content.StoryDatabase;
import Databases.Group.GroupDatabase;
import Databases.Notification.NotificationDatabase;
import Databases.Profile.ProfileDatabase;
import Databases.Relations.RelationDatabase;
import Databases.User.UserDatabase;
import Interfaces.Database;
import java.io.File;

public class DatabaseFactory {
    public static <T> Database<T> createDatabase(String type) {
        switch (type.toLowerCase()) {
            case "user":
                return (Database<T>) new UserDatabase(new File("Lab 9/src/Databases/User/Users.json"));
            case "post":
                return (Database<T>) new PostDatabase(new File("Lab 9/src/Databases/Content/postsDB.json"));
            case "story":
                return (Database<T>) new StoryDatabase(new File("Lab 9/src/Databases/Content/storiesDB.json"));
            case "relations":
                return (Database<T>) new RelationDatabase(new File("Lab 9/src/Databases/Relations/relationsDB.json"));
            case "profile":
                return (Database<T>) new ProfileDatabase(new File("Lab 9/src/Databases/Profile/profilesDB.json"));
            case "group":
                return (Database<T>) new GroupDatabase(new File("Lab 9/src/Databases/Group/GroupsDB.json"));
            case "notification":
                return (Database<T>) new NotificationDatabase(new File("Lab 9/src/Databases/Notification/NotificationsDB.json"));
            default:
                throw new IllegalArgumentException("No database implementation for type: " + type);
        }
    }
}
