package main;

public class MainInstance
{
	public static void main(String[] args)
	{
		QuizManager quizManager = new QuizManager();
		quizManager.setIsProgramRunning(true);

		quizManager.setPassword("Amogamog");
		quizManager.connectToDatabase();

		while (quizManager.getIsProgramRunning())
		{
			quizManager.menu();
		}

		quizManager.closeAllConnection();
		System.exit(0);
	}
}
