import java.sql.*;

public class UserDB {
	private static Connection connection;

	public static void setConnection(Connection conn) {
		connection = conn;
	}

	public static int insert(User user) throws SQLException {
		PreparedStatement ps = null;

		String query = "INSERT INTO Userdb (Email, FirstName, LastName) " + "VALUES (?, ?, ?)";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			return ps.executeUpdate();
		} finally {
			closePreparedStatement(ps);
		}
	}

	public static int delete(User user) throws SQLException {

		PreparedStatement ps = null;
		String query = "DELETE FROM Userdb " + "WHERE Email = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getEmail());

			return ps.executeUpdate();
		} finally {
			closePreparedStatement(ps);
		}
	}

	public static boolean emailExists(String email) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT Email FROM Userdb " + "WHERE Email = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			return rs.next();
		} finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}
	}

	public static void selectUser(String email) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userFirstName = null;
		
		String query = "SELECT * FROM userdb " + "WHERE Email = ?";
	/*	User user = null; */
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
			userFirstName = rs.getString("firstname");	
			System.out.println("First Name: " +userFirstName);			
		/*	System.out.println(rs.getString("lastname")); */
			}
			
		} finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}

	}
	
	public static void selectUser2(String email) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userLastName = null;
		
		String query = "SELECT * FROM userdb " + "WHERE Email = ?";
	/*	User user = null; */
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
			userLastName = rs.getString("lastname");
			System.out.println("Last Name: " +userLastName);
			
		/*	System.out.println(rs.getString("lastname")); */
			}
			
		} finally {
			closeResultSet(rs);
			closePreparedStatement(ps);
		}

	}
		
	public static void updateUser_firstname(String email, String new_firstname) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "Update userdb set firstname = ? where email = ?";
		/*	User user = null; */
			try {
				ps = connection.prepareStatement(query);
				ps.setString(1, new_firstname);
				ps.setString(2, email);
				ps.executeUpdate();
			System.out.println("First Name Updated!");	

			} catch (Exception e) {
			      e.printStackTrace();
			      System.exit(1);
			}
			finally {
				closeResultSet(rs);
				closePreparedStatement(ps);
			}
	}
	
	public static void updateUser_lastname(String email, String new_lastname) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "Update userdb set lastname = ? where email = ?";
		/*	User user = null; */
			try {
				ps = connection.prepareStatement(query);
				ps.setString(1, new_lastname);
				ps.setString(2, email);
				ps.executeUpdate();
			System.out.println("Last Name Updated!");	

			} catch (Exception e) {
			      e.printStackTrace();
			      System.exit(1);
			}
			finally {
				closeResultSet(rs);
				closePreparedStatement(ps);
			}
		
		
	}

	public static void closePreparedStatement(Statement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	
}
