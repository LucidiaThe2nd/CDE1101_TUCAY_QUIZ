package main;

import java.util.Scanner;

public class MainInstance
{
	public static void main(String[] args)
	{
		DatabaseCommunicator databaseCommunicator = DatabaseCommunicator.getInstance();
		QuizManager quizManager = QuizManager.getInstance();
		UserAuthenticator userAuthenticator = UserAuthenticator.getInstance();
		Scanner scanner = new Scanner(System.in);
		quizManager.setIsProgramRunning(true);
		
		System.out.print("Enter database password: ");
		String password = scanner.nextLine();
		databaseCommunicator.setDatabasePassword(password);
		databaseCommunicator.connectToDatabase();

		while (quizManager.getIsProgramRunning())
		{
			quizManager.menu();
		}

		databaseCommunicator.closeAllConnection();
		scanner.close();
		System.exit(0);
	}
}
