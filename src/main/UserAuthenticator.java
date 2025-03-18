package main;

import java.sql.*;

public class UserAuthenticator
{
    DatabaseCommunicator databaseCommunicator = DatabaseCommunicator.getInstance();

    private boolean isLoggedIn = false;

    String[] usernames;
    String[] passwords;
    Connection connection;

    private UserAuthenticator() {}

    private static class UserAuthenticatorHolder
    {
        private static UserAuthenticator INSTANCE = new UserAuthenticator();
    }

    public static UserAuthenticator getInstance()
    {
        return UserAuthenticatorHolder.INSTANCE;
    }

    public void getQuery()
    {

    }
}
