package Backend.ContentCreation;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public abstract class Content {
    @JsonProperty
    private String contentId;
    @JsonProperty
    private String authorId;
    @JsonProperty
    private ContentProperties content;
    @JsonProperty
    private LocalDate timeStamp;


    public Content() {
        content = new ContentProperties();
    }

    //setter for json
    protected void setContentId(String contentId) {
        this.contentId = contentId;
    }

    protected void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setContent(ContentProperties content) {
        this.content = content;
    }

    //adds text
    protected void addText(String text) {
        content.setText(text);
    }

    //adds image
    protected void addImage(String imagePath) {
        content.getImagePaths().add(imagePath);
    }

    //remove image
    protected void removeImage(String imagePath) {
        content.getImagePaths().remove(imagePath);
    }

    //edit image
    protected void editImage(String oldImagePath, String newImagePath) {
        removeImage(oldImagePath);
        addImage(newImagePath);
    }

    protected void publishContent() {
        timeStamp = LocalDate.now();
    }



}
