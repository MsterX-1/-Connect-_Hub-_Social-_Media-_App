package PhaseOne.Newsfeed.Frontend;

import Databases.DataManager;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatNewGroupWindow extends JFrame {
    private JPanel panel;
    private JTextField groupNameField;
    private JTextField descriptionField;
    private JButton createGroupButton;

    public CreatNewGroupWindow(String userId, DataManager<Group> groupDataManager, DataManager<GroupRole> groupRoleDataManager) {
        setTitle("Create New Group");
        setContentPane(panel);
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupNameField.getText();
                String description = descriptionField.getText();

                // Validate group name and description
                if (groupName.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Group Name and Description are required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the group name is unique
                for (Group group : groupDataManager.getAllData()) {
                    if (groupName.equals(group.getGroupName())) {
                        JOptionPane.showMessageDialog(null, "Group Name already used!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit if a duplicate group name is found
                    }
                }

                // Create the group and group role
                Group group = new Group(groupName); // Create new group object
                group.getGroupMembers().add(userId); // Add the creator as a member
                group.setGroupDescription(description); // Set the group description

                GroupRole groupRole = new GroupRole(groupName, userId); // Create group role for the creator

                // Add to data managers and save
                groupDataManager.insertData(group);
                groupRoleDataManager.insertData(groupRole);
                groupDataManager.saveData();
                groupRoleDataManager.saveData();

                // Notify success and close the window
                JOptionPane.showMessageDialog(null, "Group Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false); // Close
            }
        });
    }
}
