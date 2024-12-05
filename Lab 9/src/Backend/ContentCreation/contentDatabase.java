package Backend.ContentCreation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ContentDatabase {
    private ArrayList<Content> contents = new ArrayList<>();
    private File file = new File("Lab 9/src/Backend/ContentCreation/postsDB.json");

    public void addContentToDatabase(Content content) {
        contents.add(content);
    }

    public void writeContentToDatabase() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(file, contents);
        } catch (IOException e) {
            System.out.println("Error while writing to json file");
        }

    }

    public int loadContentFromDatabase() {
        if (!file.exists() || file.length() == 0) {
            return 0;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Content[] content = null;
        try {
            content = objectMapper.readValue(file, Post[].class);
        } catch (IOException e) {
            System.out.println("Error in loading posts from json file");
        }

        for (Content c : content) {
            addContentToDatabase(c);
        }
        //to count posts
        return contents.size();
    }

}
