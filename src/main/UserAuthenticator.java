package main;

import java.sql.*;

public class UserAuthenticator
{
    DatabaseCommunicator databaseCommunicator = DatabaseCommunicator.getInstance();

    private boolean isLoggedIn = false;

    String[] usernames;
    String[] passwords;
    Connection connection;


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

    public boolean checkLogin()
    {
        return isLoggedIn;
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

}
