package com.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO
{
    public void addStudent(Student student)
    {
        String sql = "INSERT INTO Students (first_name , last_name, email , date_of_birth, enrollment_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))   
            {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setDate(4, student.getDateOfBirth());
            pstmt.setDate(5, student.getEnrollmentDate());
            pstmt.executeUpdate();

            System.out.println("Student added successfully.");
            }

        catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteStudent(int studentId)
    {
        String sql = "DELETE FROM Students WHERE student_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully!");
        }
        
        catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Student> getAllStudents()
    {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
            {
                while (rs.next())
                {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setDateOfBirth(rs.getDate("date_of_birth"));
                student.setEnrollmentDate(rs.getDate("enrollment_date"));
                students.add(student);
                }
            }
        
        catch (SQLException e) { e.printStackTrace(); }

        return students;
    }

}