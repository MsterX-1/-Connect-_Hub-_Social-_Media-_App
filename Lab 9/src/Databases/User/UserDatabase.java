package Databases.User;
import Interfaces.Database;
import Interfaces.IdentifiableDatabase;
import PhaseOne.UserAccountManagement.Backend.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class UserDatabase implements IdentifiableDatabase<User> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<User> users; // Internal list of users

    public UserDatabase(File filePath) {
        this.filePath = filePath;
        this.users = new ArrayList<>();
    }

    @Override
    public void loadFromJson() {

        if (!filePath.exists() || filePath.length() == 0) {
            try {
                // Create a new file if it doesn't exist or is empty
                boolean isCreated = filePath.createNewFile();
                if (isCreated) {
                    System.out.println("File created: " + filePath.getPath());
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
            }
            return;             //terminates method if file does not exist or is empty
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            User[] loadedusers = objectMapper.readValue(filePath, User[].class);
            users.clear(); // Clear existing users to avoid duplication
            users.addAll(Arrays.asList(loadedusers));
            System.out.println("Successfully loaded users from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, users);
        } catch (IOException e) {
            System.out.println("Error while saving users to " + filePath);
        }
    }

    @Override
    public void insertData(User user) {
        users.add(user);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(User user) {
        users.remove(user);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<User> getData() {
        return new ArrayList<>(users); // Return a copy to protect encapsulation
    }

    @Override
    public User getDataById(String id) {
        for (User user : users) {
            if(id.equals(user.getUserId()))
                return user;
        }
        return null;
    }
}
