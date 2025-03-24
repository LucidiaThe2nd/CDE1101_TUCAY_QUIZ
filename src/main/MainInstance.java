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
		databaseCommunicator.setIsProgramRunning(true);
		
		System.out.print("Enter database password: ");
		String password = scanner.nextLine();
		databaseCommunicator.setDatabasePassword(password);
		databaseCommunicator.connectToDatabase();
		
		while (databaseCommunicator.isProgramRunning())
		{
			while (!userAuthenticator.isLoggedIn() && databaseCommunicator.isProgramRunning())
			{	
				userAuthenticator.mainMenu();
			}
		
			while (userAuthenticator.isLoggedIn() && databaseCommunicator.isProgramRunning())
			{
				quizManager.menu();
			}
		}

		databaseCommunicator.closeAllConnection();
		scanner.close();
		System.exit(0);
	}
}
