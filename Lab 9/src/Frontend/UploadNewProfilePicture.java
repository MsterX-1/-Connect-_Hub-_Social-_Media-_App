package Frontend;

import Backend.UserDatabase;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;




public class UploadNewProfilePicture extends JFrame {
    private JButton uploadButton;
    private JButton returnButton;
    private JPanel panel;
    private JLabel imageLabel;
    private ProfileManagementPage profileManagementPage;
    private EditProfile editProfile;
    private String userId;
    private UserDatabase userDatabase;


    public UploadNewProfilePicture(ProfileManagementPage profileManagementPage, EditProfile editProfile, String userId, UserDatabase userDatabase) {
        this.profileManagementPage = profileManagementPage;
        this.editProfile = editProfile;
        this.userId = userId;
        this.userDatabase = userDatabase;
        setTitle("Upload Profile Picture");
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
            public void actionPerformed(ActionEvent e) { // دا زرار الupload
                String path = selectImage();
        if(path != null) {
            //save
            int index = userDatabase.getUserIndexById(userId);
            userDatabase.getUsers().get(index).setProfilePhotoPath(path);
            userDatabase.saveToFile();
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
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Allow only files
        fileChooser.setDialogTitle("Choose Profile Picture");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) { // If the user selects a file
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected a new Profile Picture: " + selectedFile.getAbsolutePath());
            String newProfilePicPath = selectedFile.getAbsolutePath();
            if (!imageFilter.accept(selectedFile)) {
                JOptionPane.showMessageDialog(this, "Please select a valid image file.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            displayImage(selectedFile.getAbsolutePath()); // Show image preview
            uploadImage(selectedFile); // Update the image in ProfileManagementPage
            return newProfilePicPath;
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            System.out.println("File selection has been cancelled");
        } else {
            System.out.println("An error occurred during file selection");
            return null;
        }
        return null;
    }

    private void uploadImage(File file) {
        System.out.println("Uploading Profile Picture: " + file.getName());

        // Assuming `profileManagementPage` is an instance of ProfileManagementPage
        profileManagementPage.updateProfilePicture(file.getAbsolutePath());


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