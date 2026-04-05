
package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import db.DBConnection;

public class MarkAttendance extends JFrame implements ActionListener {

    JComboBox<String> yearBox, sectionBox, subjectBox;
    JButton loadBtn, submitBtn;
    JPanel studentPanel;

    Connection con;

    public MarkAttendance() {

        setTitle("Mark Attendance");
        setSize(500,450);
        setLocationRelativeTo(null);

        yearBox = new JComboBox<>(new String[]{"1","2","3","4"});
        sectionBox = new JComboBox<>(new String[]{"1","2"});

        subjectBox = new JComboBox<>();

        loadBtn = new JButton("Load Students");
        submitBtn = new JButton("Submit Attendance");

        studentPanel = new JPanel();
        studentPanel.setLayout(new GridLayout(0,3,10,10));

        loadBtn.addActionListener(this);
        submitBtn.addActionListener(this);

        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Year"));
        topPanel.add(yearBox);

        topPanel.add(new JLabel("Section"));
        topPanel.add(sectionBox);

        topPanel.add(new JLabel("Subject"));
        topPanel.add(subjectBox);

        topPanel.add(loadBtn);

        add(topPanel,BorderLayout.NORTH);
        add(new JScrollPane(studentPanel),BorderLayout.CENTER);
        add(submitBtn,BorderLayout.SOUTH);

        con = DBConnection.getConnection();

        loadSubjects();

        setVisible(true);
    }

    // Load subjects dynamically from database
    void loadSubjects() {

        try {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT subject_name FROM subject");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                subjectBox.addItem(rs.getString("subject_name"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==loadBtn) {

            studentPanel.removeAll();

            String year = yearBox.getSelectedItem().toString();
            String section = sectionBox.getSelectedItem().toString();

            try {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT rollno,name FROM student WHERE year=? AND section=?");

                ps.setString(1, year);
                ps.setString(2, section);

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {

                    String roll = rs.getString("rollno");
                    String name = rs.getString("name");

                    JLabel nameLabel = new JLabel(roll+" - "+name);

                    JRadioButton present = new JRadioButton("Present");
                    JRadioButton absent = new JRadioButton("Absent");

                    ButtonGroup bg = new ButtonGroup();
                    bg.add(present);
                    bg.add(absent);

                    studentPanel.add(nameLabel);
                    studentPanel.add(present);
                    studentPanel.add(absent);
                }

                studentPanel.revalidate();
                studentPanel.repaint();

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource()==submitBtn) {

            Component[] comps = studentPanel.getComponents();

            try {

                for(int i=0;i<comps.length;i+=3) {

                    JLabel label = (JLabel) comps[i];
                    JRadioButton present = (JRadioButton) comps[i+1];

                    String rollno = label.getText().split(" - ")[0];

                    String status="Absent";

                    if(present.isSelected()) {
                        status="Present";
                    }

                    // get subject_id
                    PreparedStatement sub = con.prepareStatement(
                            "SELECT subject_id FROM subject WHERE subject_name=?");

                    sub.setString(1, subjectBox.getSelectedItem().toString());

                    ResultSet rsSub = sub.executeQuery();

                    int subjectId = 0;

                    if(rsSub.next()){
                        subjectId = rsSub.getInt("subject_id");
                    }

                    // get student_id
                    PreparedStatement stu = con.prepareStatement(
                            "SELECT student_id FROM student WHERE rollno=?");

                    stu.setString(1, rollno);

                    ResultSet rsStu = stu.executeQuery();

                    int studentId = 0;

                    if(rsStu.next()){
                        studentId = rsStu.getInt("student_id");
                    }

                    // insert attendance
                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO attendance(student_id,subject_id,date,status,rollno) VALUES(?,?,CURDATE(),?,?)");

                    ps.setInt(1, studentId);
                    ps.setInt(2, subjectId);
                    ps.setString(3, status);
                    ps.setString(4, rollno);

                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(this,"Attendance Saved Successfully");

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
