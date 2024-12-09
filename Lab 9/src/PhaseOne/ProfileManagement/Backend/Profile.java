package PhaseOne.ProfileManagement.Backend;

public class Profile {
    private String userId;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;

    public Profile(String userId) {
        this.userId = userId;
        this.profilePhotoPath = "Lab 9/src/PhotosUsed/DefultPic.jpg";//Default
        this.coverPhotoPath = "Lab 9/src/PhotosUsed/DefultPic.jpg";//Default
        this.bio = "Hey There !";//Default
    }
    public Profile(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPicturePath) {
        this.coverPhotoPath = coverPicturePath;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
