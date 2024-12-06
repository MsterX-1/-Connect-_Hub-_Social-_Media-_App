package Frontend;
import Backend.ContentCreation.ContentDatabase;
import Backend.UserDatabase;
import Frontend.CustomPanels.PostPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PRofileManagementPage extends JFrame {


    private JButton editButton;    // Edit Button
    private JPanel panel;          // Main Panel
    private JPanel coverpanel;
    private JPanel picturepanel;
    private JScrollPane postScrollPane;
    private JTextField BioField;
    private JButton logoutButton;
    private JButton returnButton;
    private JPanel postContainer;
    private UserDatabase userDatabase;
    private String userId;


    public PRofileManagementPage(String userID,UserDatabase userDatabase,Newsfeed newsfeed,MainWindow mainWindow, ContentDatabase contentDatabase) {
        this.userDatabase = userDatabase;
        this.userId = userID;
        setTitle("Profile Management");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        PRofileManagementPage pRofileManagementPage = this;
        //managing posts
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(contentDatabase);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //
        coverpanel.setLayout(new FlowLayout());
        picturepanel.setLayout(new FlowLayout());
       ;


        int index = userDatabase.getUserIndexById(userId);
        String bio= userDatabase.getUsers().get(index).getBio();
        BioField.setText(bio);
        BioField.setEditable(false);   // Lock the text field to make it read-only

      String pathPhotoProfile=  userDatabase.getUsers().get(index).getProfilePhotoPath();
        String pathCoverProfile=  userDatabase.getUsers().get(index).getCoverPhotoPath();
        loadCircularImageToPanel(picturepanel,pathPhotoProfile , 150); // Default profile picture
        loadCircularImageToPanel(coverpanel, pathCoverProfile, 400);



        editButton.addActionListener(e -> {
            System.out.println("Edit button clicked!");
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new EditProfile(pRofileManagementPage,userID,userDatabase,BioField);
               //setVisible(false);
            }
        });


        setContentPane(panel);
        setVisible(true);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = userDatabase.getUserIndexById(userId);
                userDatabase.getUsers().get(index).setStatus(false);
                userDatabase.saveToFile();
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
    private void populatePosts(ContentDatabase contentDatabase) {
        postContainer.removeAll();
        // Simulate data for demonstration
        if(contentDatabase.getPosts() == null)
            return;
        for (int i = 0; i < contentDatabase.getPosts().size(); i++) {
            String postAuthor = contentDatabase.getPosts().get(i).getAuthorId();
            if(!postAuthor.equals(userId)){
                continue;
            }
            String text = contentDatabase.getPosts().get(i).getContent().getText();
            ArrayList<String> imagePaths = contentDatabase.getPosts().get(i).getContent().getImagePaths();

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
