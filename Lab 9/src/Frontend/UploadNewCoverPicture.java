package Frontend;

import Backend.Databases.DataManager;
import Backend.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UploadNewCoverPicture extends JFrame {
    private JButton uploadButton;
    private JButton returnButton;
    private JPanel panel;
    private JLabel imageLabel;
    private ProfileManagementPage profileManagementPage;
    private EditProfile editProfile;
    private String userId;

    public UploadNewCoverPicture(ProfileManagementPage profileManagementPage, EditProfile editProfile, String userId, DataManager<User>userDataManager) {
        this.profileManagementPage = profileManagementPage;
        this.editProfile = editProfile;
        this.userId = userId;
        setTitle("Upload Cover Picture");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setContentPane(panel);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());



        imageLabel = new JLabel();
        panel.add(imageLabel, BorderLayout.CENTER);



        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = selectImage();
                if(path != null) {
                    //save
                    userDataManager.getDataById(userId).setCoverPhotoPath(path);
                    userDataManager.saveData();
                    profileManagementPage.setVisible(true);
                    setVisible(false);
                }else {

                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile.setVisible(true);
                setVisible(false);
            }
        });
    }

    private String selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // عشان الuser ميختارش حاجه غير ال files
        fileChooser.setDialogTitle("Choose Cover Picture");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int returnValue = fileChooser.showOpenDialog(this); //بيبين ال file chooser

        if(returnValue == JFileChooser.APPROVE_OPTION) { // لو ال user داس علي الSelect حيخش في الif
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Select a new Profile Picture"+selectedFile.getAbsolutePath());
            String newProfilePicPath = selectedFile.getAbsolutePath();
            if (!imageFilter.accept(selectedFile)) {
                JOptionPane.showMessageDialog(this, "Please select a valid image file.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // save here
            displayImage(selectedFile.getAbsolutePath()); // Show image preview
            UploadImage(selectedFile); // Update the image in ProfileManagementPage
            return newProfilePicPath;
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            System.out.println("File selection has been cancelled");
        }
        else{
            System.out.println("An error occurred during file selection");
            return null;
        }
        return null;
    }
    private void UploadImage(File file) {
        System.out.println("Uploading Cover Picture: " + file.getName());
        // Call the method to update the cover photo instead of the profile picture
        profileManagementPage.updateCoverPicture(file.getAbsolutePath());
    }

    private void displayImage(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();

        Image scaledImage = image.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage); // Create a new scaled ImageIcon

        imageLabel.setIcon(imageIcon); // Update the label to display the selected image
        revalidate(); // Refresh layout
        repaint(); // Repaint the frame
    }

}