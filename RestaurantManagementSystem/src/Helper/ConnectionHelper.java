package Helper;

import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class ConnectionHelper {
	static final String URL = "jdbc:mysql://127.0.0.1:3306/rms_db"; //
	static final String USER = "root";
	static final String PASSWORD = "admin";
	private static final String sqlTable = "tbl_users";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public String validateUser(String username, String password) {
		String query = "SELECT userType FROM " + sqlTable + " WHERE username = ? AND password = ?";

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("userType");
			} else {

				return "wrong";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
