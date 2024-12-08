package PhaseOne.ContentCreation.Backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Post extends Content {
    @JsonIgnore
    private static int postCounter;

    public static int getPostCounter() {
        return postCounter;
    }

    public static void setPostCounter(int postCounter) {
        Post.postCounter = postCounter;
    }

    @Override
    public void publishContent(String authorId) {
        postCounter++;
        setContentId("Post " + postCounter);
        setAuthorId(authorId);
        setTimeStamp(LocalDateTime.now());
    }

}
