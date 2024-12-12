package Databases.Notification;

import Interfaces.IdentifiableDatabase;
import PhaseTwo.NotificationSystem.Backend.Notification;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationDatabase implements IdentifiableDatabase<Notification> {
    private final File filePath;       // Path to the JSON file
    private final ArrayList<Notification> notifications; // Internal list of notifications


    public NotificationDatabase(File filePath) {
        this.filePath = filePath;
        this.notifications = new ArrayList<>();
    }



    @Override
    public void loadFromJson() {

        if (!filePath.exists() || filePath.length() == 0) {
            return;             //terminates method if file does not exist or is empty
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Notification[] loadedNotifications = objectMapper.readValue(filePath, Notification[].class);
            notifications.clear(); // Clear existing notifications to avoid duplication
            notifications.addAll(Arrays.asList(loadedNotifications));
            System.out.println("Successfully loaded notifications from " + filePath);
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Error parsing JSON file: " + filePath + ". Check file format and structure.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Ensure the file exists and is accessible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error while loading notifications: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writeValue(filePath, notifications);
        } catch (IOException e) {
            System.out.println("Error while saving notifications to " + filePath);
        }
    }

    @Override
    public void insertData(Notification Notification) {
        notifications.add(Notification);
        saveToJson(); // Persist the changes
    }

    @Override
    public void deleteData(Notification Notification) {
        notifications.remove(Notification);
        saveToJson(); // Persist the changes
    }

    @Override
    public ArrayList<Notification> getData() {
        return new ArrayList<>(notifications); // Return a copy to protect encapsulation
    }

    @Override
    public Notification getDataById(String id) {
        for (Notification Notification : notifications) {
            if(id.equals(Notification.getUserId()))
                return Notification;
        }
        return null;
    }
}
