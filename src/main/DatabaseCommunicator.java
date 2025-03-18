package main;

import java.sql.*;

public class DatabaseCommunicator
{
    private DatabaseCommunicator() {}

    private static class DatabaseCommunicatorHolder
    {
        private static DatabaseCommunicator INSTANCE = new DatabaseCommunicator();
    }

    public static DatabaseCommunicator getInstance()
    {
        return DatabaseCommunicatorHolder.INSTANCE;
    }

    Connection connection;
    String password;

    QuizManager quizManager = QuizManager.getInstance();

    /**
     * Connects to the database.
     * 
     * This method will execute a SQL query to connect to the database, and print
     * a success message if the connection is successful. If an error occurs, the
     * error message will be printed.
     */
    public void connectToDatabase()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cde1101_daaf_tucay", "root", password);
            System.out.println("Connected to the database");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Failed to connect to the database. Exiting...");
            System.exit(1);
        }   
    }

    public void setDatabasePassword(String password)
    {
        this.password = password;
    }

    public void fetchCurrentQuestion(int thisID)
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM questions WHERE question_id = " + quizManager.getID());
            while (resultSet.next())
            {
                quizManager.setQuestion(resultSet.getString("question"));
                quizManager.setOption1(resultSet.getString("option1"));
                quizManager.setOption2(resultSet.getString("option2"));
                quizManager.setOption3(resultSet.getString("option3"));
                quizManager.setOption4(resultSet.getString("option4"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void getQuestionCount()
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM questions");
            while (resultSet.next())
            {
                quizManager.setQuestionCount(resultSet.getInt(1));
            }
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
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
