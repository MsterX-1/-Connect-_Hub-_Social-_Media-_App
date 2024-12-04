package Backend.ContentCreation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class contentDatabase {
    
    File file = new File("C:\\Users\\amrra\\Documents\\GitHub\\-Connect-_Hub-_Social-_Media-_App\\Lab 9\\src\\Backend\\ContentCreation\\contentDB.json");
    protected void contentToJson(Content content) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(file, content);

    }
    protected Content contentFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(file, Post.class);
    }
}
