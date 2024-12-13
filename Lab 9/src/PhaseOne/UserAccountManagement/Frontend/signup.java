package PhaseOne.UserAccountManagement.Frontend;

import Databases.DataManager;
import Databases.DatabaseFactory;
import Interfaces.Database;
import PhaseOne.FriendManagement.Backend.UserRelations;
import PhaseOne.ProfileManagement.Backend.Profile;
import RunProgram.MainWindow;
import PhaseOne.UserAccountManagement.Backend.User;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends JFrame {
    private JButton signUpButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel datelib;
    private JPanel signupwindow;
    private JButton backButton;
    private JPasswordField passwordField1;
    private MainWindow mainWindow;
    public signup (MainWindow mainWindow, DataManager<User> userDataManager){
        this.mainWindow = mainWindow;
        setResizable(false);
        UtilDateModel model = new UtilDateModel();
        model.setValue(Calendar.getInstance().getTime());
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datelib.add(datePicker);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(signupwindow);
        signupwindow.revalidate();
        signupwindow.repaint();
        setContentPane(signupwindow);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("signup");
        setVisible(true);





        signupwindow = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the component is painted properly
                // Set the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Legion\\Downloads\\html-color-codes-color-tutorials.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        signupwindow.setLayout(null);
        setContentPane(signupwindow);

        backButton.setBackground(Color.white);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setForeground(Color.black);
        signUpButton.setBounds(680, 500, 100, 29);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 15));
        signUpButton.setForeground(Color.black);
        signUpButton.setBackground(Color.white);
        textField1 = new JTextField();
        textField1.setBounds(400, 150, 250, 29);
        textField2 = new JTextField();
        textField2.setBounds(400, 250, 250, 29);
        passwordField1 = new JPasswordField();
        passwordField1.setBounds(400, 300, 250, 29);

        JLabel label1 = new JLabel("UserName");
        label1.setFont(new Font("Arial", Font.BOLD, 15));
        label1.setBounds(30, 150, 250, 29);
        JLabel label2 = new JLabel("Email");
        label2.setFont(new Font("Arial", Font.BOLD, 15));
        label2.setBounds(30, 250, 250, 29);
        JLabel label3 = new JLabel("Date of Birth");
        label3.setFont(new Font("Arial", Font.BOLD, 15));
        label3.setBounds(30, 373, 250, 29);
        JLabel label4 = new JLabel("Password");
        label4.setFont(new Font("Arial", Font.BOLD, 15));
        label4.setBounds(30, 300, 250, 29);
        label4.setForeground(Color.white);

        label2.setForeground(Color.white);
        label2.setBackground(Color.black);
        label1.setForeground(Color.white);
        label1.setBackground(Color.black);
        label3.setForeground(Color.white);
        label3.setBackground(Color.black);


        signupwindow.add(backButton);
        signupwindow.add(signUpButton);
        signupwindow.add(textField1);
        signupwindow.add(textField2);
        signupwindow.add(passwordField1);
        signupwindow.add(datelib);
        signupwindow.add(label1);
        signupwindow.add(label2);
        signupwindow.add(label3);
        signupwindow.add(label4);










        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUserName = textField1.getText();
                String newUserEmail = textField2.getText();
                String newUserPassword = new String(passwordField1.getPassword()).trim();
                java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
                if(newUserName.isEmpty() || newUserEmail.isEmpty() || newUserPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(signupwindow, "all fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    LocalDate localDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    if(ChronoUnit.YEARS.between(localDate , LocalDate.now()) <15 )
                        JOptionPane.showMessageDialog(signupwindow, "the date is invalid for the a user", "Error", JOptionPane.ERROR_MESSAGE);
                    else if(!isValidEmail(newUserEmail)){

                        JOptionPane.showMessageDialog(signupwindow, "invalid email pattern", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                    else{
                        //Hashing Password Algorithm
                        MessageDigest encrypt = null;
                        try {
                            encrypt = MessageDigest.getInstance("SHA-256");
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                        byte[] hashedPasswordInBytes = encrypt.digest(newUserPassword.getBytes());
                        String hashedPasswordInHex = "";

                        for (int i =0 ; i< hashedPasswordInBytes.length ; i++) {
                            String hex = Integer.toHexString(0xff & hashedPasswordInBytes[i]); // Unsigned treatment
                            hashedPasswordInHex=hashedPasswordInHex+hex;
                        }
                        newUserPassword = hashedPasswordInHex;// password hashed

                        boolean userExists = false;

                        // Check if username or email already exists
                        for (int i = 0; i < userDataManager.getAllData().size(); i++) {
                            if (userDataManager.getAllData().get(i).getUsername().equals(newUserName) || userDataManager.getAllData().get(i).getEmail().equals(newUserEmail)) {
                                userExists = true;
                                break; // Exit the loop early if user exists
                            }
                        }

                        if (userExists) {
                            // If a user already exists, show an error message
                            JOptionPane.showMessageDialog(signupwindow, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Create a new user if no conflict is found
                            JOptionPane.showMessageDialog(signupwindow, "New User Created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                            String newUserId = "" + (userDataManager.getAllData().size() + 1); // Initialize user ID, consider changing format later
                            ArrayList<String> userSuggestions = new ArrayList<>();

                            // Populate user suggestions with existing user IDs
                            for (int i = 0; i < userDataManager.getAllData().size(); i++) {
                                userSuggestions.add(userDataManager.getAllData().get(i).getUserId());
                            }

                            // Create a new User object
                            User newUser = new User(newUserId, newUserEmail, newUserPassword, newUserName, localDate, false);
                            //put suggest friend to the new user
                            Database<UserRelations> userRelationsDatabase = DatabaseFactory.createDatabase("relations");
                            DataManager<UserRelations> userRelationsManager = new DataManager<>(userRelationsDatabase);
                            userRelationsManager.loadData();
                            UserRelations userRelations = new UserRelations(newUserId);
                            userRelations.setSuggestionsList(userSuggestions);
                            for(int i=0;i<userRelationsManager.getAllData().size();i++){
                                userRelationsManager.getAllData().get(i).getSuggestionsList().add(newUserId);
                            }
                            userRelationsManager.insertData(userRelations);
                            userRelationsManager.saveData();
                            // make default profile for new user
                            Database<Profile> profileDatabase = DatabaseFactory.createDatabase("profile");
                            DataManager<Profile> profileManager = new DataManager<>(profileDatabase);
                            profileManager.loadData();
                            Profile newUserProfile = new Profile(newUserId);
                            profileManager.insertData(newUserProfile);
                            profileManager.saveData();
                            // Add the new user to the data manager and save changes
                            userDataManager.insertData(newUser);
                            userDataManager.saveData();

                            // Close the signup window and open the main window
                            setVisible(false);
                            mainWindow.setVisible(true);
                        }


                    }
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                mainWindow.setVisible(true);
            }
        });
    }

    public  boolean isValidEmail(String email) {
        // Updated Regular expression for a valid email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex into a pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        Matcher matcher = pattern.matcher(email);

        // Return whether the email matches the pattern
        return matcher.matches();
    }

}