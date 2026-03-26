package com.university;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EnrollmentFrame extends JFrame
{

    private JComboBox<ComboItem> studentCombo;
    private JComboBox<ComboItem> courseCombo;
    private JTextField gradeField;
    private JTextField searchField;
    
    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();

    private int selectedStudentId = -1;
    private int selectedCourseId = -1;

    public EnrollmentFrame()
    {
        setTitle("Enrollment Management (إدارة التسجيل والعلامات)");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3,1,5,5));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search Filter");
        searchPanel.add(new JLabel("Search (Name/Course): "));
        searchPanel.add(searchField);
        
        JPanel comboPanel = new JPanel(new FlowLayout());
        studentCombo = new JComboBox<>();
        courseCombo = new JComboBox<>();
        gradeField = new JTextField(5);
        comboPanel.add(new JLabel("Student:"));
        comboPanel.add(studentCombo);
        comboPanel.add(new JLabel("Course:"));
        comboPanel.add(courseCombo);
        comboPanel.add(new JLabel("Grade:"));
        comboPanel.add(gradeField);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton enrollBtn = new JButton("Add Enrollment");
        JButton updateBtn = new JButton("Update Grade");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        enrollBtn.setBackground(new Color(60,179,113));
        updateBtn.setBackground(new Color(70,130,180));
        deleteBtn.setBackground(new Color(178,34,34));
        enrollBtn.setForeground(Color.WHITE);
        updateBtn.setForeground(Color.WHITE);
        setForeground(Color.WHITE);

        btnPanel.add(enrollBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);

        topPanel.add(searchPanel);
        topPanel.add(comboPanel);
        topPanel.add(btnPanel);
        add(topPanel, BorderLayout.NORTH);

        String[] colNames = {"sID","cID","Student Name","Course Name","Grade"};
        
        tableModel = new DefaultTableModel(colNames, 0)
        {
            @Override
            public boolean isCellEditable(int row,int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setWidth(0);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadComboData();
        loadTableData();

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
        {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {filter();}
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {filter();}
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {filter();}
            private void filter()
            {
                String text = searchField.getText();
                if (text.trim().length() == 0)
                {
                    sorter.setRowFilter(null);
                }
                else
                {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
                }
            }
        });

        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int viewRow = table.getSelectedRow();
                if (viewRow != -1)
                {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    
                    selectedStudentId = (int) tableModel.getValueAt(modelRow, 0);
                    selectedCourseId = (int) tableModel.getValueAt(modelRow, 1);
                    String grade = (String) tableModel.getValueAt(modelRow, 4);

                    gradeField.setText(grade.equals("-") ? "" : grade);
                }
            }
        });

        enrollBtn.addActionListener(e ->
        {
            ComboItem sItem = (ComboItem) studentCombo.getSelectedItem();
            ComboItem cItem = (ComboItem) courseCombo.getSelectedItem();
            if (sItem != null && cItem != null)
            {
                enrollmentDAO.enrollStudent(sItem.id, cItem.id);
                loadTableData();
            }
        });

        updateBtn.addActionListener(e ->
        {
            if (selectedStudentId != -1 && selectedCourseId != -1)
            {
                String newGrade = gradeField.getText();
                enrollmentDAO.updateGrade(selectedStudentId,selectedCourseId,newGrade);
                loadTableData();
                JOptionPane.showMessageDialog(this,"Grade Updated!");
                clearSelection();
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Please select a row first!");
            }
        });

        deleteBtn.addActionListener(e ->
        {
            if (selectedStudentId != -1 && selectedCourseId != -1)
            {
                int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this enrollment?");
                if (confirm == JOptionPane.YES_OPTION)
                {
                    enrollmentDAO.deleteEnrollment(selectedStudentId,selectedCourseId);
                    loadTableData();
                    clearSelection();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please select a row to delete!");
            }
        });
        refreshBtn.addActionListener(e -> loadTableData());
    }

    private void loadTableData()
    {
        tableModel.setRowCount(0);
        List<Object[]> list = enrollmentDAO.getStudentEnrollmentsWithIds();
        for (Object[] row : list)
        {
            tableModel.addRow(row);
        }
    }

    private void clearSelection()
    {
        selectedStudentId = -1;
        selectedCourseId = -1;
        gradeField.setText("");
        table.clearSelection();
    }

    private void loadComboData()
    {
        studentCombo.removeAllItems();
        courseCombo.removeAllItems();
        
        List<Student> students = studentDAO.getAllStudents();
        
        for (Student s : students)
        {
            studentCombo.addItem(new ComboItem(s.getStudentId(), s.getFirstName() + " " + s.getLastName()));
        }

        List<Course> courses = courseDAO.getAllCourses();
        
        for (Course c : courses)
        {
            courseCombo.addItem(new ComboItem(c.getCourseId(),c.getCourseName()));
        }
    }
    class ComboItem
    {
        int id;
        String label;
        public ComboItem(int id, String label){this.id = id;this.label = label;}
        @Override
        public String toString() {return label;}
    }
}