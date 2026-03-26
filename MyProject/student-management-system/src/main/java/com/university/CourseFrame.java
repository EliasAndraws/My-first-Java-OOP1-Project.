package com.university;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseFrame extends JFrame
{
    private final CourseDAO courseDAO;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField courseNameField,creditsField,instructorIdField,departmentIdField;

    public CourseFrame()
    {
        courseDAO = new CourseDAO();

        setTitle("Course Management System");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID","Course Name","Credits","Instructor ID","Dept ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5,2,10,10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        courseNameField = new JTextField();
        creditsField = new JTextField();
        instructorIdField = new JTextField();
        departmentIdField = new JTextField();

        formPanel.add(new JLabel("Course Name:")); formPanel.add(courseNameField);

        formPanel.add(new JLabel("Credits (Hours):")); formPanel.add(creditsField);

        formPanel.add(new JLabel("Instructor ID:")); formPanel.add(instructorIdField);

        formPanel.add(new JLabel("Department ID:")); formPanel.add(departmentIdField);

        JButton addButton = new JButton("Add Course");
        JButton deleteButton = new JButton("Delete Course");
        JButton refreshButton = new JButton("Refresh List");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
        addButton.addActionListener(e -> addCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        refreshButton.addActionListener(e -> loadCourses());

        loadCourses();
    }

    private void addCourse()
    {
        try
        {
            String name = courseNameField.getText();
            int credits = Integer.parseInt(creditsField.getText());
            int instId = Integer.parseInt(instructorIdField.getText());
            int deptId = Integer.parseInt(departmentIdField.getText());

            Course c = new Course();
            
            c.setCourseName(name);
            c.setCredits(credits);
            c.setInstructorId(instId);
            c.setDepartmentId(deptId);

            courseDAO.addCourse(c);
            
            courseNameField.setText("");
            creditsField.setText("");
            instructorIdField.setText("");
            departmentIdField.setText("");
            
            loadCourses(); 
        }
        catch (NumberFormatException ex){JOptionPane.showMessageDialog(this,"Please enter valid numbers for Credits/IDs");}
    }

    private void loadCourses()
    {
        tableModel.setRowCount(0);
        List<Course> courses = courseDAO.getAllCourses();
        
        for (Course c : courses)
        {
            tableModel.addRow(new Object[]
            {
                c.getCourseId(),
                c.getCourseName(),
                c.getCredits(),
                c.getInstructorId(),
                c.getDepartmentId()
            });
        }
    }

    private void deleteCourse()
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1)
        {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            courseDAO.deleteCourse(id);
            loadCourses();
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Select a course to delete!");
        }
    }
}