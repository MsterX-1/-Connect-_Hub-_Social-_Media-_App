package PhaseTwo.GroupManagement.Frontend;

import CustomJPanels.GroupPosts.GroupPostsUIManager;
import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupPosts;
import PhaseTwo.GroupManagement.Backend.GroupRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GroupWindow extends JFrame {
    private JPanel panel;
    private JPanel picturePanel;
    private JButton groupSettingButton;
    private JButton returnToNewsfeedButton;
    private JPanel groupPostsContainer;
    private JLabel descriptionLabel;
    private JLabel groupNameLabel;
    private JScrollPane groupPostsScrollPane;

    public GroupWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager, DataManager<Profile> profileDataManager, DataManager<GroupRole> groupRoleDataManager, String userId, Newsfeed newsfeed) {
        setTitle("Group Profile");
        GroupWindow groupWindow= this;
        setContentPane(panel);
        setVisible(true);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        picturePanel.setLayout(new FlowLayout());
        Database<GroupPosts> groupDatabase = DatabaseFactory.createDatabase("groupPost");
        DataManager<GroupPosts> groupPostsDataManager = new DataManager<>(groupDatabase);
        groupPostsDataManager.loadData();
        Database<UserRelations> userRelationsDatabase = DatabaseFactory.createDatabase("relations");
        DataManager<UserRelations> userRelationsDataManager = new DataManager<>(userRelationsDatabase);
        userRelationsDataManager.loadData();
        GroupPostsUIManager groupPostsUIManager = new GroupPostsUIManager(groupName,groupPostsDataManager,userRelationsDataManager);
        groupPostsUIManager.refreshList(groupPostsContainer,groupPostsScrollPane);
        String description=groupDataManager.getDataByName(groupName).getGroupDescription();
        descriptionLabel.setText(description);
        groupNameLabel.setText(groupName);

        String groupPhotoPath= groupDataManager.getDataByName(groupName).getGroupPhotoPath();
        loadCircularImageToPanel(picturePanel,groupPhotoPath , 300);


        groupSettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOwner(userId,groupName,groupRoleDataManager)){

                    new GroupOwnerSettingWindow(groupName,userId,groupDataManager,userDataManager,profileDataManager,groupWindow,groupRoleDataManager,newsfeed);

                } else if (isAdmin(userId,groupName,groupRoleDataManager)) {

                    new GroupAdminSettingWindow(groupName,userId,groupDataManager,userDataManager,profileDataManager,groupRoleDataManager);
                }else {

                     new NormalMemberSettingWindow(groupName,groupDataManager,groupRoleDataManager,userId,newsfeed,groupWindow);
                }
            }
        });
        returnToNewsfeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newsfeed.setVisible(true);
                setVisible(false);
            }
        });
    }
    public boolean isOwner(String userId , String groupName,DataManager<GroupRole> groupRoleDataManager){
        if (userId.equals(groupRoleDataManager.getDataByName(groupName).getGroupCreator()))
            return true;
        else
            return false;
    }
    public boolean isAdmin(String userId , String groupName,DataManager<GroupRole> groupRoleDataManager) {
        for(int i =0; i<groupRoleDataManager.getDataByName(groupName).getGroupAdmins().size(); i++){
            if (userId.equals(groupRoleDataManager.getDataByName(groupName).getGroupAdmins().get(i))){
                return true;
            }
        }
        return false;
    }
    public void updateGroupPicture(String imagePath) {
        System.out.println("Updating group picture with: " + imagePath);
        loadCircularImageToPanel(picturePanel, imagePath, 300);
    }
    private void loadCircularImageToPanel(JPanel panel, String imagePath, int diameter) {
        try {
            File imageFile = new File(imagePath);
            System.out.println("Attempting to load image from: " + imageFile.getAbsolutePath());

            if (!imageFile.exists()) {
                throw new IOException("Image file not found at: " + imagePath);
            }

            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image image = imageIcon.getImage();


            JPanel circlePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;


                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //عشان اعمل smooth edge



                    Shape circle = new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter);//عشان اعمل شكل circle
                    g2d.setClip(circle);


                    g2d.drawImage(image, 0, 0, diameter, diameter, null);// بعمل Scale لل image عشان تبقي جوه الcircle layout اللي انا لسا عامله
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(diameter, diameter);
                }
            };


            panel.removeAll();
            panel.setLayout(new GridBagLayout()); // عشان احطها في النص

            panel.add(circlePanel); // بضيف الدايره في الpanel
            panel.revalidate();// جدد الlayout
            panel.repaint(); // للرسم مره اخري
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
