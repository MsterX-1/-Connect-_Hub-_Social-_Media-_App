package Backend.ContentCreation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ContentDatabase {
    private int expiredStoriesCounter;
    private ArrayList<Content> contents = new ArrayList<>();
    private File postsFile = new File("Lab 9/src/Backend/ContentCreation/postsDB.json");
    private File storiesFile = new File("Lab 9/src/Backend/ContentCreation/storiesDB.json");

    public void addContentToDatabase(Content content) {
        contents.add(content);
    }

    public void writeContentToDatabase(String type) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            if (type.equals("post"))
                objectMapper.writeValue(postsFile, contents);
            if (type.equals("story"))
                objectMapper.writeValue(storiesFile, contents);
        } catch (IOException e) {
            System.out.println("Error while writing to json postsFile");
        }

    }

    public int loadContentFromDatabase(String type) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            if (type.equals("post")) {
                if (!postsFile.exists() || postsFile.length() == 0) {
                    return 0;
                }
                Content[] content = objectMapper.readValue(postsFile, Post[].class);
                contents.addAll(Arrays.asList(content));
            }
            if (type.equals("story")) {
                if (!storiesFile.exists() || storiesFile.length() == 0) {
                    return 0;
                }
                Content[] content = objectMapper.readValue(storiesFile, Story[].class);
                for (Content c : content) {

                    if (storyHasExpired(LocalDateTime.now(), c.getTimeStamp()))
                        expiredStoriesCounter++;
                    else
                        contents.add(c);
                }

            }
        } catch (IOException e) {
            System.out.println("Error in loading posts from json postsFile");
        }

        //to count posts
        return contents.size() + expiredStoriesCounter;
    }
    public int getLastStoryId(){
        if(contents.isEmpty())
            return 0;
        String id = contents.getLast().getContentId();
        String[] split = id.split(" ");
        return Integer.parseInt(split[1]);
    }
    private boolean storyHasExpired(LocalDateTime currentTime, LocalDateTime timeStamp) {
        // Calculate the duration between the event time and the current time
        Duration duration = Duration.between(currentTime, timeStamp);

        // story has expired if true
        return duration.toHours() >= 24;
    }
}
