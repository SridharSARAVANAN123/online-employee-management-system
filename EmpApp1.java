package EmployeeManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

class Management {

	int id;
	String name;
	float salary;
	long contact_no;
	String email_id;

	public Management(int id, String name, float salary, long contact_no, String email_id) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.contact_no = contact_no;
		this.email_id = email_id;
	}

	public String toString() {
		return "\nManagement Details :" + "\nID: " + this.id + "\nName: " + this.name + "\nSalary: " + this.salary
				+ "\nContact No: " + this.contact_no + "\nEmail-id: " + this.email_id;
	}
}

public class EmpApp1 {
	private static final String INSERT_Employee_SQL = "INSERT INTO Employee" + "  (id,name,salary,email_id) VALUES "
			+ " (?, ?, ?,?);";
	private static final String DELETE_Employee_SQL = "delete from Employee where id = ?;";
	private static final String UPDATE_Employee_SQL = "update employee set salary = ? where name = ?;";
	private static final String UPDATE_Employee_SQL1 = "update employee set name = ? where id = ?;";
	private static final String QUERY = "select id, name, salary, email_id from employee where id=?";
	private static final String QUERY2 = "select id, name, salary, email_id from employee ";

	public static void main(String[] args) {
		EmpApp1 empapp = new EmpApp1();
		empapp.method();
	}

	static Scanner sc = new Scanner(System.in);
	static ArrayList<Management> all = new ArrayList<Management>();

	static void display(ArrayList<Management> all) {
		System.out.println("\n******************************Employee List*****************************************\n");
		System.out
				.println(String.format("%-10s%-15s%-10s%-20s%-10s", "ID", "Name", "salary", "contact-no", "Email-Id"));
		for (Management e : all) {
			System.out.println(
					String.format("%-10s%-15s%-10s%-20s%-10s", e.id, e.name, e.salary, e.contact_no, e.email_id));
		}
	}

	int id;
	String name;
	float salary;
	long contact_no;
	String email_id;

	void method() {

		File f = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {

			f = new File("N:/Java Work Space/Eclipse Programs/Employee Management Tool/src/ManagementDataList1.txt");
			if (f.exists()) {
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);
				all = (ArrayList<Management>) ois.readObject();
			}

		} catch (Exception exp) {
			System.out.println(exp);
		}

		do {		
			System.out.println("\n				*********Welcome to the Employee Management System**********			\n");
			System.out.println("");
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("In this Application we can see our company Employees Details");
			System.out.println("");
			System.out.println("Enter 1 for Adding Details of the Employee ");
			System.out.println("");
			System.out.println("Enter 2 for Searching Employee from the Database ");
			System.out.println("");
			System.out.println("Enter 3 for Edit Employee Details ");
			System.out.println("");
			System.out.println("Enter 4 for Delete the Records of the Employee");
			System.out.println("");
			System.out.println("Enter 5 for Viewing all the Employees data from the Database");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("");
			System.out.println("This is an Application of working an ");
			System.out.println(
					"1). Add Employee to the DataBase\n" + "2). Search for Employee\n" + "3). Edit Employee details\n"
							+ "4). Delete Employee Details\n" + "5). Display all Employee working in this company\n");
			System.out.println("Enter your choice : ");
			int ch = sc.nextInt();

			switch (ch) {
			case 1:
				System.out.println("\nEnter the following details to ADD list:\n");
				System.out.println("Enter ID :");
				id = sc.nextInt();
				try (Connection connectionss = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?", "root",
						"Infotel");

						// Step 2:Create a statement using connection object
						PreparedStatement preparedStatementss = connectionss.prepareStatement(QUERY)) {

					preparedStatementss.setInt(1, id);

					ResultSet rs = preparedStatementss.executeQuery();
					if (rs.next()) {
						int Empid = rs.getInt("id");
						String name = rs.getString("name");
						float salary = rs.getFloat("salary");
						String email = rs.getString("email_id");
						
						if (id == Empid) {

							System.out.println("Employee Id is already taken amd Please choose another Id ");
				

						}
					}
						else {
							System.out.println("Enter Name :");
							name = sc.next();
							System.out.println("Enter Salary :");
							salary = sc.nextFloat();
							System.out.println("Enter Contact No :");
							contact_no = sc.nextLong();
							System.out.println("Enter Email-ID :");
							email_id = sc.next();
							all.add(new Management(id, name, salary, contact_no, email_id));
							display(all);

							try (Connection connectionsss = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?", "root",
									"Infotel");

									// Step 2:Create a statement using connection object
									PreparedStatement preparedStatementsss = connectionss.prepareStatement(INSERT_Employee_SQL)) {

								preparedStatementsss.setInt(1, id);
								preparedStatementsss.setString(2, name);
								preparedStatementsss.setFloat(3, salary);
								preparedStatementsss.setString(4, email_id);
								preparedStatementsss.executeUpdate();

							} catch (SQLException e) {

								// print SQL exception information
								printtheSQLException(e);
							} finally {

							}
						}

					}

