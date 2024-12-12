package PhaseTwo.GroupManagement.Backend;

import java.util.ArrayList;

public class GroupRole {
private String groupName;
private String groupCreator;
private ArrayList<String> groupAdmins;
private ArrayList<String> groupMembers;
public GroupRole(String groupName, String groupCreator){
    this.groupName = groupName;
    this.groupCreator =groupCreator;
    this.groupAdmins = new ArrayList<>();
    this.groupMembers = new ArrayList<>();
}
public GroupRole(){}

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCreator() {
        return groupCreator;
    }

    public void setGroupCreator(String groupCreator) {
        this.groupCreator = groupCreator;
    }

    public ArrayList<String> getGroupAdmins() {
        return groupAdmins;
    }

    public void setGroupAdmins(ArrayList<String> groupAdmins) {
        this.groupAdmins = groupAdmins;
    }

    public ArrayList<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
