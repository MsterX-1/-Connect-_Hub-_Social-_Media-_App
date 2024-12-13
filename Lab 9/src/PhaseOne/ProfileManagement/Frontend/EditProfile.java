package PhaseOne.ProfileManagement.Frontend;
import Databases.DataManager;
import Databases.DataManagerFactory;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfile extends JFrame {

    private JLabel profilePhotoLabel;
    private JLabel coverPhotoLabel;
    private JButton ChangeProfilePicButton;
    private JButton ChangePassButton;
    private JButton ChangeBioButton;
    private JButton ChangeCoverPicButton;
    private JPanel panel;
    private JButton returnButton;
    private ProfileManagementPage pRofileManagementPage;
    private String userId;


    public EditProfile(ProfileManagementPage pRofileManagementPage, String userId, JLabel bioLabel) {
        this.pRofileManagementPage = pRofileManagementPage;
        this.userId = userId;
        EditProfile editProfile = this;
        setTitle("Edit Profile");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(panel);
        setVisible(true);
        DataManager<User> userDataManager = DataManagerFactory.getDataManager("user");
        DataManager<Profile> profileDataManager = DataManagerFactory.getDataManager("profile");


        ChangeProfilePicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new UploadNewProfilePicture(pRofileManagementPage,editProfile,userId,userDataManager,profileDataManager).setVisible(true);
                setVisible(false);
            }
        });
        ChangeCoverPicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UploadNewCoverPicture(pRofileManagementPage,editProfile,userId,userDataManager,profileDataManager).setVisible(true);
                setVisible(false);
            }
        });

        ChangePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassWindow(pRofileManagementPage,editProfile,userId,userDataManager).setVisible(true);
                setVisible(false);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 pRofileManagementPage.setVisible(true);
                 setVisible(false);
            }
        });
        ChangeBioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bio;
                bio=JOptionPane.showInputDialog(null,"Enter New Bio: ", "Info", JOptionPane.INFORMATION_MESSAGE);
                if (bio == null) {
                    // User clicked "Cancel"
                    JOptionPane.showMessageDialog(null, "Bio update canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                } else if (bio.trim().isEmpty()) {
                    // User entered an empty string
                    JOptionPane.showMessageDialog(null, "Bio cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // User entered valid input
                    bioLabel.setText(bio);
                    profileDataManager.getDataById(userId).setBio(bio);
                    profileDataManager.saveData();
                    JOptionPane.showMessageDialog(null, "Bio updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

}
