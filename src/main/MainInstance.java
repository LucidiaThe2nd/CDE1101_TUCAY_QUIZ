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
		
		while (true)
		{			
			System.out.print("Enter username: ");
			String loginUsername = scanner.nextLine();
			System.out.print("Enter password: ");
			String loginPassword = scanner.nextLine();
			
			userAuthenticator.setUsernames(databaseCommunicator.fetchStudentID());
			userAuthenticator.setPasswords(databaseCommunicator.fetchPasswords());
			if (userAuthenticator.loginVerification(loginUsername, loginPassword))
			{
				break;
			}
			else 
			{
				System.out.println("Invalid username or password.");
			}
		}

		System.out.println("Logged in!");

		while (quizManager.getIsProgramRunning())
		{
			quizManager.menu();
		}

		databaseCommunicator.closeAllConnection();
		scanner.close();
		System.exit(0);
	}
}
