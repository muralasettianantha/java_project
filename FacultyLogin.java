
package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import db.DBConnection;

public class FacultyLogin extends JFrame implements ActionListener {

    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;

    public FacultyLogin() {

        setTitle("Faculty Login");
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        userField = new JTextField();
        passField = new JPasswordField();

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);

        setLayout(new GridLayout(3,2,10,10));

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(new JLabel());
        add(loginBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        try {

            // ✅ Correct connection
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM faculty WHERE username=? AND password=?");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                JOptionPane.showMessageDialog(this,"Faculty Login Successful");

                new FacultyDashboard().setVisible(true);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this,"Invalid Login");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FacultyLogin();
    }
}
