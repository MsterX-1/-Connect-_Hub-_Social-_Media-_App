package Backend.ContentCreation;

import Backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

public class contentWindow extends JFrame {
    private JPanel main;
    private JButton publishButton;
    private JButton storyButton;
    private JTextArea description;
    private JButton story;
    private JButton image;
    private JLabel textAreaLabel;
    private JPanel buttonPanel;
    private JPanel textPanel;

    public contentWindow(User user) {
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Share your thoughts");

        setVisible(true);
        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentDatabase contentDatabase = new contentDatabase();
                Content post1 = new Post();
                post1.addText(description.getText());
                post1.publishContent();
                try {
                    contentDatabase.contentToJson(post1);
                    Content post2 = contentDatabase.contentFromJson();
                    post2.addText("mohammed");
                    contentDatabase.contentToJson(post2);
                    System.out.println("json converted");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void main(String[] args) {
        new contentWindow(new User("" ,"","","", LocalDate.now(),true));
    }
}
