package PhaseTwo.GroupManagement.Backend;

import PhaseOne.ContentCreation.Backend.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupPosts{
    private String groupName;
    private ArrayList<Post> posts;

    public GroupPosts(String groupName) {
        this.groupName = groupName;
        posts = new ArrayList<>();
    }
    public GroupPosts() {}

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
    public void addPost(String authorId , String text , String imagePath) {
        Post newPost = new Post();
        newPost.setAuthorId(authorId);
        newPost.setContentId("0");
        newPost.addText(text);
        newPost.addImage(imagePath);
        newPost.setTimeStamp( LocalDateTime.now() );
        posts.add(newPost);

    }
    public void editPostText(Post post , String newText ) {
        post.addText(newText);
    }
    public void editPostImage(Post post , String newImagePath ) {
        String oldImagePath = post.getContent().getImagePaths().get(0);
        post.editImage(oldImagePath,newImagePath);
    }
    public void removePost(Post deletedPost) {

            posts.remove(deletedPost);
    }

}
