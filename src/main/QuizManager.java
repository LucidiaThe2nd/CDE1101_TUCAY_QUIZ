package main;

import java.sql.*;
import java.util.Scanner;

public class QuizManager 
{
    private int id;
    private String question, option1, option2, option3, option4, answer;
    private Scanner scanner = new Scanner(System.in);

    private String password;

    private int questionCount;

    private Connection connection;
    private ProgramStatus programStatus;

    private Boolean isProgramRunning = true;

    public QuizManager()
    {
        programStatus = ProgramStatus.MENU;
    }

    public Boolean getIsProgramRunning()
    {
        return isProgramRunning;
    }

    public void setIsProgramRunning(Boolean isProgramRunning)
    {
        this.isProgramRunning = isProgramRunning;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

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

    void closeAllConnection()
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

    /**
     * Fetches the question with the given ID from the database and prints it.
     * 
     * This method will execute a SQL query to fetch the question with the given
     * ID from the database, and then call the printQuestion method to print it.
     * If an error occurs, the error message will be printed.
     * 
     * @param thisID The ID of the question to be fetched.
     */
    void fetchCurrentQuestion(int thisID)
    {   
        if (thisID > questionCount)
        {
            id -= 1;
        }
        else if (thisID < 1)
        {
            id += 1;
        }
        else
        {
            
        }


        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM questions WHERE question_id = " + id);
            while (resultSet.next())
            {
                question = resultSet.getString("question");
                option1 = resultSet.getString("option_1");
                option2 = resultSet.getString("option_2");
                option3 = resultSet.getString("option_3");
                option4 = resultSet.getString("option_4");
                answer = resultSet.getString("answer");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        printQuestion();
    }

    /**
     * Fetches the total number of questions from the database.
     * 
     * This method will execute a SQL query to fetch the total number of
     * questions from the database, and store the result in the questionCount
     * field. If an error occurs, the error message will be printed.
     */
    void getQuestionCount()
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM questions");
            while (resultSet.next())
            {
                questionCount = resultSet.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Prints the current question and its options, and accepts user input.
     * 
     * This method will print the current question and its options, and then
     * wait for the user to input an answer. The user's answer will be compared
     * to the correct answer, and a message will be printed according to whether
     * the answer is correct or not. The user can also input a command to go to
     * the next question, go back to the previous question, or return to the
     * main menu.
     */
    void printQuestion()
    {
        scanner.nextLine();
        programStatus = ProgramStatus.QUIZ;

        System.out.println("Question #" + id + ": " + question);
        System.out.println("A. " + option1);
        System.out.println("B. " + option2);
        System.out.println("C. " + option3);
        System.out.println("D. " + option4);
        System.out.println("===============================");
        if (id == 1)
        {
            System.out.println("X: Next Question");
        }
        else if (id == questionCount)
        {
            System.out.println("Z: Previous Question");
        }
        else 
        {
            System.out.println("Z: Previous Question | X: Next Question");
        }

        System.out.println("R: Return to Menu");

        System.out.print("Enter Input: ");
        String userAnswer = scanner.nextLine();

        switch (userAnswer)
        {
            case "x":
                id += 1;
                break;
            case "z":
                id -= 1;
                break;
            case "r":
                programStatus = ProgramStatus.MENU;
                menu();
                break;
            default:
                if (userAnswer.equals(answer))
                {
                    System.out.println("Correct Answer!");
                    id += 1;
                }
                else
                {
                    System.out.println("Wrong Answer!");
                }
                break;
        }

        System.out.println("===============================");
        System.out.println("Press Enter to Continue...");

        fetchCurrentQuestion(id);
    }

    /**
     * Displays the main menu of the quiz application and handles user input.
     * 
     * The menu offers two options:
     * 1. Start answering quiz questions.
     * 2. Exit the application.
     * 
     * Based on the user's choice, the program will either set the status to QUIZ
     * and fetch the first question, or it will stop the program from running.
     */
    void menu()
    {
        System.out.println("1. Answer Questions");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");

        switch (scanner.nextInt())
        {
            case 1:
                id = 1;
                getQuestionCount();
                fetchCurrentQuestion(id);
                break;
            case 2:
                setIsProgramRunning(false);
                closeAllConnection();
                System.exit(0);
                return;
            default:
                break;
        }
    }
}
