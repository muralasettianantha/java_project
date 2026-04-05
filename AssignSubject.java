
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

public class AssignSubject extends JFrame {

    JComboBox<String> facultyBox, subjectBox;
    JTextField yearField, sectionField;

    public AssignSubject(){

        setTitle("Assign Subject to Faculty");
        setSize(350,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2,10,10));

        facultyBox = new JComboBox<>();
        subjectBox = new JComboBox<>();

        yearField = new JTextField();
        sectionField = new JTextField();

        JButton assignBtn = new JButton("Assign");

        add(new JLabel("Faculty"));
        add(facultyBox);

        add(new JLabel("Subject"));
        add(subjectBox);

        add(new JLabel("Year"));
        add(yearField);

        add(new JLabel("Section"));
        add(sectionField);

        add(new JLabel());
        add(assignBtn);

        loadFaculty();
        loadSubjects();

        assignBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                try{

                    Connection con = DBConnection.getConnection();

                    String faculty = facultyBox.getSelectedItem().toString();
                    String subject = subjectBox.getSelectedItem().toString();

                    String year = yearField.getText();
                    String section = sectionField.getText();

                    int facultyId = Integer.parseInt(faculty.split("-")[0]);
                    int subjectId = Integer.parseInt(subject.split("-")[0]);

                    String sql = "INSERT INTO faculty_subject(faculty_id,subject_id,year,section) VALUES(?,?,?,?)";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setInt(1, facultyId);
                    pst.setInt(2, subjectId);
                    pst.setString(3, year);
                    pst.setString(4, section);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Subject Assigned Successfully");

                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        setVisible(true);
    }

    void loadFaculty(){

        try{

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM faculty");

            while(rs.next()){

                facultyBox.addItem(rs.getInt("faculty_id")+"-"+rs.getString("name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    void loadSubjects(){

        try{

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM subject");

            while(rs.next()){

                subjectBox.addItem(rs.getInt("subject_id")+"-"+rs.getString("subject_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
