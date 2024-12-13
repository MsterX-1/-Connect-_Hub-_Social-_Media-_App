package PhaseTwo.GroupManagement.Backend;

import java.util.ArrayList;

public class Group {
    private String groupName;
    private String groupDescription;
    private String groupPhotoPath;
    private ArrayList<String> groupMembers;
    private ArrayList<String> membershipRequests;
    public Group(String groupName){
        this.groupName = groupName;
        this.groupDescription = "Welcome to "+groupName+" Group!";
        this.groupPhotoPath = "Lab 9/src/PhotosUsed/DefaultGroupPic.jpg";
        this.groupMembers = new ArrayList<>();
        this.membershipRequests = new ArrayList<>();
    }
    public Group(){}

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPhotoPath() {
        return groupPhotoPath;
    }

    public void setGroupPhotoPath(String groupPhotoPath) {
        this.groupPhotoPath = groupPhotoPath;
    }

    public ArrayList<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public ArrayList<String> getMembershipRequests() {
        return membershipRequests;
    }

    public void setMembershipRequests(ArrayList<String> membershipRequests) {
        this.membershipRequests = membershipRequests;
    }
}
