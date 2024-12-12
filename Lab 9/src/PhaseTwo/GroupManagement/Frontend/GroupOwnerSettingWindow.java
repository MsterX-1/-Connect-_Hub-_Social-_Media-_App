package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupOwnerSettingWindow extends JFrame {
    private JButton promoteMemberButton;
    private JButton demoteMemberButton;
    private JButton removeMemberButton;
    private JButton manageGroupPostsButton;
    private JButton deleteTheGroupButton;
    private JPanel panel;

    public GroupOwnerSettingWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager) {
        setTitle("Group Owner Setting");
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

                    // return to the news feed



                    JOptionPane.showMessageDialog(null, "Group deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Group deletion Canceled", "Canceled", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveMemberFromGroupWindow(groupName,groupDataManager,userDataManager,profileDataManager);
            }
        });
    }
}
