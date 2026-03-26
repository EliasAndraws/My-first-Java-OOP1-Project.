package student_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection
{
    private static final String URL = "jdbc:mysql://localhost:3306/university_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root-123";

    public static Connection getConnection()
    {
        Connection connection = null;
        
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Driver not found! Check your Libraries.");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed! Check DB URL/User/Pass.");
            e.printStackTrace();
        }
        return connection;
    }
}