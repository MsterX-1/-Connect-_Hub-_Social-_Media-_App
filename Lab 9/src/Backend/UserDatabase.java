package Backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDatabase {
    private ArrayList<User> users = new ArrayList<User>();

    public void loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File("C:\\Users\\Legion\\OneDrive\\Documents\\GitHub\\-Connect-_Hub-_Social-_Media-_App\\Lab 9\\src\\Backend\\Users.json");

        // Check if the file exists and is not empty
        if (file.exists() && file.length() > 0) {
            try {
                User[] usersArray = objectMapper.readValue(file, User[].class);

                // Add the users from the array to the ArrayList
                for (User user : usersArray) {
                    this.users.add(user);
                }

                // Print out users if needed for debugging
                for (User user : this.users) {
                    System.out.println(user); // This will call the User's toString() method if it's overridden
                }

            } catch (IOException e) {
                System.err.println("Error loading JSON file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Handle the case when the file is empty or doesn't exist
            if (!file.exists()) {
                System.out.println("File does not exist. Creating a new one.");
            } else {
                System.out.println("The file is empty.");
            }
            // Optionally initialize the users list if the file is empty or doesn't exist
            this.users = new ArrayList<>();
        }
    }

    public boolean userExistence(User inputUser){
        for(int i =0 ; i <users.size();i++) {
            if(inputUser.getUsername().equals(users.get(i).getUsername())  || inputUser.getEmail().equals(users.get(i).getEmail()))
                return true;
        }
        return false;
    }

    public String hashPasswords(String password) throws NoSuchAlgorithmException {
        MessageDigest encrypt = MessageDigest.getInstance("SHA-256");
        byte[] hashedPasswordInBytes = encrypt.digest(password.getBytes());
        String hashedPasswordInHex = "";
        for (int i =0 ; i< hashedPasswordInBytes.length ; i++) {
            String hex = Integer.toHexString(0xff & hashedPasswordInBytes[i]); // Unsigned treatment
            hashedPasswordInHex=hashedPasswordInHex+hex;
        }
        return hashedPasswordInHex;
    }

    public boolean userLogin(String userInputName , String userInputPassword) throws NoSuchAlgorithmException {
        for(int i =0 ; i <this.users.size() ; i++)
        {
            try {

                if(userInputName.equals(users.get(i).getUsername()) && hashPasswords(userInputPassword).equals(users.get(i).getPassword()) ) {
                    users.get(i).setStatus(true);
                    return true;

                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        }
        return false;
    }
    public int getUserIndexByNameAndPass(String userInputName , String userInputPassword) throws NoSuchAlgorithmException {
        for(int i =0 ; i <this.users.size() ; i++)
        {
            try {

                if(userInputName.equals(users.get(i).getUsername()) && hashPasswords(userInputPassword).equals(users.get(i).getPassword()) ) {
                    return i;

                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        }
        return 0;
    }
    public int getUserIndexById(String userId)  {
        for(int i =0 ; i <this.users.size() ; i++)
        {
            if(userId.equals(users.get(i).getUserId()) ) {
                return i;
            }
        }
        return 0;
    }

    public boolean addUser(User inputUser) throws NoSuchAlgorithmException {
        if(userExistence(inputUser))
            return false;
        else {
            inputUser.setPassword(hashPasswords(inputUser.getPassword()));
            users.add(inputUser);
            return true;
        }
    }

    public  void saveToFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            User[] usersArray = new  User[users.size()];
            for(int i =0 ;i <users.size() ; i++) {
                usersArray[i] =  users.get(i);
            }
            objectMapper.writeValue(new File("C:\\Users\\Legion\\OneDrive\\Documents\\GitHub\\-Connect-_Hub-_Social-_Media-_App\\Lab 9\\src\\Backend\\Users.json"), usersArray);

        } catch (IOException e) {
            System.err.println("Error while writing in JSON file: ");

        }
    }
    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (id.equals(user.getUserId()))
                return user;
        }
        return null;
    }
}