package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseTwo.GroupManagement.Backend.GroupPosts;

import javax.swing.*;

public class EditGroupPost extends JFrame {
    private JPanel panel1;
    private JScrollPane editScroll;
    private JPanel container;


    public EditGroupPost(String groupName, String userId, DataManager<GroupPosts> groupPostsDataManager){

        setContentPane(panel1);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Edit group posts");
        setResizable(false);
        setVisible(true);
    }

}
