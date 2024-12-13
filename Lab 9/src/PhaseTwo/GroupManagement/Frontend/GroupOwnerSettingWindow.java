package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupOwnerSettingWindow extends JFrame {
    private JButton promoteDemoteMemberButton;
    private JButton removeMemberButton;
    private JButton manageGroupPostsButton;
    private JButton deleteTheGroupButton;
    private JPanel panel;
    private JButton changeGroupPhotoButton;
    private JButton checkMemberShipRequestsButton;

    public GroupOwnerSettingWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, GroupWindow groupWindow, DataManager<GroupRole> groupRoleDataManager, Newsfeed newsfeed) {
        setTitle("Group Owner Setting");
        GroupOwnerSettingWindow groupSetting = this;
        setContentPane(panel);
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        deleteTheGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the group?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    // Logic for deleting the group
                    groupDataManager.removeData(groupDataManager.getDataByName(groupName));
                    groupRoleDataManager.removeData(groupRoleDataManager.getDataByName(groupName));

                    // return to the news feed
                    newsfeed.setVisible(true);
                    groupWindow.setVisible(false);
                    setVisible(false);



                    JOptionPane.showMessageDialog(null, "Group deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Group deletion Canceled", "Canceled", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveMemberAsOwnerWindow(groupName,groupDataManager,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
        changeGroupPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UploadNewGroupPicture(groupDataManager,groupName,groupWindow,groupSetting);
                setVisible(false);
            }
        });
        manageGroupPostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        promoteDemoteMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PromoteAndDemoteMembersWindow(groupName,groupDataManager,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
        checkMemberShipRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckMemberShipRequestWindow(groupName,groupDataManager,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
    }
}
