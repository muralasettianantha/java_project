
package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import db.DBConnection;
public class StudentDashboard extends JFrame implements ActionListener {

    JButton viewAttendance, logout;
    String rollno;

    public StudentDashboard(String rollno) {

        this.rollno = rollno;

        setTitle("Student Dashboard");
        setSize(300,200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,1,10,10));

        viewAttendance = new JButton("View Attendance");
        logout = new JButton("Logout");

        viewAttendance.addActionListener(this);
        logout.addActionListener(this);

        add(viewAttendance);
        add(logout);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==viewAttendance) {

          new ViewAttendance(rollno); 

        }

        if(e.getSource()==logout) {

            new LoginPage();
            dispose();

        }
       
    }
}
