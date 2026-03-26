package com.university;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;

public class StudentFrame extends JFrame
{
    private final StudentDAO studentDAO = new StudentDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField firstName,lastName,email,dateOfBirth,enrollmentDate;

    public StudentFrame()
    {
        setTitle("Student Management System");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID","First Name","Last Name","Email","Date of Birth","Enrollment Date"};
        tableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3,4,10,10)); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 

        firstName = new JTextField();
        lastName = new JTextField();
        email = new JTextField();
        dateOfBirth = new JTextField();
        enrollmentDate = new JTextField(LocalDate.now().toString());

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstName);

        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastName);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(email);

        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        formPanel.add(dateOfBirth);

        formPanel.add(new JLabel("Enrollment Date (YYYY-MM-DD):"));
        formPanel.add(enrollmentDate);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton refreshButton = new JButton("Refresh List");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel,BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        refreshButton.addActionListener(e -> loadStudents());
        deleteButton.addActionListener(e -> deleteStudents());

        loadStudents();
    }

    private void loadStudents()
    {
        tableModel.setRowCount(0);
        
        List<Student> students = studentDAO.getAllStudents(); 

        for (Student s : students)
        {
            Object[] row =
            {
                s.getStudentId(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.getDateOfBirth(),
                s.getEnrollmentDate()
            };
            tableModel.addRow(row);
        }
    }

    private void addStudent()
    {
        try
        {
            String fName = firstName.getText();
            String lName = lastName.getText();
            String emailVal = email.getText();
            String dobStr = dateOfBirth.getText();
            String enrollStr = enrollmentDate.getText();
            
            if (fName.isEmpty() || lName.isEmpty() || emailVal.isEmpty())
            {
                JOptionPane.showMessageDialog(this,"Please fill in all text fields.");
                return;
            }

            Student s = new Student();
            s.setFirstName(fName);
            s.setLastName(lName);
            s.setEmail(emailVal);
            s.setDateOfBirth(Date.valueOf(dobStr));
            s.setEnrollmentDate(Date.valueOf(enrollStr));

            studentDAO.addStudent(s);

            JOptionPane.showMessageDialog(this,"Student Added Successfully!");
            
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            dateOfBirth.setText("");
            enrollmentDate.setText("");
            
            loadStudents();
        }
        
        catch (IllegalArgumentException e) { JOptionPane.showMessageDialog(this,"Date format error! Please use YYYY-MM-DD"); }
    }
    
    private void deleteStudents()
    {
        int selectedRow = table.getSelectedRow();
    
        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(this,"Please select a student to delete first!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this student?","Confirm Delete",JOptionPane.YES_NO_OPTION);
    
        if (confirm == JOptionPane.YES_OPTION)
        {
                int student_id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                
                studentDAO.deleteStudent(student_id);

                loadStudents(); 
            
                JOptionPane.showMessageDialog(this,"Student deleted successfully!");
        }
    }
}