
package ui;

import db.DBConnection;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceReport extends JFrame implements ActionListener {

    JComboBox<String> yearBox, sectionBox;
    JButton loadBtn;
    JTable table;
    DefaultTableModel model;

    public AttendanceReport() {

        setTitle("Attendance Report");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        yearBox = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        sectionBox = new JComboBox<>(new String[]{"1", "2"});
        loadBtn = new JButton("Load Report");

        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Year"));
        topPanel.add(yearBox);

        topPanel.add(new JLabel("Section"));
        topPanel.add(sectionBox);

        topPanel.add(loadBtn);

        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();

        model.addColumn("Roll No");
        model.addColumn("Subject");
        model.addColumn("Date");
        model.addColumn("Status");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        model.setRowCount(0);

        String year = yearBox.getSelectedItem().toString();
        String section = sectionBox.getSelectedItem().toString();

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT s.rollno, " +
                "IFNULL(sub.subject_name, a.subject) AS subject_name, " +
                "a.date, a.status " +
                "FROM attendance a " +
                "JOIN student s ON a.rollno = s.rollno " +
                "LEFT JOIN subject sub ON a.subject_id = sub.subject_id " +
                "WHERE s.year=? AND s.section=? " +
                "ORDER BY a.date DESC"
            );

            ps.setString(1, year);
            ps.setString(2, section);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getString("rollno"),
                    rs.getString("subject_name"),
                    rs.getDate("date"),
                    rs.getString("status")
                });
            }

            if(model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No attendance records found");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading attendance report");
        }
    }
}
