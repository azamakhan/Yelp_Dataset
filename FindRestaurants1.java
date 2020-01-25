import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

class FindRestaurants1 {
	public static void main(String args[]) {
		String dbSys = null;
		Scanner in = null;
		try {
			in = new Scanner(System.in);
			System.out.println("Please enter information to test connection to the database");
			dbSys = readEntry(in, "Using Oracle (o), MySql (m) or HSQLDB (h)? ");

		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(1);
		}
		// Prompt the user for connect information
		String user = null;
		String password = null;
		String connStr = null;
		String yesNo;
		try {
			if (dbSys.equals("o")) {
				user = readEntry(in, "user: ");
				password = readEntry(in, "password: ");
				yesNo = readEntry(in,
						"use canned Oracle connection string (y/n): ");
				if (yesNo.equals("y")) {
					String host = readEntry(in, "host: ");
					String port = readEntry(in, "port (often 1521): ");
					String sid = readEntry(in, "sid (site id): ");
					connStr = "jdbc:oracle:thin:@" + host + ":" + port + ":"
							+ sid;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
			} else if (dbSys.equals("m")) {// MySQL--
				user = readEntry(in, "user: ");
				password = readEntry(in, "password: ");
				yesNo = readEntry(in,
						"use canned MySql connection string (y/n): ");
				if (yesNo.equals("y")) {
					String host = readEntry(in, "host: ");
					String port = readEntry(in, "port (often 3306): ");
					String db = user + "db";
					connStr = "jdbc:mysql://" + host + ":" + port + "/" + db;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
			} else if (dbSys.equals("h")) { // HSQLDB (Hypersonic) db
				yesNo = readEntry(in,
						"use canned HSQLDB connection string (y/n): ");
				if (yesNo.equals("y")) {
					String db = readEntry(in, "db or <CR>: ");
					connStr = "jdbc:hsqldb:hsql://localhost/" + db;
				} else {
					connStr = readEntry(in, "connection string: ");
				}
				user = "sa";
				password = "";
			} else {
				user = readEntry(in, "user: ");
				password = readEntry(in, "password: ");
				connStr = readEntry(in, "connection string: ");
			}
		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(3);
		}
		System.out.println("using connection string: " + connStr);
		System.out.print("Connecting to the database...");
		System.out.flush();
		Connection conn = null;
		// Connect to the database
		// Use finally clause to close connection
		try {
			conn = DriverManager.getConnection(connStr, user, password);
			System.out.println("connected.");
			FindRes_query(conn);
		} catch (SQLException e) {
			System.out.println("Problem with JDBC Connection\n");
			printSQLException(e);
			System.exit(4);
		} finally {
			// Close the connection, if it was obtained, no matter what happens
			// above or within called methods
			if (conn != null) {
				try {
					conn.close(); // this also closes the Statement and
									// ResultSet, if any
				} catch (SQLException e) {
					System.out
							.println("Problem with closing JDBC Connection\n");
					printSQLException(e);
					System.exit(5);
				}
			}
		}
	}
	
	// try to create and use a "welcome" table, or throw if this fails
	static void FindRes_query(Connection conn) throws SQLException {
		
		// Create a statement
		CallableStatement cstmt = null;
		PreparedStatement p_stmt = null;
		
		
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
			String user_input = scanner.nextLine();	
			
			String myquery = "SELECT b.name, b.stars, b.review_count, geo_distance( a.Longitude, a.Latitude, b.Longitude, b.Latitude) as Distance  FROM yelp_db.business b, yelp_db.category c, (select * from yelp_db.apartments where listing= '" +user_input+ "')a WHERE b.id = c.business_id AND b.city = 'Las Vegas' AND b.state = 'NV' AND  c.category = 'Restaurants' AND 200 > geo_distance( a.Longitude, a.Latitude, b.Longitude, b.Latitude)  GROUP BY b.id, b.name, b.stars, b.review_count, geo_distance( a.Longitude, a.Latitude, b.Longitude,b.Latitude) HAVING b.review_count >=10";
			p_stmt = conn.prepareStatement(myquery);
			ResultSet rset = null;
			String userData = "";
			rset = p_stmt.executeQuery();
	
		while(rset.next()) {
		
		userData = " Name: " + rset.getString(1) + " \n Ratings: " + rset.getString(2) + " \n No. of Reviews: " + rset.getString(3) + "\n Distance: " + rset.getString(4) + "\n";
		System.out.println(userData);
		}
			
		} finally {   // Note: try without catch: let the caller handle
					  // any exceptions of the "normal" db actions. 
			p_stmt.close(); // clean up statement resources, incl. rset
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
	public static String readEntry(Scanner in, String prompt)
			throws IOException {
		System.out.print(prompt);
		return in.nextLine().trim();
	}
}