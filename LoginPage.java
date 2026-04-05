
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import db.DBConnection;
public class LoginPage extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public LoginPage() {

        setTitle("Student Attendance System - Login");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(4,2,10,10));

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Login As:");
        roleBox = new JComboBox<>(new String[]{"Admin","Faculty","Student"});

        JButton loginBtn = new JButton("Login");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(roleLabel);
        add(roleBox);
        add(new JLabel());
        add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = roleBox.getSelectedItem().toString();
                        if(role.equals("Admin") && username.equals("admin") && password.equals("admin@123")) {

            JOptionPane.showMessageDialog(null,"Admin Login Successful");
            new AdminDashboard().setVisible(true);
            dispose();
        }

        // FACULTY LOGIN
        else if(role.equals("Faculty")) {
            JOptionPane.showMessageDialog(null,"Faculty Login Successful");
            new FacultyDashboard().setVisible(true);
            dispose();
        }

        // STUDENT LOGIN
        else if(role.equals("Student")) {

    if(username.isEmpty()) {
        JOptionPane.showMessageDialog(null,"Enter Roll Number");
        return;
    }

    String rollno = username;

    JOptionPane.showMessageDialog(null,"Student Login Successful");

    new StudentDashboard(rollno).setVisible(true);
    dispose();
}

        else {
            JOptionPane.showMessageDialog(null,"Invalid Login");
        }
    }
});

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
