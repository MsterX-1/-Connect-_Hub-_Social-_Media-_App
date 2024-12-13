package PhaseTwo.ProfileViewer;

import CustomJPanels.PostPanels.PostPanel;
import CustomJPanels.PostPanels.PostsUIManager;
import CustomJPanels.PostPanels.ProfilePostsUIManager;
import Databases.DataManager;
import Databases.DataManagerFactory;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileViewer extends JFrame {
    private JPanel mainPanel;
    private JPanel photoPanel;
    private JPanel coverPanel;
    private JScrollPane postScroll;
    private JPanel postsPanel;
    private JLabel biolabel;
    private JLabel userName;
    private String userId;


    public ProfileViewer(String userId) {


        DataManager<Post> postDataManager = DataManagerFactory.getDataManager("post");
        postDataManager.loadData();

        ProfilePostsUIManager postsUIManager = new ProfilePostsUIManager(userId);
        postsUIManager.refreshList(postsPanel,postScroll);

        DataManager<User> userDataManager = DataManagerFactory.getDataManager("user");
        userDataManager.loadData();

        DataManager<Profile> profileDataManager = DataManagerFactory.getDataManager("profile");
        profileDataManager.loadData();

        this.userId = userId;
        setVisible(true);
        setContentPane(mainPanel);
        setTitle("Profile Viewer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);


        userName.setText(userDataManager.getDataById(userId).getUsername());

        coverPanel.setLayout(new FlowLayout());
        photoPanel.setLayout(new FlowLayout());


        String bio = profileDataManager.getDataById(userId).getBio();
        biolabel.setText(bio);
        userName.setFont(new Font("Arial", Font.BOLD, 15));
        String pathPhotoProfile = profileDataManager.getDataById(userId).getProfilePhotoPath();
        String pathCoverProfile = profileDataManager.getDataById(userId).getCoverPhotoPath();
        loadCircularImageToPanel(photoPanel, pathPhotoProfile, 100);
        loadRectangularImageToPanel(coverPanel, pathCoverProfile);



    }

    private void loadCircularImageToPanel(JPanel panel, String imagePath, int diameter) {
        try {
            File imageFile = new File(imagePath);
            System.out.println("Attempting to load image from: " + imageFile.getAbsolutePath());

            if (!imageFile.exists()) {
                throw new IOException("Image file not found at: " + imagePath);
            }

            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image image = imageIcon.getImage();


            JPanel circlePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;


                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //عشان اعمل smooth edge


                    Shape circle = new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter);//عشان اعمل شكل circle
                    g2d.setClip(circle);


                    g2d.drawImage(image, 0, 0, diameter, diameter, null);// بعمل Scale لل image عشان تبقي جوه الcircle layout اللي انا لسا عامله
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(diameter, diameter);
                }
            };


            panel.removeAll();
            panel.setLayout(new GridBagLayout()); // عشان احطها في النص

            panel.add(circlePanel); // بضيف الدايره في الpanel
            panel.revalidate();// جدد الlayout
            panel.repaint(); // للرسم مره اخري
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRectangularImageToPanel(JPanel panel, String imagePath) {
        try {
            File imageFile = new File(imagePath);
            System.out.println("Attempting to load image from: " + imageFile.getAbsolutePath());

            if (!imageFile.exists()) {
                throw new IOException("Image file not found at: " + imagePath);
            }

            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image image = imageIcon.getImage();

            JPanel rectangularPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;

                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Draw the image scaled to the size of the panel
                    g2d.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);
                }
            };

            panel.removeAll();
            panel.setLayout(new BorderLayout());
            panel.add(rectangularPanel, BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
