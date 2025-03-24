package main;

import java.util.Scanner;

public class UserAuthenticator
{
    private boolean isLoggedIn = false;

    String[] usernames;
    String[] passwords;

    int currentStudentID;
    DatabaseCommunicator databaseCommunicator;
    
    Scanner scanner = new Scanner(System.in);

    /************************ SINGLETON PLATTERN ***************************/
    private UserAuthenticator() {}

    private static class UserAuthenticatorHolder
    {
        private static UserAuthenticator INSTANCE = new UserAuthenticator();
    }

    public static UserAuthenticator getInstance()
    {
        return UserAuthenticatorHolder.INSTANCE;
    }
    /***********************************************************************/

    public void checkUsernames()
    {
        for (int i = 0; i < usernames.length; i++)
        {
            System.out.println(usernames[i]);
        }
    }

    public void setUsernames(String[] usernames)
    {
        this.usernames = usernames;
    }

    public void setPasswords(String[] passwords)
    {
        this.passwords = passwords;
    }

    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    public int getCurrentStudentID()
    {
        return currentStudentID;
    }

    public boolean loginVerification(String username, String password)
    {
        for (int i = 0; i < usernames.length; i++)
        {
            if (usernames[i].equals(username) && passwords[i].equals(password))
            {
                isLoggedIn = true;
                return isLoggedIn;
            }
        }

        return false;
    }

    public void setLoggedOut()
    {
        isLoggedIn = false;
    }

    public void registerUser()
    {
        scanner.nextLine();
        System.out.println("To cancel, leave either field empty and press enter.");
        int newID = getInt();
        if (newID <= -1)
        {
            return;
        }

        System.out.print("Enter new First Name: ");
        String newFirstName = scanner.nextLine();
        if (newFirstName.isEmpty())
        {
            return;
        }

        System.out.print("Enter new Last Name: ");
        String newLastName = scanner.nextLine();
        if (newLastName.isEmpty())
        {
            return;
        }

        System.out.print("Enter new Password: ");
        String newPassword = scanner.nextLine();
        if (newPassword.isEmpty())
        {
            return;
        }

        if (databaseCommunicator.registerUser(newID, newFirstName, newLastName, newPassword))
        {
            mainMenu();
        }
        else
        {
            registerUser();
        }
    }

    public void loginUser()
    {
        scanner.nextLine();
        System.out.println("To cancel, leave either field empty and press enter.");
        System.out.print("Enter ID: ");
        String loginUsername = scanner.nextLine();
        if (loginUsername.isEmpty())
        {
            return;
        }

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();
        if (loginPassword.isEmpty())
        {
            return;
        }

        setUsernames(databaseCommunicator.fetchStudentID());
        setPasswords(databaseCommunicator.fetchPasswords());

        if (loginVerification(loginUsername, loginPassword))
        {
            currentStudentID = Integer.parseInt(loginUsername);
            System.out.println("Login successful.");
        }
        else 
        {
            System.out.println("Invalid username or password.");
            loginUser();
        }
    }

    public void mainMenu()
    {
        if (databaseCommunicator == null)
        {
            databaseCommunicator = DatabaseCommunicator.getInstance();
        }

        System.out.println("Main Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        switch (scanner.nextInt())
        {
            case 1:
                loginUser();
                break;
            case 2:
                registerUser();
                break;
            case 3:
            databaseCommunicator.setIsProgramRunning(false);
        }
    }

    public int getInt()
    {
        while (true)
        {
            System.out.print("Enter ID: ");
            String input = scanner.nextLine();

            if (input.isEmpty())
            {
                return -1;
            }
            
            try 
            {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
    
}
