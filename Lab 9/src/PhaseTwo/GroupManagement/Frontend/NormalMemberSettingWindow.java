package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NormalMemberSettingWindow extends JFrame {
    private JButton postContentButton;
    private JButton leaveTheGroupButton;
    private JPanel panel;

    public NormalMemberSettingWindow(String groupName, DataManager<Group> groupDataManager, DataManager<GroupRole> groupRoleDataManager, String userId, Newsfeed newsfeed, GroupWindow groupWindow, DataManager<Notification> notificationDataManager) {
        setTitle("Normal User Setting");
        setContentPane(panel);
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        leaveTheGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to Leave the group?", "Confirm Leaving the Group",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    // Logic for leaving from the group
                    groupDataManager.getDataByName(groupName).getGroupMembers().remove(userId);
                    groupRoleDataManager.getDataByName(groupName).getGroupMembers().remove(userId);
                    groupDataManager.saveData();
                    groupRoleDataManager.saveData();

                    // return to the news feed
                    newsfeed.setVisible(true);
                    groupWindow.setVisible(false);
                    setVisible(false);



                    JOptionPane.showMessageDialog(null, "You Left the Group!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Group Leaving Canceled", "Canceled", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        postContentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i =0 ; i<groupDataManager.getDataByName(groupName).getGroupMembers().size();i++)
                {
                    String memberId = groupDataManager.getDataByName(groupName).getGroupMembers().get(i);
                    if(!memberId.equals(userId)) {
                        System.out.println("member id "+memberId);
                        System.out.println("user id "+userId);

                        notificationDataManager.getDataById(memberId).addgroupsPostsNotification(groupName);
                    }
                }
                notificationDataManager.saveData();

            }
        });
    }
}
