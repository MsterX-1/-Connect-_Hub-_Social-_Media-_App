package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseTwo.GroupManagement.Backend.Group;

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
    private JPanel membersContainer;
    private JLabel descriptionLabel;

    public GroupWindow(String groupName, DataManager<Group> groupDataManager, DataManager<User> userDataManager,DataManager<Profile> profileDataManager) {
        setTitle("Group Management");
        setContentPane(panel);
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        picturePanel.setLayout(new FlowLayout());

        String description=groupDataManager.getDataByName(groupName).getGroupDescription();
        descriptionLabel.setText(description);

        String groupPhotoPath= groupDataManager.getDataByName(groupName).getGroupPhotoPath();
        loadCircularImageToPanel(picturePanel,groupPhotoPath , 300);


        groupSettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GroupOwnerSettingWindow(groupName,groupDataManager,userDataManager,profileDataManager);
            }
        });
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
//    public static void main(String[] args) {
//        Database<Group> groupDatabase = DatabaseFactory.createDatabase("group");
//        DataManager<Group> groupDataManager = new DataManager<>(groupDatabase);
//        groupDataManager.loadData();
//        Group g1 = new Group("ELGamdeen");
//        g1.getGroupMembers().add("1");//id
//        g1.getGroupMembers().add("2");
//        g1.getGroupMembers().add("3");
//        groupDataManager.insertData(g1);
//        Database<User> userDatabase = DatabaseFactory.createDatabase("user");
//        DataManager<User> userDataManager = new DataManager<>(userDatabase);
//        userDataManager.loadData();
//        Database<Profile> profileDatabase = DatabaseFactory.createDatabase("profile");
//        DataManager<Profile> profileDataManager = new DataManager<>(profileDatabase);
//        profileDataManager.loadData();
//        GroupWindow window = new GroupWindow("ELGamdeen",groupDataManager,userDataManager,profileDataManager);
//
//    }
}
