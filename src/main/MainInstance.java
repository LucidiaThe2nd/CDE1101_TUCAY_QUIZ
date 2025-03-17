package main;

import java.util.Scanner;

public class MainInstance
{
	public static void main(String[] args)
	{
		QuizManager quizManager = new QuizManager();
		Scanner scanner = new Scanner(System.in);
		quizManager.setIsProgramRunning(true);

		String password = scanner.nextLine();
		quizManager.setPassword(password);
		quizManager.connectToDatabase();

		while (quizManager.getIsProgramRunning())
		{
			quizManager.menu();
		}

		quizManager.closeAllConnection();
		scanner.close();
		System.exit(0);
	}
}
