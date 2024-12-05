package Backend.ContentCreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import Backend.User;

public class Post extends Content {
    @JsonIgnore
    private static int postCounter;

    public static void setPostCounter(int postCounter) {
        Post.postCounter = postCounter;
    }

    public Post() {
        postCounter++;
        setContentId("Post" + postCounter);
        setAuthorId("user.getUserId()");

    }


}
