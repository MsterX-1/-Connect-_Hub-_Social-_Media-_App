package Backend.FriendManager;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;

public class friendsWindow extends JFrame {
    private JList<String> list1;
    private JButton backButton;
    private JPanel friends;

    public friendsWindow() {
        // Initialize the JPanel
        friends = new JPanel();
        friends.setLayout(new BorderLayout()); // Set a layout manager for the panel
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.loadFromFile();
        // Set up the JFrame
        setVisible(true);
        setContentPane(friends);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("FriendsManagerW");

        // Create the DefaultListModel and add elements
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i =0 ; i<userDatabase.getUsers().get(0).getFriendsIds().size();i++)
        listModel.addElement(userDatabase.getUsers().get(0).getFriendsIds().get(i));


        // Create the JList with the model
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Optional: allows single selection
        list.setLayoutOrientation(JList.VERTICAL);

        // Put the JList in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(list);

        // Add the JScrollPane to the JPanel
        friends.add(scrollPane, BorderLayout.CENTER); // Add it to the center of the panel

        // Optionally, you can add a back button
        backButton = new JButton("Back");
        friends.add(backButton, BorderLayout.SOUTH); // Add it to the bottom of the panel
    }

    public static void main(String[] args) {

        new friendsWindow();
    }
}