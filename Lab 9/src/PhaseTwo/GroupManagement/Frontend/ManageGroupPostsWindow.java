package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupPosts;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageGroupPostsWindow extends JFrame{
    private JButton createPostButton;
    private JButton modifyPostWindow;
    private JButton deletePostButton;
    private JPanel main;

    public ManageGroupPostsWindow(String groupName, String userId, DataManager<GroupPosts> groupPostsDataManager, DataManager<Group> groupDataManager , DataManager<Notification> notificationDataManager) {

        setTitle("Create New Group");
        setContentPane(main);
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreatePostWindow(groupName,userId,groupPostsDataManager,groupDataManager,notificationDataManager);
            }
        });
        modifyPostWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyPost(groupName,userId, groupPostsDataManager);
            }
        });

    }
}
