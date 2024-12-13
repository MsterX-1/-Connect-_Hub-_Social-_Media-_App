package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupAdminSettingWindow extends JFrame {
    private JButton checkMembershipRequestsButton;
    private JButton removeMemberButton;
    private JButton managePostsButton;
    private JPanel panel;

    public GroupAdminSettingWindow(String groupName ,String userId, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager) {
        setTitle("Group Admin Setting");
        setContentPane(panel);
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        removeMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveMemberAsAdminWindow(groupName,groupDataManager,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
        checkMembershipRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckMemberShipRequestWindow(groupName,groupDataManager,userDataManager,profileDataManager,groupRoleDataManager);
            }
        });
    }
}
