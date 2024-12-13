package CustomJPanels;

import javax.swing.*;
import java.awt.*;

public class NotificationTextPanel extends javax.swing.JPanel {

    public NotificationTextPanel(String text) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Horizontal alignment with gaps

        setPreferredSize(new Dimension(300, 20));// Set preferred size for the panel


        // Create JLabel for the text
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the text


        //add text
        add(textLabel);
    }


}
