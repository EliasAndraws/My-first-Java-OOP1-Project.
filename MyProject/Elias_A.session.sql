package com.university;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class MainApp
{
    public static void main(String[] args)
    {
            SwingUtilities.invokeLater(() -> {
            new StudentFrame().setVisible(true);
        });

        StudentDAO studentDAO = new StudentDAO();
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("--- Student Management System ---");
            System.out.println("1. Add a new student");
            System.out.println("2. View all students");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice)
            {
                case 1->
                {
                    Student newStudent = new Student();
                    System.out.print("Enter your first name: ");
                    newStudent.setFirstName(scanner.nextLine());
                    System.out.print("Enter your last name: ");
                    newStudent.setLastName(scanner.nextLine());
                    System.out.print("Enter your email: ");
                    newStudent.setEmail(scanner.nextLine());
                    System.out.print("Enter your date of birth (YYYY-MM-DD): ");
                    newStudent.setDateOfBirth(Date.valueOf(scanner.nextLine()));
                    newStudent.setEnrollmentDate(new Date(System.currentTimeMillis()));
                    studentDAO.addStudent(newStudent);
                }

                case 2->
                {
                    List<Student> students = studentDAO.getAllStudents();
                    for (Student student : students)
                    {
                        System.out.println(student);
                    }
                }
                
                case 3->
                {
                    System.out.println("Exiting...");
                    //scanner.close();
                    System.exit(0);
                }

                default-> System.out.println("Invalid option.");
            }
        }
    }
}