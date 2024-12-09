package Databases;


import Databases.Content.PostDatabase;
import Databases.Content.StoryDatabase;
import Databases.Profile.ProfileDatabase;
import Databases.Relations.RelationDatabase;
import Databases.User.UserDatabase;
import Interfaces.Database;

import java.io.File;

public class DatabaseFactory {
    public static <T> Database<T> createDatabase(String type) {
        if (type.equalsIgnoreCase("user")) {
            return (Database<T>) new UserDatabase(new File("Lab 9/src/Databases/User/Users.json"));
        } else if (type.equalsIgnoreCase("post")) {
            return (Database<T>) new PostDatabase(new File("Lab 9/src/Databases/Content/postsDB.json"));
        } else if (type.equalsIgnoreCase("story")) {
            return (Database<T>) new StoryDatabase(new File("Lab 9/src/Databases/Content/storiesDB.json"));
        } else if (type.equalsIgnoreCase("relations")) {
            return (Database<T>) new RelationDatabase(new File("Lab 9/src/Databases/Relations/relationsDB.json"));
        } else if (type.equalsIgnoreCase("profile")) {
            return (Database<T>) new ProfileDatabase(new File("Lab 9/src/Databases/Profile/profilesDB.json"));
        }
        throw new IllegalArgumentException("No database implementation for type: " + type);
    }
}
