package Databases.Profile;

import Interfaces.IdentifiableDatabase;
import PhaseOne.ProfileManagement.Backend.Profile;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileDatabase implements IdentifiableDatabase<Profile> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<Profile> profiles; // Internal list of Profiles

    public ProfileDatabase(File filePath) {
        this.filePath = filePath;
        this.profiles = new ArrayList<>();
    }

    @Override
    public void loadFromJson() {

        if (!filePath.exists() || filePath.length() == 0) {
            return;             //terminates method if file does not exist or is empty
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Profile[] loadedProfiles = objectMapper.readValue(filePath, Profile[].class);
            profiles.clear(); // Clear existing Profiles to avoid duplication
            profiles.addAll(Arrays.asList(loadedProfiles));
            System.out.println("Successfully loaded profiles from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, profiles);
        } catch (IOException e) {
            System.out.println("Error while saving profiles to " + filePath);
        }
    }

    @Override
    public void insertData(Profile profile) {
        profiles.add(profile);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(Profile profile) {
        profiles.remove(profile);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<Profile> getData() {
        return new ArrayList<>(profiles); // Return a copy to protect encapsulation
    }

    @Override
    public Profile getDataById(String id) {
        for (Profile profile : profiles) {
            if(id.equals(profile.getUserId()))
                return profile;
        }
        return null;
    }
}
