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
    private MainWindow mainWindow;

    public Login(MainWindow mainWindow, DataManager<User> userDataManager) {
        this.mainWindow = mainWindow;
        setVisible(true);
        setContentPane(login);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("login");


        login = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the component is painted properly
                // Set the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Legion\\Downloads\\html-color-codes-color-tutorials.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        login.setLayout(null);
        setContentPane(login);


        backButton.setBounds(10, 500, 100, 29);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setForeground(Color.black);
        backButton.setBackground(Color.white);
        loginButton.setBounds(680, 500, 100, 29);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(Color.black);
        loginButton.setBackground(Color.white);


        JLabel label1 = new JLabel("Username");
        label1.setFont(new Font("Arial", Font.BOLD, 15));
        label1.setBounds(30, 188, 250, 29);
        label1.setForeground(Color.white);
        label1.setBackground(Color.black);
        JLabel label2 = new JLabel("Password");
        label2.setFont(new Font("Arial", Font.BOLD, 15));
        label2.setForeground(Color.white);
        label2.setBackground(Color.black);
        label2.setBounds(30, 300, 250, 29);
        usernametext = new JTextField();
        usernametext.setBounds(400, 188, 250, 29);
        paswordtext = new JPasswordField();
        paswordtext.setBounds(400, 300, 250, 29);


        login.add(backButton);
        login.add(loginButton);
        login.add(usernametext);
        login.add(paswordtext);
        login.add(label1);
        login.add(label2);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = usernametext.getText();
                String userPassword = new String(paswordtext.getPassword()).trim();

                if (userName.isEmpty() || userPassword.isEmpty())
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

                    for (int i = 0; i < hashedPasswordInBytes.length; i++) {
                        String hex = Integer.toHexString(0xff & hashedPasswordInBytes[i]); // Unsigned treatment
                        hashedPasswordInHex = hashedPasswordInHex + hex;
                    }
                    userPassword = hashedPasswordInHex;// password hashed

                    boolean loginSuccessful = false; // To track if the login was successful

                    for (int i = 0; i < userDataManager.getAllData().size(); i++) {
                        if (userDataManager.getAllData().get(i).getUsername().equals(userName) && userDataManager.getAllData().get(i).getPassword().equals(userPassword)) {

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

    private JTextField usernametext;
    private JPanel login;
    private JButton loginButton;
    private JButton backButton;
    private JPasswordField paswordtext;


}