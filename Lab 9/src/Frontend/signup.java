package Frontend;

import Backend.User;
import Backend.UserDatabase;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private UserDatabase userDatabase;
    private MainWindow mainWindow;
    public signup (MainWindow mainWindow, UserDatabase userDatabase){
        this.mainWindow = mainWindow;
        this.userDatabase=userDatabase;
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
                        String newUserId = ""+(userDatabase.getUsers().size()+1); //initialize user id maybe will change the format latter
                        User newUser = new User(newUserId,newUserEmail,newUserPassword,newUserName,localDate,false);
                        try {
                            if (userDatabase.addUser(newUser)) {
                                JOptionPane.showMessageDialog(signupwindow, "New User Created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                userDatabase.saveToFile();
                                setVisible(false);
                                mainWindow.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(signupwindow, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NoSuchAlgorithmException ex) {
                            JOptionPane.showMessageDialog(signupwindow, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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