package main;

public class Question
{
    private int id;
    private String question, optionA, optionB, optionC, optionD, userAnswer;

    public Question(int id, String question, String optionA, String optionB, String optionC, String optionD, String userAnswer) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.userAnswer = userAnswer;
    }

    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String answer) { this.userAnswer = answer; }
}