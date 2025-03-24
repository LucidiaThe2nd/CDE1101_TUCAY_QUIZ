package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    Scanner scanner = new Scanner(System.in);

    boolean isProgramRunning = true;

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
    

    public String[] fetchStudentID()
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT student_id FROM users");
            List<String> list = new java.util.ArrayList<String>();
            while (resultSet.next())
            {
                list.add(resultSet.getString("student_id"));
            }
            return list.toArray(new String[0]);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }

    public String[] fetchPasswords()
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT password FROM users");
            List<String> list = new java.util.ArrayList<String>();
            while (resultSet.next())
            {
                list.add(resultSet.getString("password"));
            }
            return list.toArray(new String[0]);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }

    /**
     * Registers a new user with the given details.
     * 
     * This method will execute a SQL query to insert a new user with the given
     * details into the database. A success message will be printed if the
     * registration is successful. If an error occurs, the error message will be
     * printed.
     * 
     * @param newID The new student ID to be registered.
     * @param newFirstName The new first name to be registered.
     * @param newLastName The new last name to be registered.
     * @param newPassword The new password to be registered.
     * @return true if the registration is successful, false otherwise.
     */
    public boolean registerUser(int newID, String newFirstName, String newLastName, String newPassword)
    {
        try
        {
            String sql = "INSERT INTO users (student_id, first_name, last_name, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newID);
            stmt.setString(2, newFirstName);
            stmt.setString(3, newLastName);
            stmt.setString(4, newPassword);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println(rowsInserted + " User registered successfully.");
            }

            stmt.close();

            return true;
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong: Please try again.");
        }

        return false;
    }

    public boolean hasUnfinishedQuiz(int userId)
    {
        String sql = "SELECT COUNT(*) FROM quiz_sessions WHERE student_id = ? AND completed = FALSE";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) return true;

        }
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    public void generateQuizQuestions(int thisID)
    {
        String sql = "SELECT question_id FROM questions ORDER BY RAND() LIMIT 10";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            String insertSql = "INSERT INTO quiz_sessions (student_id, question_id, current_position) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            
            int position = 1;
            while (rs.next())
            {
                insertStmt.setInt(1, thisID);
                insertStmt.setInt(2, rs.getInt("question_id"));
                insertStmt.setInt(3, position);
                insertStmt.executeUpdate();
                position++;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public List<Question> getQuizQuestions(int userId)
    {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.question_id, q.question, q.option_1, q.option_2, q.option_3, q.option_4, s.user_answer " +
                     "FROM quiz_sessions s JOIN questions q ON s.question_id = q.question_id " +
                     "WHERE s.student_id = ? AND s.completed = FALSE ORDER BY s.session_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql))
            {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next())
                {
                    questions.add(new Question(
                            rs.getInt("question_id"),
                            rs.getString("question"),
                            rs.getString("option_1"),
                            rs.getString("option_2"),
                            rs.getString("option_3"),
                            rs.getString("option_4"),
                            rs.getString("user_answer")
                    ));
                }
            } 
            catch (SQLException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
            return questions;
        }
        
    public int getCurrentPosition(int userId)
    {
        String sql = "SELECT current_position FROM quiz_sessions WHERE student_id = ? LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("current_position");

        }
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return 1;
    }

    public void updateCurrentPosition(int userId, int position)
    {
        String sql = "UPDATE quiz_sessions SET current_position = ? WHERE student_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, position);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void saveAnswer(int userId, int questionId, String answer)
    {
        String sql = "UPDATE quiz_sessions SET user_answer = ? WHERE student_id = ? AND question_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, answer);
            stmt.setInt(2, userId);
            stmt.setInt(3, questionId);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void markQuizComplete(int userId)
    {
        String sql = "UPDATE quiz_sessions SET completed = TRUE WHERE student_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    public boolean isProgramRunning()
    {
        return isProgramRunning;
    }

    public void setIsProgramRunning(boolean isProgramRunning)
    {
        this.isProgramRunning = isProgramRunning;
    }
}
