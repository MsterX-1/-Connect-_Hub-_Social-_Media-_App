package Databases.Relations;

import Interfaces.IdentifiableDatabase;
import PhaseOne.FriendManagement.Backend.UserRelations;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RelationDatabase implements IdentifiableDatabase<UserRelations> {

    private final File filePath;       // Path to the JSON file
    private final ArrayList<UserRelations> relations; // Internal list of relations

    public RelationDatabase(File filePath) {
        this.filePath = filePath;
        this.relations = new ArrayList<>();
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
            UserRelations[] loadedUserRelations = objectMapper.readValue(filePath, UserRelations[].class);
            relations.clear(); // Clear existing relations to avoid duplication
            relations.addAll(Arrays.asList(loadedUserRelations));
            System.out.println("Successfully loaded relations from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading relations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, relations);
        } catch (IOException e) {
            System.out.println("Error while saving relations to " + filePath);
        }
    }

    @Override
    public void insertData(UserRelations UserRelations) {
        relations.add(UserRelations);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(UserRelations UserRelations) {
        relations.remove(UserRelations);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<UserRelations> getData() {
        return new ArrayList<>(relations); // Return a copy to protect encapsulation
    }

    @Override
    public UserRelations getDataById(String id) {
        for (UserRelations UserRelations : relations) {
            if(id.equals(UserRelations.getUserId()))
                return UserRelations;
        }
        return null;
    }
}
