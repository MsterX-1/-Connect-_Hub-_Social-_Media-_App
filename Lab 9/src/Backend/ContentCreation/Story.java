package Backend.ContentCreation;

import Backend.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Story extends Content {
    @JsonIgnore
    public static int storyCounter;

    public static void setStoryCounter(int storyCounter) {
        Story.storyCounter = storyCounter;
    }


    @Override
    public void publishContent() {
        storyCounter++;
        setContentId("Story " + storyCounter);
        setTimeStamp(LocalDateTime.now());
    }



}
