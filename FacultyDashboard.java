
package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class FacultyDashboard extends JFrame implements ActionListener {

    JButton viewSubjects, markAttendance, logout;
    JButton reportBtn;
    public FacultyDashboard() {

        setTitle("Faculty Dashboard");
        setSize(350,250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1,10,10));

        viewSubjects = new JButton("View Assigned Subjects");
        markAttendance = new JButton("Mark Attendance");
        reportBtn = new JButton("View Attendance Report");
        logout = new JButton("Logout");

        viewSubjects.addActionListener(this);
        markAttendance.addActionListener(this);
        reportBtn.addActionListener(this);
        logout.addActionListener(this);

        add(viewSubjects);
        add(markAttendance);
        add(reportBtn);
        add(logout);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==viewSubjects) {

    try {

        Connection con = db.DBConnection.getConnection();

        String query = "SELECT s.subject_name, fs.year, fs.section " +
                       "FROM subject s " +
                       "JOIN faculty_subject fs ON s.subject_id = fs.subject_id";

        PreparedStatement ps = con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        String subjects = "";

        while(rs.next()) {

            subjects += rs.getString("subject_name") +
                        " - Year " + rs.getInt("year") +
                        " - Section " + rs.getInt("section") + "\n";
        }

        JOptionPane.showMessageDialog(this,
                subjects,
                "Assigned Subjects",
                JOptionPane.INFORMATION_MESSAGE);

    } catch(Exception ex) {
        ex.printStackTrace();
    }
}
        

        if(e.getSource()==markAttendance) {

            new MarkAttendance();   // opens attendance page

        }
        if(e.getSource()==reportBtn){
            new AttendanceReport();
            
        }


        if(e.getSource()==logout) {

            new LoginPage();
            dispose();

        }
    }
}
