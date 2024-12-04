package Frontend;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileManagement extends JFrame {
    private JTextField bioField;
    private JLabel profilePhotoLabel;
    private JLabel coverPhotoLabel;
    private JButton Change;
    private JButton changeButton;
    private JButton changeButton2;
    private JButton changeButton1;
    private JPanel panel;
    private JButton returnButton;

    public ProfileManagement() {

        setTitle("Profile Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setContentPane(panel);
        PRofileManagementPage profileManagementPage = new PRofileManagementPage("1003");



        Change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                new UploadNewProfilePicture(profileManagementPage).setVisible(true);
                dispose();
            }
        });
        changeButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UploadNewCoverPicture(profileManagementPage).setVisible(true);
                dispose();
            }
        });
        changeButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new PRofileManagementPage("1003").setVisible(true);
                 dispose();
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

           // new PRofileManagementPage().setVisible(true);
            new PRofileManagementPage("1003").setVisible(true);


        });


    }









}
