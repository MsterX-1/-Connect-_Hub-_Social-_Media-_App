package Backend.FriendManager;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendRequesrs1 extends JFrame {
    private JPanel FriendsWindow;
    private JScrollPane RecivedfriendsRequests;
    private JButton backButton;
    private JButton sendFriendRequestButton;
    private JPanel friendRequests;
    private JPanel sendFriendRequest;
    private UserDatabase userDatabase;
    private String currentUserId;

    public FriendRequesrs1(UserDatabase userDatabase, String currentUserId, FriendMangerWindow1 friendMangerWindow1) {
        this.userDatabase = userDatabase;
        this.currentUserId = currentUserId;
        FriendsWindow = new JPanel();
        FriendsWindow.setLayout(new BorderLayout());
        RecivedfriendsRequests = new JScrollPane();
setVisible(true);
        // Create a panel to hold the friend requests
        friendRequests = new JPanel();
        friendRequests.setLayout(new BoxLayout(friendRequests, BoxLayout.Y_AXIS));

        // Add the friend requests to the panel
        for (int i = 0; i < userDatabase.getUserById(currentUserId).getFriendsRequestsIds().size(); i++) {
            // Create a panel for each friend request
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
int index = i;
            // Add the friend's username to the panel
            JLabel usernameLabel = new JLabel(userDatabase.getUserById(currentUserId).getFriendsRequestsIds().get(index));
            requestPanel.add(usernameLabel);

            // Create the accept and decline buttons
            JButton acceptButton = new JButton("Accept");
            JButton declineButton = new JButton("Decline");

            // Add action listeners to the buttons
            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Accept the friend request
                    friendMangerWindow1.acceptFriendRequest(currentUserId, userDatabase.getUserById(currentUserId).getFriendsRequestsIds().get(index));
                    friendRequests.remove(requestPanel);
                    friendRequests.revalidate();
                    friendRequests.repaint();
                }
            });

            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Decline the friend request
                    friendMangerWindow1.declineFriendRequest(currentUserId, userDatabase.getUserById(currentUserId).getFriendsRequestsIds().get(index));
                    friendRequests.remove(requestPanel);
                    friendRequests.revalidate();
                    friendRequests.repaint();
                }
            });

            // Add buttons to the request panel
            requestPanel.add(acceptButton);
            requestPanel.add(declineButton);

            // Add the request panel to the friend requests panel
            friendRequests.add(requestPanel);
        }

        // Add the friend requests panel to the scroll pane
        RecivedfriendsRequests.setViewportView(friendRequests);
        FriendsWindow.add(RecivedfriendsRequests, BorderLayout.CENTER);

        // Add the back button (if needed)
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back button action
                dispose(); // Close the current window
                friendMangerWindow1.setVisible(true); // Show the friend manager window
            }
        });
        FriendsWindow.add(backButton, BorderLayout.SOUTH);

        // Set up the frame
        this.setContentPane(FriendsWindow);
        this.setTitle("Friend Requests");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}