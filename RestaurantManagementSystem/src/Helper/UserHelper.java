package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class UserHelper {
	private static String url = ConnectionHelper.URL;
	private static String user = ConnectionHelper.USER;
	private static String password = ConnectionHelper.PASSWORD;
	private static final String sqlTable = "tbl_users";

	public void getUser(DefaultTableModel tableModel) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT id, userName, password, userType FROM " + sqlTable;
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String kullaniciAdi = resultSet.getString("userName");
				String sifre = resultSet.getString("password");
				String kullaniciTuru = resultSet.getString("userType");

				tableModel.addRow(new Object[] { id, kullaniciAdi, sifre, kullaniciTuru });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addUser(String userName, String pass, String userType) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "INSERT INTO " + sqlTable + "(userName, password, userType) VALUES (?,?,?)";

			statement = connection.prepareStatement(query);
			statement.setString(1, userName);
			statement.setString(2, pass);
			statement.setString(3, userType);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				helper.showMessage("success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteUser(int id) {
		Connection connection;
		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "DELETE FROM " + sqlTable + " WHERE id = ?";

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.close();

			helper.showMessage("success");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(int id, String newName, String newPassword, String newType) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "UPDATE " + sqlTable + " SET userName = ?, password = ?, userType=? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, newName);
			statement.setString(2, newPassword);
			statement.setString(3, newType);
			statement.setInt(4, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
