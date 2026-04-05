
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

public class AddSubject extends JFrame {

    JTextField subjectField, deptField, yearField;

    public AddSubject() {

        setTitle("Add Subject");
        setSize(350,250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2,10,10));

        JLabel subjectLabel = new JLabel("Subject Name:");
        subjectField = new JTextField();

        JLabel deptLabel = new JLabel("Department:");
        deptField = new JTextField();

        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField();

        JButton addBtn = new JButton("Add Subject");

        add(subjectLabel);
        add(subjectField);

        add(deptLabel);
        add(deptField);

        add(yearLabel);
        add(yearField);

        add(new JLabel());
        add(addBtn);

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = DBConnection.getConnection();

                    String sql = "INSERT INTO subject(subject_name,department,year) VALUES(?,?,?)";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, subjectField.getText());
                    pst.setString(2, deptField.getText());
                    pst.setString(3, yearField.getText());

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Subject Added Successfully");

                    subjectField.setText("");
                    deptField.setText("");
                    yearField.setText("");

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
