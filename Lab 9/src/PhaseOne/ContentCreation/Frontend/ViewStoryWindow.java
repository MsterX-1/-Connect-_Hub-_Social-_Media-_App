package PhaseOne.ContentCreation.Frontend;

import javax.swing.*;

public class ViewStoryWindow extends JFrame {


    private JPanel panel1;
    private JScrollPane storyScroll;
    private JPanel storyPanel;


    public ViewStoryWindow(){

        setVisible(true);
        setContentPane(panel1);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("View Story Window");

    }
}
