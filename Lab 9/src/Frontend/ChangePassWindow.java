package Frontend;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class ChangePassWindow extends JFrame {
    private JPasswordField OldPasswordField;
    private JPasswordField NewpasswordField;
    private JButton returnButton;
    private JButton changePasswordButton;
    private JPanel ChangePassWindowContainer;
    private ProfileManagementPage profileManagementPage;
    private EditProfile editProfile;
    private String userId;
    private UserDatabase userDatabase;
 public ChangePassWindow(ProfileManagementPage profileManagementPage, EditProfile editProfile, String userId, UserDatabase userDatabase) {
     this.profileManagementPage = profileManagementPage;
     this.editProfile = editProfile;
     this.userId = userId;
     this.userDatabase = userDatabase;
     setTitle("Change Password");
     setSize(400, 300);
     setLocationRelativeTo(null);
     setContentPane(ChangePassWindowContainer);


     returnButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             editProfile.setVisible(true);
             setVisible(false);
         }
     });
     changePasswordButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String oldPassword = new String(OldPasswordField.getPassword());
             String newPassword = new String(NewpasswordField.getPassword());
             if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                 JOptionPane.showMessageDialog(ChangePassWindowContainer, "All Fields Must Be Filled", "Error", JOptionPane.ERROR_MESSAGE);
             }else {
                 String hashedInputOldPass;// input of the user changed to hash to check the old pass in the database
                 try {
                     hashedInputOldPass = userDatabase.hashPasswords(oldPassword);
                 } catch (NoSuchAlgorithmException ex) {
                     throw new RuntimeException(ex);
                 }
                 int index = userDatabase.getUserIndexById(userId);
                 String OldUserPassInDatabase = userDatabase.getUsers().get(index).getPassword();
                 if(!hashedInputOldPass.equals(OldUserPassInDatabase)) {
                     JOptionPane.showMessageDialog(ChangePassWindowContainer, "Old Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                 }else {
                     try {
                         String hashedNewPass = userDatabase.hashPasswords(newPassword);
                         userDatabase.getUsers().get(index).setPassword(hashedNewPass);
                         userDatabase.saveToFile();
                         JOptionPane.showMessageDialog(ChangePassWindowContainer, "Password Changed Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                         editProfile.setVisible(true);
                         setVisible(false);
                     } catch (NoSuchAlgorithmException ex) {
                         throw new RuntimeException(ex);
                     }

                 }

             }




         }
     });
 }
}
