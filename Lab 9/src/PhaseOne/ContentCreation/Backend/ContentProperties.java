package PhaseOne.ContentCreation.Backend;

import java.util.ArrayList;

public class ContentProperties {
    private String text;
    private ArrayList<String> imagePaths;

    public ContentProperties() {
        imagePaths = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }
}
