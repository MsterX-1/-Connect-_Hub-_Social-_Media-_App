package PhaseOne.ProfileManagement.Frontend;

import Databases.DataManager;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
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

 public ChangePassWindow(ProfileManagementPage profileManagementPage, EditProfile editProfile, String userId, DataManager<User> userDataManager) {
     this.profileManagementPage = profileManagementPage;
     this.editProfile = editProfile;
     this.userId = userId;
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

                 String hashedInputOldPass= HashingPassword(oldPassword);// password hashed ,input of the user changed to hash to check the old pass in the database

                 String OldUserPassInDatabase = userDataManager.getDataById(userId).getPassword();

                 if(!hashedInputOldPass.equals(OldUserPassInDatabase)) {
                     JOptionPane.showMessageDialog(ChangePassWindowContainer, "Old Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                 }else {

                     String hashedNewPass = HashingPassword(newPassword);// password hashed

                     userDataManager.getDataById(userId).setPassword(hashedNewPass);
                     userDataManager.saveData();
                     JOptionPane.showMessageDialog(ChangePassWindowContainer, "Password Changed Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                     editProfile.setVisible(true);
                     setVisible(false);

                 }

             }




         }
     });
 }
 public String HashingPassword(String password) {
     //Hashing Password Algorithm
     MessageDigest encrypt = null;
     try {
         encrypt = MessageDigest.getInstance("SHA-256");
     } catch (NoSuchAlgorithmException ex) {
         throw new RuntimeException(ex);
     }
     byte[] hashedPasswordInBytes = encrypt.digest(password.getBytes());
     String hashedPasswordInHex = "";

     for (int i =0 ; i< hashedPasswordInBytes.length ; i++) {
         String hex = Integer.toHexString(0xff & hashedPasswordInBytes[i]); // Unsigned treatment
         hashedPasswordInHex=hashedPasswordInHex+hex;
     }
     password = hashedPasswordInHex;// password hashed
     return password;
 }
}
