package main;

import java.util.Scanner;
import java.util.List;
import java.util.Random;

public class QuizManager 
{
    private int id;
    private String question, option1, option2, option3, option4, answer;
    private Scanner scanner = new Scanner(System.in);

    DatabaseCommunicator databaseCommunicator;
    UserAuthenticator userAuthenticator = UserAuthenticator.getInstance();

    private int questionCount;

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

    public void startQuiz(int userId)
    {
        if (!databaseCommunicator.hasUnfinishedQuiz(userId)) {
            databaseCommunicator.generateQuizQuestions(userId);
        }

        resumeQuiz(userId);
    }

    private void resumeQuiz(int userId) {
        List<Question> questions = databaseCommunicator.getQuizQuestions(userId);
        int position = databaseCommunicator.getCurrentPosition(userId);
        int totalQuestions = questions.size();

        if (questions.isEmpty()) {
            System.out.println("No quiz found.");
            return;
        }

        while (true) {
            Question q = questions.get(position - 1);

            System.out.println("\nQuestion " + position + "/" + totalQuestions);
            System.out.println(q.getQuestion());
            System.out.println("A) " + q.getOptionA());
            System.out.println("B) " + q.getOptionB());
            System.out.println("C) " + q.getOptionC());
            System.out.println("D) " + q.getOptionD());
            System.out.println("[Your Answer: " + (q.getUserAnswer() != null ? q.getUserAnswer() : "None") + "]");

            System.out.println("\nOptions: [N]ext | [P]revious | [A-D] Answer | [S]ubmit Quiz | [E]xit and Resume Later");
            System.out.print("Choice: ");
            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "N":
                    if (position < totalQuestions) {
                        position++;
                        databaseCommunicator.updateCurrentPosition(userId, position);
                    } else {
                        System.out.println("You're on the last question.");
                    }
                    break;

                case "P":
                    if (position > 1) {
                        position--;
                        databaseCommunicator.updateCurrentPosition(userId, position);
                    } else {
                        System.out.println("You're on the first question.");
                    }
                    break;

                case "A": case "B": case "C": case "D":
                databaseCommunicator.saveAnswer(userId, q.getId(), input);
                    q.setUserAnswer(input);
                    System.out.println("Answer saved!");
                    break;
                case "E":
                    System.out.println("Your progress has been saved. You can resume later.");
                    return; // Exit the quiz loop but keep progress saved.
                case "S":
                    if (confirmSubmit()) {
                        databaseCommunicator.markQuizComplete(userId);
                        System.out.println("Quiz submitted successfully!");
                        return;
                    }
                    break;

                default:
                    System.out.println("Invalid input! Try again.");
            }
        }
    }

    private boolean confirmSubmit()
    {
        System.out.print("Are you sure you want to submit the quiz? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("yes");
    }

    void menu()
    {
        if (databaseCommunicator == null)
        {
            databaseCommunicator = DatabaseCommunicator.getInstance();
        }

        System.out.println("1. Start Quiz");
        System.out.println("2. Resume Quiz");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");

        switch (scanner.nextInt())
        {
            case 1:
                scanner.nextLine();
                startQuiz(userAuthenticator.getCurrentStudentID());
                break;
            case 2:
                scanner.nextLine();
                resumeQuiz(userAuthenticator.getCurrentStudentID());
                break;
            case 3:
                System.out.println("Logging out...");
                userAuthenticator.setLoggedOut();
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
