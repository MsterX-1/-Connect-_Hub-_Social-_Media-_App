package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseTwo.GroupManagement.Backend.GroupPosts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageGroupPostsWindow extends JFrame{
    private JButton createPostButton;
    private JButton editPostButton;
    private JButton deletePostButton;
    private JPanel main;

    public ManageGroupPostsWindow(String groupName,String userId) {
        setTitle("Create New Group");
        setContentPane(main);
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        Database<GroupPosts> groupPostsDatabase = DatabaseFactory.createDatabase("groupPost");
        DataManager<GroupPosts> groupPostsDataManager = new DataManager<>(groupPostsDatabase);
        groupPostsDataManager.loadData();

        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreatePostWindow(groupName,userId,groupPostsDataManager);
            }
        });
        editPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new 
            }
        });
        deletePostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
