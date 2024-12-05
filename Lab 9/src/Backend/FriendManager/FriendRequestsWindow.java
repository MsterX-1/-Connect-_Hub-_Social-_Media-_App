package Backend.FriendManager;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendRequestsWindow extends JFrame {
    private JPanel mainPanel;
    private JPanel friendRequests;
    private JLabel imageLabel;
    private JButton declineButton;
    private JButton acceptButton;
    private JLabel usernameLabel;

    public FriendRequestsWindow(String userId , UserDatabase userDatabase) {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Friend requests");
        usernameLabel.setText(userDatabase.getUserById("2").getUsername());
        setVisible(true);
        // Load the image from file
        ImageIcon imageIcon = new ImageIcon(userDatabase.getUserById("2").getProfilePhotoPath()); // Replace with your image path

        // Resize the image to fit the label's size
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        // Set the resized image to the label
        imageLabel.setIcon(new ImageIcon(image));

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            FriendManager friendManager = new FriendManager();
            friendManager.acceptFriendRequest("2",userId);
            friendManager.writeToDatabase("friend");
            }
        });
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.loadFromFile();
        new FriendRequestsWindow("1" , userDatabase);
    }


}
