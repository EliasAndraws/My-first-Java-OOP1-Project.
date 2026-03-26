package com.university;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class MainApp
{
    public static void main(String[] args)
    {
        try
        {
        FlatLightLaf.setup();
        }
        
        catch (Exception ex) { System.err.println("Failed to initialize LaF"); }
        
        SwingUtilities.invokeLater(() ->{showLoginDialog();});
    }

    private static void showLoginDialog()
    {
    JFrame loginFrame = new JFrame("Login");
    loginFrame.setSize(350, 200);
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    loginFrame.setLocationRelativeTo(null);
    loginFrame.setLayout(new GridLayout(3, 2, 10, 10));

    JLabel lblUser = new JLabel("UserName:", JLabel.CENTER);
    JTextField txtUser = new JTextField();
    JLabel lblPass = new JLabel("Password:", JLabel.CENTER);
    JPasswordField txtPass = new JPasswordField();
    JButton btnLogin = new JButton("Enter");
    JButton btnCancel = new JButton("Cancel");

    loginFrame.add(lblUser);
    loginFrame.add(txtUser);
    loginFrame.add(lblPass);
    loginFrame.add(txtPass);
    loginFrame.add(btnCancel);
    loginFrame.add(btnLogin);

    btnLogin.addActionListener(e ->
    {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        if (username.equals("admin") && password.equals("123"))
        {
            loginFrame.dispose();
            createMainMenu();
        }
        
        else
        {
            JOptionPane.showMessageDialog(loginFrame, "The username or password is wrong", "Erorr", JOptionPane.ERROR_MESSAGE);
        }
    }
    );

    btnCancel.addActionListener(e -> System.exit(0));

    loginFrame.setVisible(true);
}
    
    private static void createMainMenu()
    {        
        JFrame mainFrame = new JFrame("University Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400,300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new GridLayout(3,1,10,10));

        JButton btnEnroll = new JButton("Student Enrollments");
        JButton btnStudents = new JButton("Manage Students");
        JButton btnCourses = new JButton("Manage Courses");
        JButton btnExit = new JButton("Exit");

        Font btnFont = new Font("Arial",Font.BOLD,16);
        btnStudents.setFont(btnFont);
        btnCourses.setFont(btnFont);
        btnExit.setFont(btnFont);
        btnEnroll.setFont(btnFont);
        
        btnEnroll.addActionListener(e -> new EnrollmentFrame().setVisible(true));
        btnStudents.addActionListener(e ->{new StudentFrame().setVisible(true);});
        btnCourses.addActionListener(e ->{new CourseFrame().setVisible(true);});
        btnExit.addActionListener(e ->{System.exit(0);});

        mainFrame.add(btnEnroll);
        mainFrame.add(btnStudents);
        mainFrame.add(btnCourses);
        mainFrame.add(btnExit);

        mainFrame.setVisible(true);
    }
}