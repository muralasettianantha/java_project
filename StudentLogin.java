package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import db.DBConnection;

public class StudentLogin extends JFrame implements ActionListener {

    JTextField rollField;
    JPasswordField passField;
    JButton loginBtn;

    Connection con;

    public StudentLogin() {

        setTitle("Student Login");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,10,10));

        rollField = new JTextField();
        passField = new JPasswordField();
        loginBtn = new JButton("Login");

        add(new JLabel("Roll Number"));
        add(rollField);

        add(new JLabel("Password"));
        add(passField);

        add(new JLabel(""));
        add(loginBtn);

        loginBtn.addActionListener(this);

        con = DBConnection.getConnection();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String roll = rollField.getText();
        String pass = new String(passField.getPassword());

        try {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM student WHERE rollno=? AND password=?");

            ps.setString(1, roll);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                JOptionPane.showMessageDialog(this,"Login Successful");

                new StudentDashboard(roll);
                dispose();

            } else {

                JOptionPane.showMessageDialog(this,"Invalid Login");

            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
