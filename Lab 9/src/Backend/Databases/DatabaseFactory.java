package Backend.Databases;


import Backend.Interfaces.Database;

import java.io.File;

public class DatabaseFactory {
    public static <T> Database<T> createDatabase(String type) {
        if (type.equalsIgnoreCase("user")) {
           // return (Database<T>) new UserDatabase();
        } else if (type.equalsIgnoreCase("post")) {
            return (Database<T>) new PostDatabase(new File("C:\\Users\\Legion\\OneDrive\\Documents\\GitHub\\-Connect-_Hub-_Social-_Media-_App\\Lab 9\\src\\Backend\\Databases\\postsDB.json"));
        } else if (type.equalsIgnoreCase("story")) {
            return (Database<T>) new StoryDatabase(new File("Lab 9/src/Backend/Databases/storiesDB.json"));
        }
        throw new IllegalArgumentException("No database implementation for type: " + type);
    }
}
