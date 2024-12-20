package PhaseOne.ProfileManagement.Frontend;

import PhaseOne.ContentCreation.Backend.Post;
import PhaseOne.ContentCreation.Backend.Story;
import Databases.DataManager;
import PhaseOne.Newsfeed.Frontend.Newsfeed;
import PhaseOne.ProfileManagement.Backend.Profile;
import PhaseOne.UserAccountManagement.Backend.User;
import CustomJPanels.PostPanels.PostPanel;
import RunProgram.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileManagementPage extends JFrame {


    private JButton editButton;    // Edit Button
    private JPanel panel;          // Main Panel
    private JPanel coverpanel;
    private JPanel picturepanel;
    private JScrollPane postScrollPane;

    private JButton logoutButton;
    private JButton returnButton;
    private JPanel postContainer;
    private JLabel bioLabel;
    private String userId;


    public ProfileManagementPage(String userId, DataManager<User>userDataManager, Newsfeed newsfeed, MainWindow mainWindow, DataManager<Post> postDataManager , DataManager<Story> storyDataManager, DataManager<Profile> profileManager) {
        this.userId = userId;
        setTitle("Profile Management");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ProfileManagementPage pRofileManagementPage = this;
        //managing posts
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(postDataManager);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        coverpanel.setLayout(new FlowLayout());
        picturepanel.setLayout(new FlowLayout());



        String bio= profileManager.getDataById(userId).getBio();
        bioLabel.setText(bio);

        String pathPhotoProfile=  profileManager.getDataById(userId).getProfilePhotoPath();
        String pathCoverProfile= profileManager.getDataById(userId).getCoverPhotoPath();
        loadCircularImageToPanel(picturepanel,pathPhotoProfile , 150); // Default profile picture
        loadCircularImageToPanel(coverpanel, pathCoverProfile, 400);



        editButton.addActionListener(e -> {
            System.out.println("Edit button clicked!");
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new EditProfile(pRofileManagementPage,userId,userDataManager , bioLabel,profileManager);
               //setVisible(false);
            }
        });


        setContentPane(panel);
        setVisible(true);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userDataManager.getDataById(userId).setStatus(false);
                userDataManager.saveData();
                JOptionPane.showMessageDialog(null, "Logout Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainWindow.setVisible(true);
                setVisible(false);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newsfeed.setVisible(true);
                setVisible(false);
            }
        });
    }
    private void populatePosts(DataManager<Post> postManager) {
        postContainer.removeAll();
        // Simulate data for demonstration
        if(postManager.getAllData() == null)
            return;
        for (int i = 0; i < postManager.getAllData().size(); i++) {
            if(!userId.equals(postManager.getAllData().get(i).getAuthorId()))
                continue;
            String text = postManager.getAllData().get(i).getContent().getText();
            ArrayList<String> imagePaths = postManager.getAllData().get(i).getContent().getImagePaths();

            // Create a PostPanel for each post
            PostPanel postPanel = new PostPanel(text, imagePaths);

            // Add padding and border to each PostPanel
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            postContainer.add(postPanel);

        }

        // Revalidate and repaint the container to apply updates
        postContainer.revalidate();
        postContainer.repaint();
    }

    public void updateProfilePicture(String imagePath) {
        System.out.println("Updating profile picture with: " + imagePath);
        loadCircularImageToPanel(picturepanel, imagePath, 150);
    }
    public void updateCoverPicture(String imagePath) {
        System.out.println("Updating cover picture with: " + imagePath);
        loadCircularImageToPanel(coverpanel, imagePath, 400); // Adjust to use your cover photo panel and desired size
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


}
