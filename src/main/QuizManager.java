package main;

import java.util.Scanner;
import java.util.Random;

public class QuizManager 
{
    private int id;
    private String question, option1, option2, option3, option4, answer;
    private Scanner scanner = new Scanner(System.in);


    private int questionCount;

    private Boolean isProgramRunning = true;

    private QuizManager() {}

    private static class QuizManagerHolder
    {
        private static final QuizManager INSTANCE = new QuizManager();
    }

    /**
     * Gets the instance of the QuizManager class.
     * 
     * This method returns the singleton instance of the QuizManager class.
     * 
     * @return The singleton instance of the QuizManager class.
     */
    public static QuizManager getInstance()
    {
        return QuizManagerHolder.INSTANCE;
    }

    public Boolean getIsProgramRunning()
    {
        return isProgramRunning;
    }

    public void setIsProgramRunning(Boolean isProgramRunning)
    {
        this.isProgramRunning = isProgramRunning;
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

        DatabaseCommunicator.getInstance().fetchCurrentQuestion(id);

        printQuestion();
    }

    void fetchRandomQuestion()
    {
        Random random = new Random();
        id = random.nextInt(questionCount) + 1;
        printQuestion();
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
                fetchCurrentQuestion(id);
                return;
            case "z":
                id -= 1;
                fetchCurrentQuestion(id);
                return;
            case "r":
                menu();
                return;
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
        System.out.println("2. Random Questions");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        switch (scanner.nextInt())
        {
            case 1:
                id = 1;
                DatabaseCommunicator.getInstance().getQuestionCount();
                fetchCurrentQuestion(id);
                break;
            case 2:
                break;
            case 3:
                setIsProgramRunning(false);
                DatabaseCommunicator.getInstance().closeAllConnection();
                System.exit(0);
                return;
            default:
                break;
        }
    }

    public void setID(int thisValue)
    {
        this.id = thisValue;
    }

    public int getID()
    {
        return this.id;
    }

    public void setQuestion(String thisValue)
    {
        this.question = thisValue;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public void setOption1(String thisValue)
    {
        this.option1 = thisValue;
    }

    public String getOption1()
    {
        return this.option1;
    }

    public void setOption2(String thisValue)
    {
        this.option2 = thisValue;
    }

    public String getOption2()
    {
        return this.option2;
    }

    public void setOption3(String thisValue)
    {
        this.option3 = thisValue;
    }

    public String getOption3()
    {
        return this.option3;
    }

    public void setOption4(String thisValue)
    {
        this.option4 = thisValue;
    }

    public String getOption4()
    {
        return this.option4;
    }

    public void setAnswer(String thisValue)
    {
        this.answer = thisValue;
    }

    public String getAnswer()
    {
        return this.answer;
    }

    public void setQuestionCount(int thisValue)
    {
        this.questionCount = thisValue;
    }
}
