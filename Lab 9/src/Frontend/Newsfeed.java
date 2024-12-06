package Frontend;

import Backend.FriendManager.FriendManager;
import Backend.FriendManager.FriendMangerWindow1;
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
    private JButton FriendMangerbot;
    private JPanel imagePlace;
    private UserDatabase userDatabase;
    private String userId;

    public Newsfeed(UserDatabase userDatabase, String userId,MainWindow mainWindow) {
        this.userDatabase = userDatabase;
        this.userId = userId;
        Newsfeed newsfeed = this;
        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        
        imagelabel.setIcon(new ImageIcon(updateNewsFeedPhoto()));


        imagelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                new PRofileManagementPage(userId, userDatabase,newsfeed,mainWindow).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId,"story");
            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId,"post");
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto();
                imagelabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        FriendMangerbot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendMangerWindow1(userDatabase , userId);

            }
        });
    }
    public Image updateNewsFeedPhoto(){
        int index = userDatabase.getUserIndexById(userId);
        String pathPhotoProfile = userDatabase.getUsers().get(index).getProfilePhotoPath();
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imagelabel.getWidth();
        int scaledHeight = imagelabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }
}
