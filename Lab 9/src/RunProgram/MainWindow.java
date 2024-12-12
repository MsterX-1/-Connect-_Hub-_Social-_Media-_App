package RunProgram;

import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseOne.UserAccountManagement.Backend.User;
import PhaseOne.UserAccountManagement.Frontend.Login;
import PhaseOne.UserAccountManagement.Frontend.signup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JButton Signup;
    private JButton loginButton;
    private JPanel main;

    public MainWindow(DataManager<User> userDataManager) {
        setVisible(true);
        setContentPane(main);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Main");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow mainWindow = this;

        main = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the component is painted properly
                // Set the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Legion\\Downloads\\html-color-codes-color-tutorials.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        main.setLayout(null);
        setContentPane(main);
        loginButton = new JButton("Login");
        Signup = new JButton("Sign Up");
        // Set button sizes and positions (adjust as needed)
        loginButton.setBounds(470, 280, 100, 29);// x, y, width, height
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(Color.black);
        loginButton.setBackground(Color.white);
        Signup.setBounds(220, 280, 100, 29); // x, y, width, height
        Signup.setFont(new Font("Arial", Font.BOLD, 15));
        Signup.setForeground(Color.black);
        Signup.setBackground(Color.white);
        main.add(loginButton);
        main.add(Signup);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(mainWindow, userDataManager);
                setVisible(false);
            }
        });

        Signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new signup(mainWindow, userDataManager);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        Database<User> userDatabase = DatabaseFactory.createDatabase("user");
        DataManager<User> userDataManager = new DataManager<>(userDatabase);
        userDataManager.loadData();
        new MainWindow(userDataManager);
    }
}