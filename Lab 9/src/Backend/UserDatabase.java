package Backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserDatabase {
    private ArrayList<User> users = new ArrayList<User>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public  void loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();


        objectMapper.registerModule(new JavaTimeModule());

        try {
            User[] usersArray = objectMapper.readValue(new File("src/Backend/Users.json"), User[].class);

            // Add the users from the array to the ArrayList
            for (User user : usersArray) {
                this.users.add(user);
            }


            for (User user : this.users) {
                System.out.println(user); // This will call the User's toString() method if it's overridden
            }

        } catch (IOException e) {
            System.err.println("Error loading JSON file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public boolean userExsitance(User inputUser){
        for(int i =0 ; i <users.size();i++)
        {
            if(inputUser.getUserId() == users.get(i).getUserId() && inputUser.getUsername() == users.get(i).getUsername() && inputUser.getEmail() ==  users.get(i).getEmail() )
                return true ;
        }

return false ;
    }
    public void adduser(User inputUser){
        if(userExsitance(inputUser))
            System.out.println("user already exsit");
        else
            this.users.add(inputUser);

    }
    public void deleteuser(User inputUser){
        if(userExsitance(inputUser)) {
            this.users.remove(this.users.indexOf(inputUser));


        }

        else
            System.out.println("user doesnt exsit");

    }

public  User getuserbyId(String id) {

    for (User user : users) {
        if (user.getUserId() == id) {
            return user;
        }
    }
    return null;
}
}