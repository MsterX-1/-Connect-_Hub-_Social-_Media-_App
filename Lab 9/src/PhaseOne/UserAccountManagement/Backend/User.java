package PhaseOne.UserAccountManagement.Backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class User  {
    private String userId;
    private String email;
    private String password;
    private String username;
    private LocalDate dateOfBirth;
    private boolean status;
    private String bio;
    private String profilePhotoPath;
    private String coverPicturePath;

    public User(String userId, String email, String password, String username, LocalDate dateOfBirth, boolean status) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.bio = "Hey There !";
        this.profilePhotoPath = "Lab 9/src/PhotosUsed/DefultPic.jpg";
        this.coverPicturePath = "Lab 9/src/PhotosUsed/DefultPic.jpg";
    }
    public User() {}
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getCoverPhotoPath() {
        return coverPicturePath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPicturePath = coverPhotoPath;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isStatus() {
        return status;
   }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String checkStatus(){
        if(isStatus())
            return "online";
        return "offline";
    }
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", status=" + status +
                ", bio='" + bio + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", coverPicturePath='" + coverPicturePath + '\'' +
                '}';
    }

}