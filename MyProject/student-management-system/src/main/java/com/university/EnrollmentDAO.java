package com.university;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO
{
    public List<String[]> getStudentEnrollments()
    {
        List<String[]> list = new ArrayList<>();
        
        String sql = "SELECT s.first_name, s.last_name, c.course_name, e.grade "+"FROM Enrollments e "+"JOIN Students s ON e.student_id = s.student_id "+"JOIN Courses c ON e.course_id = c.course_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                String courseName = rs.getString("course_name");
                String grade = rs.getString("grade");
                
                if (grade == null) grade = "-";

                list.add(new String[]{fullName,courseName,grade});
            }
        }
        
        catch (SQLException e) { e.printStackTrace(); }
        
        return list;
    }

    public void enrollStudent(int studentId,int courseId)
    {
        String sql = "INSERT INTO Enrollments (student_id,course_id) VALUES (?,?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {          
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.executeUpdate();  
        }
        
        catch (SQLException e) { e.printStackTrace(); }
    }
    
    public void updateGrade(int studentId,int courseId,String newGrade)
    {
        String sql = "UPDATE Enrollments SET grade = ? WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {        
            pstmt.setString(1, newGrade);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseId);
            pstmt.executeUpdate();       
        }
    
        catch (SQLException e) { e.printStackTrace(); }
    }
    
    public void deleteEnrollment(int studentId,int courseId)
    {
        String sql = "DELETE FROM Enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.executeUpdate();   
        }
    
        catch (SQLException e) { e.printStackTrace(); }
    
    }
    
    public List<Object[]> getStudentEnrollmentsWithIds()
    {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT e.student_id, e.course_id, s.first_name, s.last_name, c.course_name, e.grade "+"FROM Enrollments e "+"JOIN Students s ON e.student_id = s.student_id "+"JOIN Courses c ON e.course_id = c.course_id";

        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql))
        { 
            while (rs.next())
            {
                int sId = rs.getInt("student_id");
                int cId = rs.getInt("course_id");
                String fullName = rs.getString("first_name") +" "+ rs.getString("last_name");
                String courseName = rs.getString("course_name");
                String grade = rs.getString("grade");
                if (grade == null) grade = "-";

                list.add(new Object[]{sId, cId, fullName, courseName, grade});
            }   
        }
    
        catch (SQLException e){e.printStackTrace();}
        
        return list;
    }
}