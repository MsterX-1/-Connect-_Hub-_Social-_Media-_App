package Backend.ContentCreation;

import Backend.User;

public class Post extends Content {
    private static int postCounter;

    public Post() {
        postCounter++;
        setContentId("Post1");
        setAuthorId("user1");

    }


}
