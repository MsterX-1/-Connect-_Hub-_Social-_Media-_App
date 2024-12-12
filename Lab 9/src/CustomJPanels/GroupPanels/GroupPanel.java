package CustomJPanels.GroupPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupPanel extends JPanel {
    public GroupPanel(String groupName, String groupImagePath) {


        // Set layout manager for horizontal alignment
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment with gaps
        setPreferredSize(new Dimension(600, 70)); // Set preferred size for the panel

        // Create JLabel for the image
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(groupImagePath); // Load the image from the given path
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Create JLabel for the text
        JLabel textLabel = new JLabel(groupName);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Horizontal alignment for buttons
        buttonPanel.setOpaque(false); // Transparent background for consistency

        // Create buttons for Accept and Decline
        JButton viewGroup = new JButton("View Group");
        JButton leaveGroup = new JButton("Leave Group");
        JButton joinGroup = new JButton("Join Group");
        buttonPanel.add(viewGroup);

        //if group member
        buttonPanel.add(leaveGroup);
        //if not group member
        buttonPanel.add(joinGroup);

        // Add components to the main panel
        add(imageLabel);      // Add the image label on the left
        add(textLabel);       // Add the text label in the middle
        add(buttonPanel);     // Add the buttons on the right

        // Add action listeners to buttons
        viewGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        leaveGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        joinGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}