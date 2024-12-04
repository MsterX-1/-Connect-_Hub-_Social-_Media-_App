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
        signup signup1 = this;
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
                if (selectedDate != null &&  newUserName!= null && newUserEmail!= null && newUserPassword!= null) {
if(newUserName.isEmpty() || newUserEmail.isEmpty() || newUserPassword.isEmpty()) {
    JOptionPane.showMessageDialog(signup1, "all fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
}else{
                    LocalDate localDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
if(ChronoUnit.YEARS.between(localDate , LocalDate.now()) <15 )
    JOptionPane.showMessageDialog(signup1, "the date is invalid for the a user", "Error", JOptionPane.ERROR_MESSAGE);
else if(!isValidEmail(newUserEmail)){

    JOptionPane.showMessageDialog(signup1, "invalid email pattern", "Error", JOptionPane.ERROR_MESSAGE);



}
else{
String newUserId = ""+(userDatabase.getUsers().size()+1);
    User newUser = new User(newUserId,newUserEmail,newUserPassword,newUserName,localDate,false);
    try {
        userDatabase.adduser(newUser);
    } catch (NoSuchAlgorithmException ex) {
        throw new RuntimeException(ex);
    }
    userDatabase.saveToFile();
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
