package PhaseOne.ContentCreation.Backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Duration;
import java.time.LocalDateTime;

public class Story extends Content {
    @JsonIgnore
    public static int storyCounter;

    public static void setStoryCounter(int storyCounter) {
        Story.storyCounter = storyCounter;
    }


    @Override
    public void publishContent(String authorId) {
        storyCounter++;
        setContentId("Story " + storyCounter);
        setAuthorId(authorId);
        setTimeStamp(LocalDateTime.now());
    }


    public static boolean storyHasExpired(LocalDateTime currentTime, LocalDateTime timeStamp) {
        // Calculate the duration between the event time and the current time
        Duration duration = Duration.between(currentTime, timeStamp);

        // story has expired if true
        return duration.toHours() >= 24;
    }


}
