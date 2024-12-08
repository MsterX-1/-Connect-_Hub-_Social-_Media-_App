package Frontend;

import Backend.Databases.DataManager;
import Backend.Databases.DatabaseFactory;
import Backend.Interfaces.Database;
import Backend.User;

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