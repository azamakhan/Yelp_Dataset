/* Sample program for hw5 
 * First install the stored procedure in assign_pilot.sql
 */
import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

public class FindPilotsSP0 {
	private static Connection connection = null;

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		// Prompt the user for connect information
		String username = null;
		String password = null;
		String connStr = null;
		try {
				username = readEntry(in, "Oracle username: ");
				password = readEntry(in, "Oracle password: ");
				String host = readEntry(in, "host: ");
				String port = readEntry(in, "port (often 1521): ");
				String sid = readEntry(in, "sid (site id): ");
				connStr = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
		} catch (IOException e) {
			System.out.println("Problem with user input, please try again\n");
			System.exit(3);
		}
		System.out.println("using connection string: " + connStr);
		System.out.print("Connecting to the database...");

		try {
			connection = getConnected(connStr, username, password);
		} catch (SQLException except) {
			System.out.println("Problem with JDBC Connection");
			System.out.println(except.getMessage());
			System.exit(1);
		}
	
		try {
		int retVal = assignFlight(connection, 33, 1000);
		System.out.println("assignFlight returned "+ retVal); // should be 18 (#rows in flights)
		} catch (SQLException except) {
			System.out.println("SQLException:");
			System.out.println(except.getMessage());
			System.exit(1);
		}
		
	}
	
	// Call sample stored procedure in assign_pilot.sql and return its result
	private static int assignFlight(Connection connection, int flno, int distance) throws SQLException {
		CallableStatement cstmt = null;
		try {
			int returnValue = -1;
			// Prepare to call the stored procedure
			System.out.println("assignFlight for "+flno);
			cstmt = connection.prepareCall("{call assign_pilot (?, ?, ?)}");
			cstmt.setInt(1, flno); 												
			cstmt.setInt(2, distance);
			cstmt.setInt(3, returnValue);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.execute(); // call stored procedure
			returnValue = cstmt.getInt(3);
			return returnValue;
		} catch (SQLException e) {
			System.out.println("Problem with assignFlight ");
			throw (e); // let caller handle after above output
		} finally {
			closeCallableStatement(cstmt);
		}
	}

	public static void closeCallableStatement(CallableStatement cstmt) {
		try {
			if (cstmt != null) {
				cstmt.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
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
			System.out.println("SQLState: " + e.getSQLState());
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
