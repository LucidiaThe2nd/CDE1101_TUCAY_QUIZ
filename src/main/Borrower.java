package main;

import java.sql.*;

public class Borrower
{
	private int borrowerNumber;
	private String firstName, lastName, address;
	
	public Borrower(int tBorrowID, String tFirstName, String tLastName, String tAddress)
	{
		this.borrowerNumber = tBorrowID;
		this.firstName = tFirstName;
		this.lastName = tLastName;
		this.address = tAddress;
	}
	
	public Borrower()
	{
		this.borrowerNumber = 0;
		this.firstName = "";
		this.lastName = "";
		this.address = "";
	}
	
	public void setBorrowerNumber(int b)
	{
		this.borrowerNumber = b;
	}
	
	public int getBorrowerNumber()
	{
		return this.borrowerNumber;
	} 
	
	public void setFirstName(String tFirstName)
	{
		this.firstName = tFirstName;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	} 
	
	public void setLastName(String tLastName)
	{
		this.lastName = tLastName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	} 
	
	public void setAddress(String tAddress)
	{
		this.address = tAddress;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public void fetchAndDisplayBorrowers()
	{
		Connection connection = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cde1101_daaf_tucay", "root", "1234");
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM borrower");
			while (resultSet.next())
			{
				Borrower borrower = new Borrower(
					resultSet.getInt("borrower_id"),
					resultSet.getString("first_name"),
					resultSet.getString("last_name"),
					resultSet.getString("address")
					);

				// Display student information

				System.out.println("Student Number: " + borrower.getBorrowerNumber()
					+ "\tFirst Name: " + borrower.getFirstName()
					+ "\tLast Name: " + borrower.getLastName()
					+ "\tAddress: " + borrower.getAddress());
			}

				

				resultSet.close();

				statement.close();

				connection.close();

		} 
		catch (Exception e)
		{

			System.out.println(e);

		}

	}
}
