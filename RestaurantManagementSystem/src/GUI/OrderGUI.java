package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import Helper.OrderHelper;
import Helper.helper;
import Models.Order;
import Models.Product;
import Models.TableData;
import Models.User;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class OrderGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableTables;

	OrderHelper orderHelper = new OrderHelper();
	DefaultTableModel tablesModel = new DefaultTableModel(null,
			new Object[] { "ID", "Masa No", "Toplam Tutar", "Sipariş No" });

	private JLabel lblNewLabel;
	private JTextField fld_tableNo;
	private JButton btn_addOrder;

	private int OrderNo = 100;
	private JTextField fld_unitPrice;
	private JTextField fld_totalPrice;

	private JComboBox combo_Menu; // JComboBox for menu selection
	private static final int orderNo = 1000;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderGUI frame = new OrderGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param user
	 */
	public OrderGUI(User user) {
		setTitle("Restoran Yönetim Sistemi |  © Uğur Çakır");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 794, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollTables = new JScrollPane();
		contentPane.add(scrollTables, BorderLayout.WEST);

		tableTables = new JTable(tablesModel);
		tableTables.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollTables.setViewportView(tableTables);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		tableTables.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				settleBillTableNo();
			}
		});

		lblNewLabel = new JLabel("SEÇİLEN MASA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(8, 42, 301, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblNewLabel);

		fld_tableNo = new JTextField();
		fld_tableNo.setHorizontalAlignment(SwingConstants.CENTER);
		fld_tableNo.setEditable(false);
		fld_tableNo.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_tableNo.setBounds(8, 70, 301, 20);
		panel.add(fld_tableNo);
		fld_tableNo.setColumns(10);

		btn_addOrder = new JButton("SİPARİŞ EKLE");

		btn_addOrder.setBackground(Color.ORANGE);
		btn_addOrder.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_addOrder.setBounds(8, 223, 301, 30);
		panel.add(btn_addOrder);

		JLabel lblAktifKulllanc = new JLabel("Aktif Kulllanıcı");
		lblAktifKulllanc.setHorizontalAlignment(SwingConstants.CENTER);
		lblAktifKulllanc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAktifKulllanc.setBounds(8, 11, 170, 20);
		panel.add(lblAktifKulllanc);

		JLabel lbl_user = new JLabel(user.getUserName());
		lbl_user.setForeground(Color.GREEN);
		lbl_user.setBackground(Color.BLACK);
		lbl_user.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_user.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_user.setBounds(187, 11, 119, 20);
		panel.add(lbl_user);

		combo_Menu = new JComboBox();
		combo_Menu.setFont(new Font("Tahoma", Font.BOLD, 14));
		combo_Menu.setBackground(Color.WHITE);
		combo_Menu.setBounds(8, 101, 170, 30);
		panel.add(combo_Menu);

		JSpinner spinner_quantity = new JSpinner();
		spinner_quantity.setModel(new SpinnerNumberModel(0, 0, 20, 1));
		spinner_quantity.setFont(new Font("Tahoma", Font.BOLD, 16));
		spinner_quantity.setBounds(201, 101, 47, 30);
		panel.add(spinner_quantity);
		spinner_quantity.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// Spinner'ın değeri değiştiğinde yapılacak işlem
				int value = (Integer) spinner_quantity.getValue();
				double price = Double.parseDouble(fld_unitPrice.getText());
				double totalPrice = value * price;
				fld_totalPrice.setText(String.valueOf(totalPrice));

			}
		});

		fld_unitPrice = new JTextField();
		fld_unitPrice.setText("180.00");
		fld_unitPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		fld_unitPrice.setBounds(201, 142, 108, 30);
		panel.add(fld_unitPrice);
		fld_unitPrice.setColumns(10);

		fld_totalPrice = new JTextField();
		fld_totalPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		fld_totalPrice.setColumns(10);
		fld_totalPrice.setBounds(201, 183, 108, 30);
		panel.add(fld_totalPrice);

		JLabel lblBirimFiyat = new JLabel("Birim Fiyat :");
		lblBirimFiyat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBirimFiyat.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBirimFiyat.setBounds(8, 142, 170, 30);
		panel.add(lblBirimFiyat);

		JLabel lblAdetFiyat = new JLabel("Toplam Fiyat :");
		lblAdetFiyat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdetFiyat.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAdetFiyat.setBounds(8, 183, 170, 29);
		panel.add(lblAdetFiyat);

		orderHelper.getTables(tablesModel);

		// Veritabanından ürünleri çekip JComboBox'a ekle
		loadProducts();

		btn_addOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableNo = fld_tableNo.getText().strip();
				int OrderNumber = orderNo + 1;

				if (tableNo.length() <= 0) {
					helper.showError("Masa Seçiniz.");
					return;
				} else {
					String productName = combo_Menu.getSelectedItem().toString().strip();
					int quantity = (int) spinner_quantity.getValue();
					double totalPrice = Double.parseDouble(fld_totalPrice.getText());

					if (productName.strip().length() <= 0 && quantity <= 0 && totalPrice <= 0) {
						helper.showError("Ürün veya adet seçimi yapmadığınız.");
					} else {
						if (orderHelper.getOrderNo(tableNo) > 0) {
							int ordNum = orderHelper.getOrderNo(tableNo);
							orderHelper.createOrder(ordNum, productName, quantity, tableNo, totalPrice);
							double totalPriceOrder = orderHelper.getOrderTotal(tableNo);
							orderHelper.updateTableTotal(tableNo, totalPriceOrder);
							updateTablesModel();
							helper.showMessage("success");

						} else {
							orderHelper.createOrder(OrderNumber, productName, quantity, tableNo, totalPrice);
							OrderNumber++;
							double totalPriceOrder = orderHelper.getOrderTotal(tableNo);
							orderHelper.updateTableTotal(tableNo, totalPriceOrder);
							updateTablesModel();
							helper.showMessage("success");
							helper.showMessage("success");

						}

					}

				}
			}
		});
	}

	public void loadProducts() {
		List<Product> productList = orderHelper.getProducts(); // DB'den ürünleri çek

		// Ürünleri JComboBox'a ekle
		DefaultComboBoxModel<Product> model = new DefaultComboBoxModel<>();
		for (Product product : productList) {
			model.addElement(product); // Her ürünü JComboBox modeline ekliyoruz
		}
		combo_Menu.setModel(model); // combo_Menu'yu model ile güncelliyoruz

		// ComboBox'tan bir ürün seçildiğinde fiyatı textField'a set et
		combo_Menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Product selectedProduct = (Product) combo_Menu.getSelectedItem(); // Seçilen ürünü al
				if (selectedProduct != null) {
					double price = selectedProduct.getPrice(); // Ürünün fiyatını al
					fld_unitPrice.setText(String.valueOf(price)); // Fiyatı textField'a set et
				}
			}
		});
	}

	public void updateTablesModel() {
		DefaultTableModel clearModel = (DefaultTableModel) tableTables.getModel();
		clearModel.setRowCount(0);
		orderHelper.getTables(tablesModel);
	}

	public int settleBillTableNo() {
		int selectedRow = tableTables.getSelectedRow();

		// Seçili satır kontrolü
		if (selectedRow != -1) {
			try {
				// Tablo modelinden 0. ve 1. sütun verilerini al
				Object tableIdObj = tableTables.getValueAt(selectedRow, 0);
				Object tableNumberObj = tableTables.getValueAt(selectedRow, 1);

				// Tür kontrolü
				int tableId = (tableIdObj instanceof Integer) ? (Integer) tableIdObj : 0;
				String tableNumber = (tableNumberObj != null) ? tableNumberObj.toString() : "";

				// Veriyi metin alanına yaz
				fld_tableNo.setText(tableNumber);

				return tableId;

			} catch (Exception ex) {
				ex.printStackTrace();
				Helper.helper.showMessage("Hata: Veri alınamadı.");
			}
		} else {
			Helper.helper.showMessage("Lütfen bir satır seçin.");
		}
		return selectedRow;
	}
}
