package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import Models.Order;
import Models.Product;
import Models.TableData;

public class OrderHelper {
	private static String url = ConnectionHelper.URL;
	private static String user = ConnectionHelper.USER;
	private static String password = ConnectionHelper.PASSWORD;

	public void getTables(DefaultTableModel tableModel) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT id, table_number, total_price,currentOrderNo FROM tbl_tables WHERE customer = 1";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String masaNo = resultSet.getString("table_number");
				double tutar = resultSet.getDouble("total_price");
				int sipNo = resultSet.getInt("currentOrderNo");

				tableModel.addRow(new Object[] { id, masaNo, tutar, sipNo });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getProducts(DefaultTableModel tableModel) {

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT id,name, price FROM tbl_menu"; //
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

	public void createOrder(int orderNo, String productName, int quantity, String tableNo, double totalPrice) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "INSERT INTO tbl_orderDetails (orderNo, product_name, quantity, table_no, product_total_price) VALUES (?,?,?,?,?)";

			statement = connection.prepareStatement(query);
			statement.setInt(1, orderNo);
			statement.setString(2, productName);
			statement.setInt(3, quantity);
			statement.setString(4, tableNo);
			statement.setDouble(5, totalPrice);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				updateTableOrderNo(tableNo, orderNo);
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

	public void updateTableOrderNo(String tableNo, int orderNo) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "UPDATE tbl_tables SET currentOrderNo = ? WHERE table_number = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, orderNo);
			statement.setString(2, tableNo);
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

	public double getTableTotal(String tableNo) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT total_price FROM tbl_tables WHERE table_number =?";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				double tutar = resultSet.getDouble("total_price");
				return tutar;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getOrderNo(String tableNo) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT currentOrderNo FROM tbl_tables WHERE table_number ='?'";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int orderNo = resultSet.getInt("currentOrderNo");
				return orderNo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public double getOrderTotal(String tableNo) {
		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT SUM(product_total_price) FROM tbl_orderdetails WHERE table_no='?';";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int orderNo = resultSet.getInt("SUM(product_total_price)");
				return orderNo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void updateTableTotal(String tableNo, double totalPrice) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			String query = "UPDATE tbl_tables SET total_price = ? WHERE table_number = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDouble(1, totalPrice);
			statement.setString(2, tableNo);
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

	public List<Product> getProducts() {
		List<Product> productList = new ArrayList<>();
		String query = "SELECT name, price FROM tbl_menu"; // Ürünleri çekmek için sorgu

		try (Connection connection = DriverManager.getConnection(url, user, password);
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");
				Product product = new Product(name, price);
				productList.add(product); // Ürünü listeye ekle
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList; // Ürün listesini döndür
	}

}
