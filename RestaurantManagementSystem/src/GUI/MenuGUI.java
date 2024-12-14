package GUI;

import java.awt.EventQueue;
import Models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Helper.ConnectionHelper;
import Helper.MenuHelper;
import Helper.helper;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_menu;
	private JLabel lbl_user;
	private JLabel lblNewLabel;
	private JLabel lblFiyat;
	private JTextField fld_productName;
	private JTextField fld_price;
	private JButton btn_addProduct;
	MenuHelper menuHelper = new MenuHelper();
	DefaultTableModel tableModel = new DefaultTableModel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuGUI frame = new MenuGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuGUI(User user) {
		setTitle("Restoran Yönetim Sistemi |  © Uğur Çakır");

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableModel.addColumn("ID");
		tableModel.addColumn("Ürün Adı");
		tableModel.addColumn("Fiyat");

		table_menu = new JTable(tableModel);
		table_menu.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JScrollPane scrollPane = new JScrollPane(table_menu);
		scrollPane.setFont(new Font("Tahoma", Font.BOLD, 18));
		scrollPane.setBounds(10, 11, 403, 539);
		contentPane.add(scrollPane);

		lbl_user = new JLabel("Aktif Kullanıcı : " + user.getUserName());
		lbl_user.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_user.setBounds(423, 11, 314, 27);
		contentPane.add(lbl_user);

		lblNewLabel = new JLabel("Ürün Adı :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(423, 75, 90, 27);
		contentPane.add(lblNewLabel);

		lblFiyat = new JLabel("Fiyat :");
		lblFiyat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiyat.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFiyat.setBounds(423, 113, 90, 27);
		contentPane.add(lblFiyat);

		fld_productName = new JTextField();
		fld_productName.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_productName.setBounds(523, 75, 211, 27);
		contentPane.add(fld_productName);
		fld_productName.setColumns(10);

		fld_price = new JTextField();
		fld_price.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_price.setColumns(10);
		fld_price.setBounds(523, 113, 211, 27);
		contentPane.add(fld_price);

		btn_addProduct = new JButton("ÜRÜN EKLE");
		btn_addProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = fld_productName.getText();
				double price = Double.parseDouble(fld_price.getText());
				if (name.length() == 0 || fld_price.getText().length() == 0) {
					helper.showError("fill");
				} else {
					menuHelper.addProduct(name, price);
					fld_productName.setText("");
					fld_price.setText("");
					updateTableModel();
				}
			}
		});
		btn_addProduct.setBackground(Color.ORANGE);
		btn_addProduct.setForeground(Color.BLACK);
		btn_addProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_addProduct.setBounds(555, 151, 147, 27);
		contentPane.add(btn_addProduct);

		menuHelper.getProducts(tableModel);

		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem deleteItem = new JMenuItem("Sil");
		JMenuItem updateItem = new JMenuItem("Güncelle");

		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (helper.showConfirm("sure")) {
					int selectedRow = table_menu.getSelectedRow();
					if (selectedRow != -1) {
						int id = (Integer) table_menu.getValueAt(selectedRow, 0);
						menuHelper.deleteProduct(id);
						updateTableModel();
					}
				} else {

				}

			}
		});

		updateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_menu.getSelectedRow();
				if (selectedRow != -1) {

					int productId = (int) table_menu.getValueAt(selectedRow, 0);
					String currentName = (String) table_menu.getValueAt(selectedRow, 1);
					Double currentPrice = (Double) table_menu.getValueAt(selectedRow, 2);

					JPanel updatePanel = new JPanel();
					updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));

					JTextField nameField = new JTextField(currentName);
					JTextField priceField = new JTextField(String.valueOf(currentPrice));

					updatePanel.add(new JLabel("Yeni Ürün Adı:"));
					updatePanel.add(nameField);
					updatePanel.add(new JLabel("Yeni Fiyat:"));
					updatePanel.add(priceField);

					int option = JOptionPane.showConfirmDialog(null, updatePanel, "Ürün Güncelleme",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if (option == JOptionPane.OK_OPTION) {
						String newName = nameField.getText().trim();
						String priceString = priceField.getText().trim();

						if (newName.isEmpty() || priceString.isEmpty()) {
							helper.showError("Ürün adı ve fiyatı boş bırakılamaz.");
						} else {
							try {
								double newPrice = Double.parseDouble(priceString);
								menuHelper.updateProduct(productId, newName, newPrice);
								updateTableModel();
							} catch (NumberFormatException ex) {
								helper.showError("Fiyat geçerli bir sayı olmalıdır.");
							}
						}
					}
				}
			}
		});

		popupMenu.add(deleteItem);
		popupMenu.add(updateItem);

		table_menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row = table_menu.rowAtPoint(e.getPoint());
					int column = table_menu.columnAtPoint(e.getPoint());

					if (row >= 0 && column >= 0) {
						table_menu.setRowSelectionInterval(row, row);
						popupMenu.show(table_menu, e.getX(), e.getY());
					}
				}
			}
		});
	}

	public void updateTableModel() {
		DefaultTableModel clearModel = (DefaultTableModel) table_menu.getModel();
		clearModel.setRowCount(0);
		menuHelper.getProducts(tableModel);
	}

}
