package PhaseTwo.GroupManagement.Frontend;

import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseTwo.GroupManagement.Backend.Group;
import PhaseTwo.GroupManagement.Backend.GroupPosts;
import PhaseTwo.NotificationSystem.Backend.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class CreatePostWindow extends JFrame {
    private JTextArea postText;
    private JButton publishButton;
    private JButton addImageButton;
    private JPanel imagePanel;
    private JPanel main;
    private String imagePath;

    public CreatePostWindow(String groupName , String userId, DataManager<GroupPosts> groupPostsDataManager, DataManager<Group> groupDataManager ,DataManager<Notification> notificationDataManager) {
        setContentPane(main);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Share your thoughts");
        setResizable(false);
        setVisible(true);



        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = postText.getText();
                if(text != null) {

                    groupPostsDataManager.getDataByName(groupName).addPost(userId, text, imagePath);
                    groupPostsDataManager.saveData();

                    for(int i =0 ; i<groupDataManager.getDataByName(groupName).getGroupMembers().size();i++)
                    {
                        String memberId = groupDataManager.getDataByName(groupName).getGroupMembers().get(i);
                        if(!memberId.equals(userId)) {

                            notificationDataManager.getDataById(memberId).addgroupsPostsNotification(groupName);
                        }
                    }
                    notificationDataManager.saveData();

                    setVisible(false);
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "Please enter some text");

            }
        });
        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File image = chooser.getSelectedFile();
                    imagePath = image.getAbsolutePath();
                    displayImages(imagePath);
                }
            }
        });
    }
    private void displayImages(String imagePath) {
        // Clear the panel (optional, if you want to refresh the panel each time)
        imagePanel.removeAll();

        // Loop through each addImageButton path and add a JLabel with the addImageButton

            // Load and resize the addImageButton
            ImageIcon imageIcon = resizeImage(new ImageIcon(imagePath), 120, 120);

            // Create a JLabel to display the addImageButton
            JLabel imageLabel = new JLabel(imageIcon);

            // Set the preferred size for the JLabel
            imageLabel.setPreferredSize(new Dimension(120, 120));

            // Add the label to the JPanel
            imagePanel.add(imageLabel);


        // Revalidate and repaint the panel to update the display
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    // Resize addImageButton helper function
    private ImageIcon resizeImage(ImageIcon originalImageIcon, int width, int height) {
        Image image = originalImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
