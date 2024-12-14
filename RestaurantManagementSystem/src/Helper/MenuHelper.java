package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class MenuHelper {
	private static String url = ConnectionHelper.URL;
	private static String user = ConnectionHelper.USER;
	private static String password = ConnectionHelper.PASSWORD;
	private static final String sqlTable = "tbl_menu";

	public void getProducts(DefaultTableModel tableModel) {

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT id,name, price FROM " + sqlTable; //
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String urunAdi = resultSet.getString("name");
				double fiyat = resultSet.getDouble("price");
				tableModel.addRow(new Object[] { id, urunAdi, fiyat });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addProduct(String name, Double price) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "INSERT INTO " + sqlTable + "(name, price) VALUES (?, ?)";

			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setDouble(2, price);
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

	public void deleteProduct(int id) {
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

	public void updateProduct(int productId, String newName, double newPrice) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "UPDATE " + sqlTable + " SET name = ?, price = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, newName);
			statement.setDouble(2, newPrice);
			statement.setInt(3, productId);
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
