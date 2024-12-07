//package Backend.ContentCreation;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class ContentDatabase {
//    private int expiredStoriesCounter;
//    private ArrayList<Content> posts = new ArrayList<>();
//    private ArrayList<Content> stories = new ArrayList<>();
//
//    private File postsFile = new File("Lab 9/src/Backend/ContentCreation/postsDB.json");
//    private File storiesFile = new File("Lab 9/src/Backend/ContentCreation/storiesDB.json");
//
//    public void addContentToDatabase(Content content, String contentType) {
//        if(contentType.equals("post"))
//            posts.add(content);
//        else if(contentType.equals("story"))
//            stories.add(content);
//    }
//
//    public ArrayList<Content> getPosts() {
//        return posts;
//    }
//
//    public void writeContentToDatabase(String type) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.registerModule(new JavaTimeModule());
//        try {
//            if (type.equals("post"))
//                objectMapper.writeValue(postsFile, posts);
//            if (type.equals("story"))
//                objectMapper.writeValue(storiesFile, stories);
//        } catch (IOException e) {
//            System.out.println("Error while writing to json");
//        }
//
//    }
//
//    public void loadContentFromDatabase(String type) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        try {
//            if (type.equals("post")) {
//                if (!postsFile.exists() || postsFile.length() == 0) {
//                    return;
//                }
//                Content[] content = objectMapper.readValue(postsFile, Post[].class);
//                posts.addAll(Arrays.asList(content));
//            }
//            if (type.equals("story")) {
//                if (!storiesFile.exists() || storiesFile.length() == 0) {
//                    return;
//                }
//                Content[] content = objectMapper.readValue(storiesFile, Story[].class);
//                for (Content c : content) {
//
//                    if (storyHasExpired(LocalDateTime.now(), c.getTimeStamp()))
//                        expiredStoriesCounter++;
//                    else
//                        stories.add(c);
//                }
//
//            }
//        } catch (IOException e) {
//            System.out.println("Error in loading posts from json postsFile");
//        }
//
//    }
//    public int getLastStoryId(){
//        if(stories.isEmpty())
//            return 0;
//        String id = stories.getLast().getContentId();
//        String[] split = id.split(" ");
//        return Integer.parseInt(split[1]);
//    }
//    public int getLastPostId(){
//        if(posts.isEmpty())
//            return 0;
//        String id = posts.getLast().getContentId();
//        String[] split = id.split(" ");
//        return Integer.parseInt(split[1]);
//    }
//    private boolean storyHasExpired(LocalDateTime currentTime, LocalDateTime timeStamp) {
//        // Calculate the duration between the event time and the current time
//        Duration duration = Duration.between(currentTime, timeStamp);
//
//        // story has expired if true
//        return duration.toHours() >= 24;
//    }
//}
