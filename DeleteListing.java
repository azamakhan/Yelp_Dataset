import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteListing {
	public static void main(String[] args) {

		String dbSys = null;
		Scanner in = null;
		try {
			in = new Scanner(System.in);
			System.out.println("Please enter information for connection to the database");
			dbSys = readEntry(in, "Using Oracle (o) or MySql (m)? ");

		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(1);
		}
		// Prompt the user for connect information
		String username = null;
		String password = null;
		String connStr = null;
		String yesNo;
		try {
			if (dbSys.equals("o")) {
				username = readEntry(in, "Oracle username: ");
				password = readEntry(in, "Oracle password: ");
				yesNo = readEntry(in, "use canned Oracle connection string (y/n): ");
				if (yesNo.equals("y")) {
					String host = readEntry(in, "host: ");
					String port = readEntry(in, "port (often 1521): ");
					String sid = readEntry(in, "sid (site id): ");
					connStr = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
			} else if (dbSys.equals("m")) {// MySQL--
				username = readEntry(in, "MySQL username: ");
				password = readEntry(in, "MySQL password: ");
				yesNo = readEntry(in, "use canned MySql connection string (y/n): ");
				if (yesNo.equals("y")) {
					String host = readEntry(in, "host: ");
					String port = readEntry(in, "port (often 3306): ");
					String db = username + "db";
					connStr = "jdbc:mysql://" + host + ":" + port + "/" + db;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
			}
		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(3);
		}
		System.out.println("using connection string: " + connStr);
		System.out.print("Connecting to the database...");
		Connection conn = null;
		try {
			conn = getConnected(connStr, username, password);
			UserDB.setConnection(conn);  // let UserDB know connection
			delete_listing(in, conn);
		} catch (SQLException e) {
			System.out.println("Problem with JDBC Connection\n");
			printSQLException(e);
			System.exit(4);
		} finally {
			closeConnection(conn);
		}
	}

	// Do main part of application
	static void delete_listing(Scanner in, Connection conn) throws SQLException {
		PreparedStatement p_stmt = null;
		PreparedStatement p_stmt2 = null;
		
		
		try {

			// We treat this drop table specially to allow it to fail
			// as it will the very first time we run this program
		/*	try {
				p_stmt.execute("drop table welcome");
			} catch (SQLException e) {
				// assume not there yet, so OK to continue
			} */
			// The following database actions are handled normally,
			// i.e., if they fail they will throw a SQLException 
			// and terminate the execution of this method
			// with execution of the finally clause
		/*	Scanner scanner = new Scanner(System.in);
				System.out.println("Enter origin city: ");
					String user_input = scanner.nextLine(); */
					Scanner scanner = new Scanner(System.in);
					System.out.println("Enter Listing No.: ");
					int user_input = scanner.nextInt();
					String myquery = "INSERT into rented_apartments SELECT * FROM apartments WHERE listing = '" +user_input+ "'";
					p_stmt = conn.prepareStatement(myquery);
					p_stmt.executeUpdate();
					
					String myquery2 = "DELETE from apartments WHERE listing = '" +user_input+ "'";
					p_stmt2 = conn.prepareStatement(myquery2);
					p_stmt2.executeUpdate();

					
			
			/*	while(rset.next()) {
				
				userData = " Name: " + rset.getString(1) + " \n Ratings: " + rset.getString(2) + " \n No. of Reviews: " + rset.getString(3) + " \n Distance: " + rset.getString(4);
				System.out.println(userData); 
				} */
		} catch (SQLException e) {
			System.out.println("Problem with listing ");
			throw (e);
		}	// let caller handle after above output
				finally {   
			p_stmt.close(); // clean up statement resources, incl. rset
		}
	}

	public static Connection getConnected(String connStr, String user, String password) throws SQLException {

		System.out.println("using connection string: " + connStr);
		System.out.print("Connecting to the database...");
		System.out.flush();

		// Connect to the database
		Connection conn = DriverManager.getConnection(connStr, user, password);
		System.out.println("connected.");
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close(); // this also closes the Statement and
								// ResultSet, if any
			} catch (SQLException e) {
				System.out.println("Problem with closing JDBC Connection\n");
				printSQLException(e);
			}
		}
	}

	// print out all exceptions connected to e by nextException or getCause
	static void printSQLException(SQLException e) {
		// SQLExceptions can be delivered in lists (e.getNextException)
		// Each such exception can have a cause (e.getCause, from Throwable)
		while (e != null) {
			System.out.println("SQLException Message:" + e.getMessage());
			Throwable t = e.getCause();
			while (t != null) {
				System.out.println("SQLException Cause:" + t);
				t = t.getCause();
			}
			e = e.getNextException();
		}
	}

	// super-simple prompted input from user
	public static String readEntry(Scanner in, String prompt) throws IOException {
		System.out.print(prompt);
		return in.nextLine().trim();
	}
}
