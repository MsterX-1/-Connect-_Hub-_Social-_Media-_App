package Backend.Databases;

import Backend.ContentCreation.Story;
import Backend.Interfaces.Database;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StoryDatabase implements Database<Story> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<Story> stories; // Internal list of stories

    public StoryDatabase(File filePath) {
        this.filePath = filePath;
        this.stories = new ArrayList<>();
    }

    @Override
    public void loadFromJson() {

        if (!filePath.exists() || filePath.length() == 0) {
            return;             //terminates method if file does not exist or is empty
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Story[] loadedStories = objectMapper.readValue(filePath, Story[].class);
            stories.clear(); // Clear existing stories to avoid duplication
            for (Story story : loadedStories) {
                if (!Story.storyHasExpired(LocalDateTime.now(), story.getTimeStamp()))
                    stories.add(story);

            }
            System.out.println("Successfully loaded stories from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading stories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, stories);
        } catch (IOException e) {
            System.out.println("Error while saving stories to " + filePath);
        }
    }

    @Override
    public void insertData(Story story) {
        stories.add(story);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(Story story) {
        stories.remove(story);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<Story> getData() {
        return new ArrayList<>(stories); // Return a copy to protect encapsulation
    }

    @Override
    public Story getDataById(String id) {
        for (Story story : stories) {
            if(id.equals(story.getContentId()))
                return story;
        }
        return null;
    }
}

