package Frontend;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class Login extends JFrame {
    private MainWindow mainWindow;
    private UserDatabase userDatabase;

    public Login(MainWindow mainWindow, UserDatabase userDatabase) {
        this.mainWindow = mainWindow;
        this.userDatabase = userDatabase;

        setVisible(true);
        setContentPane(login);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("login");

        /// //////////////////////////////////////////////////////////////////////////////

        login = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the component is painted properly
                // Set the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Legion\\Downloads\\abstract-colorful-blurry-background_558873-12978.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };


        login.setLayout(null);
        setContentPane(login);

        // Set window properties
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Main");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JTextField textuser = new JTextField();
        textuser.setBounds(350, 136, 300, 20);

        JTextField textpass = new JTextField();

        textpass.setBounds(350, 286, 300, 20);

        ///   ///////////////////////////////////////////////////////////////////////////
        JLabel username = new JLabel();
        username.setBounds(100, 50, 800, 200);
        username.setText("ENTER THE USERNAME");
        username.setForeground(Color.black);

        username.setFont(new Font("Arial", Font.BOLD, 14));
        ///////////////////////////////////////////////////////////////////////////
        JLabel password = new JLabel();
        password.setBounds(100, 200, 800, 200);
        password.setText("ENTER THE USER PASSWORD");
        password.setForeground(Color.black);
        password.setFont(new Font("Arial", Font.BOLD, 14));
        // Create the buttons
        loginButton = new JButton("LOGIN");
        backButton = new JButton("BACK");


        loginButton.setBounds(600, 500, 100, 30);
        loginButton.setBackground(Color.white);
        loginButton.setForeground(Color.black);

        backButton.setBackground(Color.white);
        backButton.setForeground(Color.black);
        backButton.setBounds(450, 500, 100, 30);

        // Add buttons to the panel
        login.add(loginButton);
        login.add(backButton);
        login.add(password);
        login.add(username);
login.add(textuser);
login.add(textpass);

        /// //////////////////////////////////////////////////////////////////////////////

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = textField1.getText();
                String userPassword = new String(passwordField1.getPassword()).trim();
                if (userName != null && userPassword != null) {
                    if (userName.isEmpty() || userPassword.isEmpty())
                        JOptionPane.showMessageDialog(login, "ALL fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        try {
                            if (userDatabase.userLogin(userName, userPassword)) {
                                JOptionPane.showMessageDialog(login, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                                userDatabase.saveToFile();
                                // next page
                                String userId = userDatabase.getUsers().get(userDatabase.getUserIndexByNameAndPass(userName, userPassword)).getUserId();
                                new Newsfeed(userDatabase, userId, mainWindow);

                                setVisible(false);

                            } else
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