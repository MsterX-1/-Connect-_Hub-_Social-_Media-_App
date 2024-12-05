package Frontend;
import Backend.UserDatabase;
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
    private JScrollPane ScrollPnael;
    private JTextField BioField;
    private JPanel postsPanel;     // Panel to hold individual posts
    private ArrayList<String> postsList;
    private UserDatabase userDatabase;
    private String userId;


    public PRofileManagementPage(String userID,UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
        this.userId = userID;
        setTitle("Profile Management");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        PRofileManagementPage pRofileManagementPage = this;

        coverpanel.setLayout(new FlowLayout());
        picturepanel.setLayout(new FlowLayout());
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

        ScrollPnael.setViewportView(postsPanel);
        int index = userDatabase.getUserIndexById(userId);
        String bio= userDatabase.getUsers().get(index).getBio();
        BioField.setText(bio);
        BioField.setEditable(false);   // Lock the text field to make it read-only


      //  UserDatabase userdb = new UserDatabase();
//userdb.loadFromFile();
      //  System.out.println(userdb.getUsers().size());
     //   if (userdb.getUsers().isEmpty()) {
     //       System.out.println("Users list is empty. Check your JSON file or loadFromFile method.");

    //    }


       // User User1 = userdb.getuserbyId(userID);
       // if (User1 != null) {
       //     System.out.println("User found: " + User1);
       //      loadCircularImageToPanel(picturepanel, User1.getProfilePhotoPath(), 150);
       //       loadCircularImageToPanel(coverpanel, User1.getCoverPhotoPath(), 400);
       //   } else {
       //       JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
       //    }

      String pathPhotoProfile=  userDatabase.getUsers().get(index).getProfilePhotoPath();
        String pathCoverProfile=  userDatabase.getUsers().get(index).getCoverPhotoPath();
        loadCircularImageToPanel(picturepanel,pathPhotoProfile , 150); // Default profile picture
        loadCircularImageToPanel(coverpanel, pathCoverProfile, 400);


        postsList = new ArrayList<>();
        loadPosts();
        displayPosts();

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

    private void loadPosts() {
        postsList.add("Post 1: This is the first post.");
        postsList.add("Post 2: Another post added here.");
        postsList.add("Post 3: Welcome to the profile management page!");
        postsList.add("Post 4: Feel free to scroll down.");
    }

    private void displayPosts() {
        postsPanel.removeAll();

        for (String post : postsList) {
            // Create a label or custom component for each post
            JLabel postLabel = new JLabel(post);
            postLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
            postLabel.setOpaque(true);
            postLabel.setBackground(Color.LIGHT_GRAY);
            postLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
            postsPanel.add(postLabel);
            postsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
        }

        postsPanel.revalidate();
        postsPanel.repaint();
    }
}
