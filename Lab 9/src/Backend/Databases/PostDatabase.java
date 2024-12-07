package Backend.Databases;

import Backend.ContentCreation.Post;
import Backend.Interfaces.Database;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PostDatabase implements Database<Post> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<Post> posts; // Internal list of posts

    public PostDatabase(File filePath) {
        this.filePath = filePath;
        this.posts = new ArrayList<>();
    }

    @Override
    public void loadFromJson() {

        if (!filePath.exists() || filePath.length() == 0) {
            return;             //terminates method if file does not exist or is empty
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Post[] loadedPosts = objectMapper.readValue(filePath, Post[].class);
            posts.clear(); // Clear existing posts to avoid duplication
            posts.addAll(Arrays.asList(loadedPosts));
            System.out.println("Successfully loaded posts from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading posts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, posts);
        } catch (IOException e) {
            System.out.println("Error while saving posts to " + filePath);
        }
    }

    @Override
    public void insertData(Post post) {
        posts.add(post);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(Post post) {
        posts.remove(post);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<Post> getData() {
        return new ArrayList<>(posts); // Return a copy to protect encapsulation
    }
}
