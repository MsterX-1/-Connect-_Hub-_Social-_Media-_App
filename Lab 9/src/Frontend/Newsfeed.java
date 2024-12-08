package Frontend;

import Backend.ContentCreation.Post;
import Backend.ContentCreation.Story;
import Backend.Databases.DataManager;
import Backend.Databases.DatabaseFactory;
import Backend.FriendManager.FriendMangerWindow1;
import Backend.Interfaces.Database;
import Backend.UserDatabase;
import Frontend.CustomPanels.PostPanel;
import Frontend.CustomPanels.ProfilePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Newsfeed extends JFrame {
    private JPanel mainContainer;
    private JButton refreshButton;
    private JButton profileManagmentButton;
    private JButton createPostButton;
    private JLabel imagelabel;
    private JButton createStoryButton;
    private JPanel postContainer;
    private JScrollPane postScrollPane;
    private JScrollPane friendScrollPane;
    private JPanel freindsContainer;
    private JScrollPane suggestionsScrollPane;
    private JPanel suggestionsContainer;
    private JButton friendManagerButton;
    private JPanel currentUserPanel;
    private JPanel lowerButtons;
    private JLabel usernameLabel;
    private JPanel zzz;
    private JPanel imagePlace;
    private UserDatabase userDatabase;
    private String userId;

    public Newsfeed(UserDatabase userDatabase, String userId, MainWindow mainWindow) {
        this.userDatabase = userDatabase;
        this.userId = userId;
        //create post database and create a data manager
        Database<Post> postDatabase = DatabaseFactory.createDatabase("post");
        DataManager<Post> postManager = new DataManager<>(postDatabase);
        postManager.loadData();
        //create story database and create a data manager
        Database<Story> storyDatabase = DatabaseFactory.createDatabase("story");
        DataManager<Story> storyManager = new DataManager<>(storyDatabase);
        storyManager.loadData();



//friendManagerButton.setIcon(new ImageIcon("C:\\Users\\Legion\\Downloads\\1535040.png"));
//friendManagerButton.setPreferredSize(new Dimension(200,50));
        //managing posts
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(postManager);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //managing friendsList
        freindsContainer.setLayout(new BoxLayout(freindsContainer, BoxLayout.Y_AXIS));
        populateFriendsList(userDatabase);
        freindsContainer.setSize(new Dimension(200, 100));
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //managing suggestions
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateSuggestionsList(userDatabase);
        suggestionsContainer.setSize(new Dimension(200, 100));
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Newsfeed newsfeed = this;
        // Frame properties
        setVisible(true);
        setTitle("NewsFeed");
        setSize(new Dimension(1000, 800));
        currentUserPanel.setPreferredSize(new Dimension(400, 200));
        friendScrollPane.setSize(new Dimension(600, 200));
        postScrollPane.setSize(new Dimension(600, 500));
        suggestionsScrollPane.setSize(new Dimension(400, 500));
        lowerButtons.setPreferredSize(new Dimension(1000, 100));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(mainContainer);


        imagelabel.setIcon(new ImageIcon(updateNewsFeedPhoto()));
        usernameLabel.setText(userDatabase.getUserById(userId).getUsername());

        imagelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //SOLID profile management should take both posts and stories
                new ProfileManagementPage(userId, userDatabase, newsfeed, mainWindow,postManager , storyManager).setVisible(true);
                setVisible(false);
            }
        });

        createStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "story", postManager , storyManager);
                //contentDatabase.loadContentFromDatabase("story");
            }
        });
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new publishContentWindow(userId, "post", postManager , storyManager);
                // contentDatabase.loadContentFromDatabase("post");
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //update posts window
                refreshPosts(postManager);
                refreshFriendsList(userDatabase);
                refreshSuggestionsList(userDatabase);

                // Update the newsfeed photo
                Image updatedImage = updateNewsFeedPhoto();
                imagelabel.setIcon(new ImageIcon(updatedImage));
            }
        });

        friendManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendMangerWindow1(userDatabase, userId);

            }
        });
    }

    public Image updateNewsFeedPhoto() {

        int index = userDatabase.getUserIndexById(userId);
        String pathPhotoProfile = userDatabase.getUserById(userId).getProfilePhotoPath();
        loadCircularImageToPanel(zzz, pathPhotoProfile, 150);
        ImageIcon imageIcon = new ImageIcon(pathPhotoProfile); // Load image
        Image image = imageIcon.getImage();
        int scaledWidth = imagelabel.getWidth();
        int scaledHeight = imagelabel.getHeight();
        Image scaledImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    public void refreshPosts(DataManager<Post> postManager) {
        postContainer.setLayout(new BoxLayout(postContainer, BoxLayout.Y_AXIS));
        populatePosts(postManager);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void refreshFriendsList(UserDatabase userDatabase) {
        freindsContainer.setLayout(new BoxLayout(freindsContainer, BoxLayout.Y_AXIS));
        populateFriendsList(userDatabase);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void refreshSuggestionsList(UserDatabase userDatabase) {
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        populateSuggestionsList(userDatabase);
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void populateFriendsList(UserDatabase userDatabase) {
        freindsContainer.removeAll();
        // Simulate data for demonstration
        if(userDatabase.getUserById(userId).getFriendsIds() == null)
            return;
        for (int i = 0; i < userDatabase.getUserById(userId).getFriendsIds().size(); i++) {

            String friendId = userDatabase.getUserById(userId).getFriendsIds().get(i);
            String friendName = userDatabase.getUserById(friendId).getUsername();
            String friendStatus = userDatabase.getUserById(friendId).checkStatus();
            System.out.println(friendStatus);
            String imagePaths = userDatabase.getUserById(friendId).getProfilePhotoPath();

            // Create a PostPanel for each post
            ProfilePanel friendPanel = new ProfilePanel(friendName + "( " + friendStatus + " )", imagePaths);

            // Add padding and border to each PostPanel
            friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            freindsContainer.add(friendPanel);

        }

        // Revalidate and repaint the container to apply updates
        freindsContainer.revalidate();
        freindsContainer.repaint();
    }

    private void populatePosts(DataManager<Post> postManager) {
        postContainer.removeAll();
        // Simulate data for demonstration
        if(postManager.getAllData() == null)
            return;
        for (int i = 0; i < postManager.getAllData().size(); i++) {
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

    private void populateSuggestionsList(UserDatabase userDatabase) {
        suggestionsContainer.removeAll();
        // Simulate data for demonstration
        if(userDatabase.getUserById(userId).getSuggestedIds() == null)
            return;
        for (int i = 0; i < userDatabase.getUserById(userId).getSuggestedIds().size(); i++) {
            String suggestedId = userDatabase.getUserById(userId).getSuggestedIds().get(i);
            String suggestedName = userDatabase.getUserById(suggestedId).getUsername();
            String imagePaths = userDatabase.getUserById(suggestedId).getProfilePhotoPath();

            // Create a PostPanel for each post
            ProfilePanel profilePanel = new ProfilePanel(suggestedName, imagePaths);

            // Add padding and border to each PostPanel
            profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the PostPanel to the container
            suggestionsContainer.add(profilePanel);

        }

        // Revalidate and repaint the container to apply updates
        suggestionsContainer.revalidate();
        suggestionsContainer.repaint();
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

circlePanel.setBackground(new Color(40,40,40));
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

