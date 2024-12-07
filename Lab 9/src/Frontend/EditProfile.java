package Frontend;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfile extends JFrame {
    private JTextField bioField;
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
    private UserDatabase userDatabase;
    private JTextField BioField;

    public EditProfile(ProfileManagementPage pRofileManagementPage, String userId, UserDatabase userDatabase, JTextField BioField) {
        this.pRofileManagementPage = pRofileManagementPage;
        this.userId = userId;
        this.userDatabase = userDatabase;
        this.BioField = BioField;
        EditProfile editProfile = this;
        setTitle("Edit Profile");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(true);
        setContentPane(panel);
        setVisible(true);

        ChangeProfilePicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new UploadNewProfilePicture(pRofileManagementPage,editProfile,userId,userDatabase).setVisible(true);
                setVisible(false);
            }
        });
        ChangeCoverPicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UploadNewCoverPicture(pRofileManagementPage,editProfile,userId,userDatabase).setVisible(true);
                setVisible(false);
            }
        });
        ChangeBioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ChangePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassWindow(pRofileManagementPage,editProfile,userId,userDatabase).setVisible(true);
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
                    BioField.setText(bio);
                    BioField.setEditable(false);
                    int index = userDatabase.getUserIndexById(userId);
                    userDatabase.getUsers().get(index).setBio(bio);
                    userDatabase.saveToFile();
                    JOptionPane.showMessageDialog(null, "Bio updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

}
