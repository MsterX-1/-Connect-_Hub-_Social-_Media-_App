package Frontend;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JButton Signup;
    private JButton loginButton;
    private JPanel main;
    private UserDatabase userDatabase;

    public MainWindow(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
        userDatabase.loadFromFile();
        setVisible(true);

        // Initialize the main panel with a null layout
        main = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the component is painted properly
                // Set the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Legion\\Downloads\\hand-drawn-communication-background_23-2151634510.avif");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set null layout to position components manually
        main.setLayout(null);
        setContentPane(main);

        // Set window properties
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Main");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel();
        label.setText("Welcome to Connect Hub!");
        label.setBounds(250, 200, 400, 40);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.white);
        // Create the buttons
        loginButton = new JButton("Login");
        Signup = new JButton("Sign Up");


        loginButton.setBounds(450, 350, 100, 30);
        loginButton.setBackground(Color.white);
        loginButton.setForeground(Color.black);

        Signup.setBackground(Color.white);
        Signup.setForeground(Color.black);
        Signup.setBounds(200, 350, 100, 30); // x, y, width, height

        // Add buttons to the panel
        main.add(loginButton);
        main.add(Signup);
        main.add(label);
        // Action listeners for the buttons
        MainWindow mainWindow = this;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When Login button is clicked, open Login window and hide the main window
                new Login(mainWindow, userDatabase);
                setVisible(false);
            }
        });

        Signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When Sign Up button is clicked, open Signup window and hide the main window
                new signup(mainWindow, userDatabase);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        // Assuming the UserDatabase class is properly defined
        UserDatabase userDatabase = new UserDatabase();
        new MainWindow(userDatabase);
    }
}