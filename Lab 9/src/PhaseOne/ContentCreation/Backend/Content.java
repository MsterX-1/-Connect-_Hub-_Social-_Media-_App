package PhaseOne.ContentCreation.Backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public abstract class Content {
    @JsonProperty
    private String contentId;
    @JsonProperty
    private String authorId;
    @JsonProperty
    private ContentProperties content;
    @JsonProperty
    private LocalDateTime timeStamp;


    public Content() {
        content = new ContentProperties();
    }

    //setter for json
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setContent(ContentProperties content) {
        this.content = content;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAuthorId() {
        return authorId;
    }

    public ContentProperties getContent() {
        return content;
    }

    public String getContentId() {
        return contentId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }


    //content methods

    //adds text
    public void addText(String text) {
        content.setText(text);
    }

    //adds image
    public void addImage(String imagePath) {
        content.getImagePaths().add(imagePath);
    }

    //remove image
    public void removeImage(String imagePath) {
        content.getImagePaths().remove(imagePath);
    }

    //edit image
    public void editImage(String oldImagePath, String newImagePath) {
        removeImage(oldImagePath);
        addImage(newImagePath);
    }

    public abstract void publishContent(String authorId);


}
