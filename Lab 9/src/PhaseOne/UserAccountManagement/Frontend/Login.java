package PhaseOne.UserAccountManagement.Frontend;

import Databases.DataManager;
import RunProgram.MainWindow;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.UserAccountManagement.Backend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends JFrame {
    private MainWindow mainWindow ;
    public Login(MainWindow mainWindow, DataManager<User> userDataManager){
        this.mainWindow = mainWindow;
        setVisible(true);
        setContentPane(login);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName= textField1.getText();
                String userPassword = new String(passwordField1.getPassword()).trim();

                    if(userName.isEmpty() || userPassword.isEmpty())
                        JOptionPane.showMessageDialog(login, "ALL fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);

                    else {
                        //Hash Algorithm
                        MessageDigest encrypt = null;
                        try {
                            encrypt = MessageDigest.getInstance("SHA-256");
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                        byte[] hashedPasswordInBytes = encrypt.digest(userPassword.getBytes());
                        String hashedPasswordInHex = "";

                        for (int i =0 ; i< hashedPasswordInBytes.length ; i++) {
                            String hex = Integer.toHexString(0xff & hashedPasswordInBytes[i]); // Unsigned treatment
                            hashedPasswordInHex=hashedPasswordInHex+hex;
                        }
                             userPassword = hashedPasswordInHex;// password hashed

                        boolean loginSuccessful = false; // To track if the login was successful

                        for (int i = 0; i < userDataManager.getAllData().size(); i++) {
                            if (userDataManager.getAllData().get(i).getUsername().equals(userName) && userDataManager.getAllData().get(i).getPassword().equals(userPassword)){

                                // Login successful
                                JOptionPane.showMessageDialog(login, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                                userDataManager.getAllData().get(i).setStatus(true); // Set status to Online
                                String userId = userDataManager.getAllData().get(i).getUserId();
                                userDataManager.saveData();
                                loginSuccessful = true; // Mark login as successful
                                new Newsfeed(userDataManager, userId, mainWindow);
                                setVisible(false);
                                break; // Exit the loop as login is successful
                            }
                        }
                        if (!loginSuccessful) {
                            // If no user matches after the loop
                            JOptionPane.showMessageDialog(login, "The username or password is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                mainWindow.setVisible(true);

            }
        });
    }
    private JTextField textField1;
    private JTextField textField2;
    private JPanel login;
    private JButton loginButton;
    private JButton backButton;
    private JPasswordField passwordField1;
}