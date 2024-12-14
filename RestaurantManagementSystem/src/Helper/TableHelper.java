package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import Helper.ConnectionHelper;

public class TableHelper {
	private static String url = ConnectionHelper.URL;
	private static String user = ConnectionHelper.USER;
	private static String password = ConnectionHelper.PASSWORD;
	private static final String sqlTable = "tbl_tables";

	public void getTables(DefaultTableModel tableModel) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT id, table_number, total_price, customer FROM " + sqlTable;
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String masaNo = resultSet.getString("table_number");
				double tutar = resultSet.getDouble("total_price");
				boolean musteri = resultSet.getInt("customer") == 1;

				// Tabloya veriyi ekle
				tableModel.addRow(new Object[] { id, masaNo, tutar, musteri });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTable(String table_number) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "INSERT INTO " + sqlTable + "(table_number) VALUES (?)";

			statement = connection.prepareStatement(query);
			statement.setString(1, table_number);
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
	
	

	public void updateCustomer(int id, int customer) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "UPDATE " + sqlTable + " SET customer = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, customer);
			statement.setInt(2, id);
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

	public void settleBill(int id, double totalPrice) {
	    Connection connection = null;
	    try {
	        connection = DriverManager.getConnection(url, user, password);
	        String query = "UPDATE " + sqlTable + " SET total_price = ? WHERE id = ?"; // Fix the column name here
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setDouble(1, totalPrice);
	        statement.setInt(2, id);
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


	public double getTotalPrice(int id) {
	    try (Connection connection = DriverManager.getConnection(url, user, password)) {
	        String query = "SELECT total_price FROM " + sqlTable + " WHERE id=?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, id); // Set the 'id' parameter here
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            double totalPrice = resultSet.getDouble("total_price");
	            return totalPrice;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}


	public String getTableNo(int id) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT table_number FROM " + sqlTable + " WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(query);

			// Set the parameter for the prepared statement
			statement.setInt(1, id); // '1' is the first parameter, and 'id' is the value to set

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// Retrieve the table number from the result set
				String tableNo = resultSet.getString("table_number");
				return tableNo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // Return an empty string if no result or an error occurs
	}

}
