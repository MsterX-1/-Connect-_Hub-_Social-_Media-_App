package Frontend;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class Login extends JFrame {
    private MainWindow mainWindow ;
    private UserDatabase userDatabase;
    public Login(MainWindow mainWindow, UserDatabase userDatabase){
        this.mainWindow = mainWindow;
        this.userDatabase=userDatabase;

        setVisible(true);
        setContentPane(login);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName= textField1.getText();
                String userPassword = new String(passwordField1.getPassword()).trim();
                if(userName!= null && userPassword!=null)
                {
                    if(userName.isEmpty() || userPassword.isEmpty())
                        JOptionPane.showMessageDialog(login, "ALL fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        try {
                            if(userDatabase.userLogin(userName , userPassword)) {
                                JOptionPane.showMessageDialog(login, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                                userDatabase.saveToFile();
                                // next page
                                String userId = userDatabase.getUsers().get(userDatabase.getUserIndexByNameAndPass(userName,userPassword)).getUserId();
                                new PRofileManagementPage(userId,userDatabase);
                                setVisible(false);


                            }
                            else
                                JOptionPane.showMessageDialog(login, "the username or password is invalid", "Error", JOptionPane.ERROR_MESSAGE);

                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }

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

