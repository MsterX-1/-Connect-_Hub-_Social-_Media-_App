package Backend.ContentCreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import Backend.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post extends Content {
    @JsonIgnore
    private static int postCounter;

    public static void setPostCounter(int postCounter) {
        Post.postCounter = postCounter;
    }


    @Override
    public void publishContent() {
        postCounter++;
        setContentId("Post" + postCounter++);
        setTimeStamp(LocalDateTime.now());
    }

}
