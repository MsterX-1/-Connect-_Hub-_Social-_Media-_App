package CustomJPanels.GroupPosts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GroupPostPanel extends javax.swing.JPanel {

    private JPanel imagePanel;

    public GroupPostPanel(String text, String imagePaths) {
        // Set layout for the main panel (vertical alignment of text and images)
        setLayout(new BorderLayout(10, 10));

        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align text to the left
        textPanel.setBackground(Color.WHITE);
        JLabel textLabel = new JLabel(text); // Create label with provided text
        textPanel.add(textLabel); // Add label to text panel

        // Image Panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal alignment
        imagePanel.setBackground(Color.GRAY);
        displayImages(imagePaths); // Add images to the image panel

        // Add the text panel and image panel to the PostPanel
        add(imagePanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);

        // Set some padding for the PostPanel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton edit = new JButton("edit post");
        JButton remove = new JButton("remove post ");


        edit.setBackground(Color.white);
        edit.setForeground(Color.black);
        remove.setForeground(Color.black);
        remove.setBackground(Color.white);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });







    }


    private void displayImages(String imagePaths) {
        // Clear the panel (optional, if you want to refresh the panel each time)


        imagePanel.removeAll();

        // Loop through each addImageButton path and add a JLabel with the addImageButton

        // Load and resize the addImageButton
        ImageIcon imageIcon = resizeImage(new ImageIcon(imagePaths), 200, 200);

        // Create a JLabel to display the addImageButton
        JLabel imageLabel = new JLabel(imageIcon);

        // Set the preferred size for the JLabel
        imageLabel.setPreferredSize(new Dimension(200, 200));

        // Add the label to the JPanel
        imagePanel.add(imageLabel);


        // Revalidate and repaint the panel to update the display
//        imagePanel.revalidate();
//        imagePanel.repaint();
    }

    private ImageIcon resizeImage(ImageIcon originalImageIcon, int width, int height) {
        Image image = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }


}
