
package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

public class AddStudent extends JFrame {

    JTextField rollField, nameField, deptField, yearField, sectionField, usernameField;
    JPasswordField passwordField;

    public AddStudent() {

        setTitle("Add Student");
        setSize(400,400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8,2,10,10));

        JLabel rollLabel = new JLabel("Roll No:");
        rollField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel deptLabel = new JLabel("Department:");
        deptField = new JTextField();

        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField();

        JLabel sectionLabel = new JLabel("Section:");
        sectionField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton addBtn = new JButton("Add Student");

        add(rollLabel);
        add(rollField);

        add(nameLabel);
        add(nameField);

        add(deptLabel);
        add(deptField);

        add(yearLabel);
        add(yearField);

        add(sectionLabel);
        add(sectionField);

        add(usernameLabel);
        add(usernameField);

        add(passwordLabel);
        add(passwordField);

        add(new JLabel());
        add(addBtn);

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DBConnection.getConnection();

                    String sql = "INSERT INTO student(rollno,name,department,year,section,username,password) VALUES(?,?,?,?,?,?,?)";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, rollField.getText());
                    pst.setString(2, nameField.getText());
                    pst.setString(3, deptField.getText());
                    pst.setString(4, yearField.getText());
                    pst.setString(5, sectionField.getText());
                    pst.setString(6, usernameField.getText());
                    pst.setString(7, new String(passwordField.getPassword()));

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Student Added Successfully");

                    rollField.setText("");
                    nameField.setText("");
                    deptField.setText("");
                    yearField.setText("");
                    sectionField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
