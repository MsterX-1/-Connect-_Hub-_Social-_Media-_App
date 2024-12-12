//package CustomJPanels;
//
//import javax.swing.*;
//import java.awt.*;
//public class ProfilePanel extends JPanel {
//    public ProfilePanel(String text, String imagePath) {
//        // Set layout manager to align components horizontally
//        setLayout(new BorderLayout(10, 10)); // Add a gap of 10 pixels between components
//        setSize(new Dimension(200, 50));
//        // Create JLabel for the image
//        JLabel imageLabel = new JLabel();
//        ImageIcon icon = new ImageIcon(imagePath); // Load the image from the given path
//        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image to fit nicely
//        imageLabel.setIcon(new ImageIcon(scaledImage));
//
//        // Create JLabel for the text
//        JLabel textLabel = new JLabel(text);
//        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text
//
//        // Add components to the panel
//        add(imageLabel, BorderLayout.WEST); // Add image on the left
//        add(textLabel, BorderLayout.CENTER); // Add text on the right
//    }
//}