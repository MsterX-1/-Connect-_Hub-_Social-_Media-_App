package PhaseTwo.GroupManagement.Frontend;

import CustomJPanels.GroupPosts.GroupPostsEditorUIManager;
import Databases.DataManager;
import PhaseTwo.GroupManagement.Backend.GroupPosts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePost extends JFrame {
    private JPanel panel1;
    private JScrollPane deleteScroll;
    private JPanel deletePanel;
    private JPanel buttonPanel;
    private JButton refreshButton;

    public DeletePost(String groupName, String userId, DataManager<GroupPosts> groupPostsDataManager) {

        setContentPane(panel1);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setTitle("Delete Group posts");
        setVisible(true);

        GroupPostsEditorUIManager groupPostsEditorUIManager = new GroupPostsEditorUIManager(groupName,groupPostsDataManager);
        groupPostsEditorUIManager.refreshList(deletePanel,deleteScroll);


        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupPostsEditorUIManager.refreshList(deletePanel,deleteScroll);

            }
        });
    }

}