				 catch (SQLException e) {

					// print SQL exception information
					printtheSQLException(e);
				} finally {

				}
				
				break;

			case 2:
				System.out.println("Enter the Employee ID to search :");
				id = sc.nextInt();
				try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?", "root",
						"Infotel"); // Step 2:Create a statement using connection object
						PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
					preparedStatement.setInt(1, id);
					System.out.println(preparedStatement); // Step 3: Execute the query or update query
					ResultSet rs = preparedStatement.executeQuery(); // Step 4: Process the ResultSet object.

					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						float salary = rs.getFloat("salary");
						String email = rs.getString("email_id");
						System.out.println("id :" + id + "," + " Name :" + name + "," + " salary:" + salary + ","
								+ " email_id :" + email + ",");
					}
				} catch (SQLException e) {
					printtheSQLException(e);
				}
				int i = 0;
				for (Management e : all) {
					if (id == e.id) {
						System.out.println(e + "\n");
						i++;
					}
				}
				if (i == 0) {
					System.out.println("\nEmployee Details are not available, Please enter a valid ID!!");
				}
				break;

			case 3:

				int ch1 = 0;
				System.out.println("\nEDIT Employee Details :\n" + "1). Employee salary\n");
				System.out.println("Enter 1 for Update Salary : ");
				System.out.println("Enter 2 for update Name : ");
				ch1 = sc.nextInt();
				switch (ch1) {

				case 1:
					System.out.println("Enter the Employee Name:");
					name = sc.next();
					System.out.println("Enter the New Salary : ");
					salary = sc.nextFloat();

					try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?",
							"root", "Infotel");
							PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_Employee_SQL)) {
						preparedStatement.setFloat(1, salary);
						preparedStatement.setString(2, name); // Step 3: Execute the query or update

						preparedStatement.executeUpdate();
					} catch (SQLException e1)// print SQL exception information
					{
						printtheSQLException(e1);
					}
					break;

				case 2:
					System.out.println("Enter the  Employee Id :");
					id = sc.nextInt();
					System.out.println("Enter the Correct Name :");
					name = sc.next();

					try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?",
							"root", "Infotel");
							PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_Employee_SQL1)) {
						preparedStatement.setString(1, name); // Step 3: Execute the query or update
						preparedStatement.setInt(2, id);
						preparedStatement.executeUpdate();
					} catch (SQLException e1)// print SQL exception information
					{
						printtheSQLException(e1);
					}
					break;

				default:
					System.out.println("\nEnter a correct choice from the List :");
					break;

				}

				break;

			case 4: {
				System.out.println("\nEnter Employee ID to DELETE from the Databse :");
				id = sc.nextInt();

				try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?", "root",
						"Infotel"); PreparedStatement statement = connection.prepareStatement(DELETE_Employee_SQL);) {
					statement.setInt(1, id);
					statement.executeUpdate();

				} catch (SQLException e) {
					printtheSQLException(e);
				}
			}
				break;

			case 5:
				try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?", "root",
						"Infotel"); // Step 2:Create a statement using connection object
						PreparedStatement preparedStatement = connection.prepareStatement(QUERY2);) {
					ResultSet rs = preparedStatement.executeQuery(); // Step 4: Process the ResultSet object.

					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						float salary = rs.getFloat("salary");
						String email = rs.getString("email_id");
						System.out.println("id :" + id + "," + " Name :" + name + "," + " salary:" + salary + ","
								+ " email_id :" + email + ",");
					}
				} catch (SQLException e) {
					printtheSQLException(e);
				}
				try {
					all = (ArrayList<Management>) ois.readObject();

				} catch (ClassNotFoundException e2) {

					System.out.println(e2);
				} catch (Exception e2) {

					System.out.println(e2);
				}
				display(all);
				break;

			default:
				System.out.println("\nEnter a correct choice from the List :");
				break;

			}

		} while (true);
	}

	public static void printtheSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				System.out.println("Entered Name Is Not In Our List");
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
