package Backend.FriendManager;

import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Suggestions extends JFrame{
    private JPanel suggestions;
    private JButton backButton;
    private JList list1;
    UserDatabase userDatabase;
    String currentUserId;

    public Suggestions(UserDatabase userDatabase , String currentUserId, FriendMangerWindow1 friendMangerWindow1){

        suggestions = new JPanel();
        suggestions.setLayout(new BorderLayout()); // Set a layout manager for the panel
        this.userDatabase = userDatabase;
        this.currentUserId = currentUserId;

        // Set up the JFrame
        setVisible(true);
        setContentPane(suggestions);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Suggestions");

        // Create the DefaultListModel and add elements
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i =0 ; i<userDatabase.getUserById(currentUserId).getSuggestedIds().size();i++)
            listModel.addElement(userDatabase.getUserById(currentUserId).getSuggestedIds().get(i));


        // Create the JList with the model
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Optional: allows single selection
        list.setLayoutOrientation(JList.VERTICAL);

        // Put the JList in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(list);

        // Add the JScrollPane to the JPanel
        suggestions.add(scrollPane, BorderLayout.CENTER); // Add it to the center of the panel
        backButton = new JButton("Back");
        suggestions.add(backButton, BorderLayout.SOUTH); // Add it to the bottom of the panel
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                friendMangerWindow1.setVisible(true);
            }
        });


    }
}
