package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;
public class AddFaculty extends JFrame {

    JTextField nameField, deptField, usernameField;
    JPasswordField passwordField;

    public AddFaculty() {

        setTitle("Add Faculty");
        setSize(350,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2,10,10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel deptLabel = new JLabel("Department:");
        deptField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton addBtn = new JButton("Add Faculty");

        add(nameLabel);
        add(nameField);

        add(deptLabel);
        add(deptField);

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

                    String sql = "INSERT INTO faculty(name,department,username,password) VALUES(?,?,?,?)";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, nameField.getText());
                    pst.setString(2, deptField.getText());
                    pst.setString(3, usernameField.getText());
                    pst.setString(4, new String(passwordField.getPassword()));

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Faculty Added Successfully");

                    nameField.setText("");
                    deptField.setText("");
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
