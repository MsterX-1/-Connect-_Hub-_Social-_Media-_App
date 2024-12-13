package Databases.Group;

import Interfaces.IdentifiableDatabase;
import Interfaces.NameKeyDatabase;
import PhaseTwo.GroupManagement.Backend.Group;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupDatabase implements NameKeyDatabase<Group> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<Group> Groups; // Internal list of Groups

    public GroupDatabase(File filePath) {
        this.filePath = filePath;
        this.Groups = new ArrayList<>();
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
            Group[] loadedGroups = objectMapper.readValue(filePath, Group[].class);
            Groups.clear(); // Clear existing Groups to avoid duplication
            Groups.addAll(Arrays.asList(loadedGroups));
            System.out.println("Successfully loaded Groups from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading Groups: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, Groups);
        } catch (IOException e) {
            System.out.println("Error while saving Groups to " + filePath);
        }
    }

    @Override
    public void insertData(Group Group) {
        Groups.add(Group);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(Group Group) {
        Groups.remove(Group);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<Group> getData() {
        return new ArrayList<>(Groups); // Return a copy to protect encapsulation
    }

    @Override
    public Group getDataByName(String name) {
        for (Group Group : Groups) {
            if(name.equals(Group.getGroupName()))
                return Group;
        }
        return null;
    }
}
