package Frontend;

import Backend.ContentCreation.Post;
import Backend.Databases.DataManager;
import Backend.Databases.DatabaseFactory;
import Backend.Interfaces.Database;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JButton Signup;
    private JButton loginButton;
    private JPanel main;
    private JPanel panelleft;
    private UserDatabase userDatabase;
private ImageIcon icon;
    public MainWindow(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
        userDatabase.loadFromFile();
        setVisible(true);
        setContentPane(main);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Main");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindow mainWindow = this;
      //  loginButton.setIcon(new ImageIcon("C:\\Users\\Legion\\Downloads\\360_F_454099801_32LqElT2W1vgRQ6YRsp3h2IsKQxTCnaG (1).png"));
       // Signup.setIcon(new ImageIcon("C:\\Users\\Legion\\Downloads\\sign-up-buttons-sign-label-speech-bubble-sign-up-vector.png"));



    //    loginButton.setPreferredSize(new Dimension(-1,-1));
       // Signup.setPreferredSize(new Dimension(-1,-1));
        main.setBackground(Color.white);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(mainWindow, userDatabase);
                setVisible(false);
            }
        });

        Signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new signup(mainWindow, userDatabase);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
//        Database<User> userDatabase = DatabaseFactory.createDatabase("user");
//        DataManager<User> userDataManager = new DataManager<>(userDatabase);
//        userDataManager.loadData();
        new MainWindow(userDatabase);
    }
}