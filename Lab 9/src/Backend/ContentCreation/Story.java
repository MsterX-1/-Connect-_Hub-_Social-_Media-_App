package Backend.ContentCreation;

import Backend.User;

public class Story extends Content {
    private static int storyCounter;

    public Story(User user) {
        storyCounter++;
        setContentId("Story" + storyCounter);
        setAuthorId(user.getUserId());
    }


}
