package Databases.GroupPosts;

import Interfaces.NameKeyDatabase;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupPosts;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupPostsDatabase implements NameKeyDatabase<GroupPosts> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<GroupPosts> groupPosts;
    public GroupPostsDatabase(File filePath) {
        this.filePath = filePath;
        this.groupPosts = new ArrayList<>();
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
            return;  // Terminates the method if file does not exist or is empty
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            GroupPosts[] loadedGroups = objectMapper.readValue(filePath, GroupPosts[].class);
            groupPosts.clear(); // Clear existing groupPosts to avoid duplication
            groupPosts.addAll(Arrays.asList(loadedGroups));
            System.out.println("Successfully loaded groupPosts from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading groupPosts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, groupPosts);
        } catch (IOException e) {
            System.out.println("Error while saving groupPosts to " + filePath);
        }
    }

    @Override
    public void insertData(GroupPosts Group) {
        groupPosts.add(Group);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(GroupPosts Group) {
        groupPosts.remove(Group);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<GroupPosts> getData() {
        return new ArrayList<>(groupPosts); // Return a copy to protect encapsulation
    }

    @Override
    public GroupPosts getDataByName(String name) {
        for (GroupPosts Group : groupPosts) {
            if(name.equals(Group.getGroupName()))
                return Group;
        }
        return null;
    }

}
