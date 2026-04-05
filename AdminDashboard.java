
package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(5,1,10,10));

        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddStudent();
            }
        });
        JButton addFacultyBtn = new JButton("Add Faculty");
        addFacultyBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddFaculty();
            }
        });
        JButton addSubjectBtn = new JButton("Add Subject");
        addSubjectBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               new AddSubject();
            }
        });
        JButton assignSubjectBtn = new JButton("Assign Subject");
        assignSubjectBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                 new AssignSubject();
            }
        });
        JButton logoutBtn = new JButton("Logout");

        add(addStudentBtn);
        add(addFacultyBtn);
        add(addSubjectBtn);
        add(assignSubjectBtn);
        add(logoutBtn);

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();
            }
        });

        setVisible(true);
    }
}
