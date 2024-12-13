package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import PhaseTwo.GroupManagement.Backend.GroupPosts;

import javax.swing.*;

public class DeletePost extends JFrame {
    private JPanel panel1;
    private JScrollPane deleteScroll;
    private JPanel deletePanel;

    public DeletePost(String groupName, String userId, DataManager<GroupPosts> groupPostsDataManager) {

        setContentPane(panel1);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Delete Group posts");
        setResizable(false);
        setVisible(true);

    }

}
