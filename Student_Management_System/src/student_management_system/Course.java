package student_management_system;

public class Course
{
    private int courseId;
    private String courseName;
    private int credits;
    private int instructorId;
    private int departmentId;  
    
    public Course()
    {}

    public int getCourseId() {return courseId;}
    public void setCourseId(int courseId) {this.courseId = courseId;}

    public String getCourseName() {return courseName;}
    public void setCourseName(String courseName) {this.courseName = courseName;}

    public int getCredits() {return credits;}
    public void setCredits(int credits) {this.credits = credits;}

    public int getInstructorId() {return instructorId;}
    public void setInstructorId(int instructorId) {this.instructorId = instructorId;}

    public int getDepartmentId() {return departmentId;}
    public void setDepartmentId(int departmentId) {this.departmentId = departmentId;}
    
    @Override
    public String toString()
    {
    return courseName + " (ID: " + courseId + ")";
    }
}