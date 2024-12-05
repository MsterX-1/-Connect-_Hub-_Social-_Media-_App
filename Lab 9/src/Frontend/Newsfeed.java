package Frontend;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Newsfeed extends JFrame {
    private JPanel panel1;
    private JButton refreshButton;
    private JButton profileManagmentButton;
    private JButton createPostButton;
    private JLabel imagelabel;
    private JButton createStoryButton;
    private JPanel imagePlace;
    private UserDatabase userDatabase;
    private String userId;

    public Newsfeed(UserDatabase userDatabase, String userId) {
        this.userDatabase = userDatabase;
        this.userId = userId;

        // Load the user database
        userDatabase.loadFromFile();

        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel1);


        int index = userDatabase.getUserIndexById(userId);
        String pathPhotoProfile = userDatabase.getUsers().get(index).getProfilePhotoPath();


        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();

        int scaledWidth = imagelabel.getWidth();
        int scaledHeight = imagelabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);

        imagelabel.setIcon(new ImageIcon(scaledImage));

        imagelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                new PRofileManagementPage(userId, userDatabase).setVisible(true);
                setVisible(false);




            }
        });
        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        profileManagmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }

    public static void main(String[] args) {
        UserDatabase userdatabase = new UserDatabase();
        new Newsfeed(userdatabase, "7");
    }


}
