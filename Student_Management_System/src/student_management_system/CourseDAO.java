package student_management_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO
{
    public void addCourse(Course course)
    {
        String sql = "INSERT INTO Courses (course_name, credits,instructor_id,department_id) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try
        {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);
        
            try (PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, course.getCourseName());
                pstmt.setInt(2, course.getCredits());
                pstmt.setInt(3, course.getInstructorId());
                pstmt.setInt(4, course.getDepartmentId());

                int affectedRows = pstmt.executeUpdate();
            
                conn.commit();

                if (affectedRows > 0)
                {
                    System.out.println("Course added successfully.");
                }
            }
            catch (SQLException e)
            {
                try
                {
                conn.rollback();
                }
                catch (SQLException ex){}
                e.printStackTrace();
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteCourse(int courseId)
    {
        String sql = "DELETE FROM Courses WHERE course_id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
            System.out.println("Course deleted successfully!");
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Course> getAllCourses()
    {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM Courses";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                Course course = new Course();
                
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setCredits(rs.getInt("credits"));
                course.setInstructorId(rs.getInt("instructor_id"));
                course.setDepartmentId(rs.getInt("department_id"));
                courseList.add(course);
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
        return courseList;
    }
}