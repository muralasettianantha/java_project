
package ui;

import db.DBConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewAttendance extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewAttendance(String rollno) {

        setTitle("My Attendance");
        setSize(600,400);
        setLocationRelativeTo(null);
        System.out.println("Roll No Received: " + rollno);
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("Roll No");
        model.addColumn("Subject");
        model.addColumn("Date");
        model.addColumn("Status");

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,1));

        JLabel overallLabel = new JLabel();
        JTextArea subjectArea = new JTextArea();
        subjectArea.setEditable(false);

        bottomPanel.add(overallLabel);
        bottomPanel.add(new JScrollPane(subjectArea));

        add(bottomPanel, BorderLayout.SOUTH);

        try {

            Connection con = DBConnection.getConnection();

            // Load attendance records
            PreparedStatement ps = con.prepareStatement(
                    "SELECT a.rollno, s.subject_name, a.date, a.status " +
                    "FROM attendance a " +
                    "JOIN subject s ON a.subject_id = s.subject_id " +
                    "WHERE a.rollno=?");

            ps.setString(1, rollno);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                model.addRow(new Object[]{
                        rs.getString("rollno"),
                        rs.getString("subject_name"),
                        rs.getDate("date"),
                        rs.getString("status")
                });
            }

            // Overall attendance percentage
            PreparedStatement ps2 = con.prepareStatement(
            "SELECT COUNT(*) AS total, " +
            "SUM(CASE WHEN status='Present' THEN 1 ELSE 0 END) AS present " +
            "FROM attendance WHERE rollno=?");

            ps2.setString(1, rollno);

            ResultSet rs2 = ps2.executeQuery();

            if(rs2.next()) {

                int total = rs2.getInt("total");
                int present = rs2.getInt("present");

                double percent = 0;

                if(total != 0){
                    percent = (present * 100.0) / total;
                }

                overallLabel.setText(
                        "Overall Attendance Percentage: " +
                        String.format("%.2f", percent) + "%");
            }

            // Subject-wise attendance percentage
            PreparedStatement ps3 = con.prepareStatement(
            "SELECT s.subject_name, COUNT(*) total, " +
            "SUM(CASE WHEN a.status='Present' THEN 1 ELSE 0 END) present " +
            "FROM attendance a " +
            "JOIN subject s ON a.subject_id = s.subject_id " +
            "WHERE a.rollno=? GROUP BY s.subject_name");

            ps3.setString(1, rollno);

            ResultSet rs3 = ps3.executeQuery();

            subjectArea.append("Subject-wise Attendance:\n\n");

            while(rs3.next()) {

                String subject = rs3.getString("subject_name");
                int total = rs3.getInt("total");
                int present = rs3.getInt("present");

                double percent = 0;

                if(total != 0){
                    percent = (present * 100.0) / total;
                }

                subjectArea.append(
                        subject + " : " +
                        String.format("%.2f", percent) + "%\n"
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
}
