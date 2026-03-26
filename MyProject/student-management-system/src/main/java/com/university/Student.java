package com.university;

import java.sql.Date;

public class Student
{
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private Date enrollmentDate;

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Date getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    @Override
    public String toString()
    {
        return "Student [ID=" +studentId +", Name=" +firstName +" "+ lastName+", Email="+email+"]";
    }
}