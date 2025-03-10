package main;

import java.sql.*;

public class QuizManager 
{
    private int id;
    private String question, option1, option2, option3, option4, answer;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void connectToDatabase()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cde1101_daaf_tucay", "root", "1234");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }   
    }

    public void closeAllConnection()
    {
        try
        {
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void menu()
    {
        System.out.println("1. Answer Questions");
        System.out.println("2. Exit");
    }
}
