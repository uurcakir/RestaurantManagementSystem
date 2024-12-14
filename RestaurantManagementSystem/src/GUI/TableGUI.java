package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.ConnectionHelper;
import Helper.TableHelper;
import Helper.helper;
import Models.User;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class TableGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableTables;

	TableHelper tableHelper = new TableHelper();

	DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "ID", "Masa No", "Tutar", "Müşteri" }, 0) {
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Integer.class; // ID
			case 1:
				return String.class; // Masa No
			case 2:
				return Double.class; // Tutar
			case 3:
				return Boolean.class; // Müşteri (Checkbox için)
			default:
				return Object.class;
			}
		}
	};

	private JTextField fld_tableNumber;
	private boolean isListening = true;
	private JTextField fld_billTableNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableGUI frame = new TableGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TableGUI(User user) {
		setTitle("Restoran Yönetim Sistemi |  © Uğur Çakır");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Tahoma", Font.BOLD, 18));
		scrollPane.setBounds(10, 11, 403, 539);
		contentPane.add(scrollPane);

		tableHelper.getTables(tableModel);

		tableTables = new JTable(tableModel);
		tableTables.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(tableTables);
		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// Eğer dinleme devre dışıysa geri dön
				if (!isListening)
					return;

				int row = e.getFirstRow();
				int column = e.getColumn();

				if (column == 3) { // Checkbox sütunu
					Boolean isChecked = (Boolean) tableModel.getValueAt(row, column);
					Integer tableID = (Integer) tableModel.getValueAt(row, 0);
					String tableNo = (String) tableModel.getValueAt(row, 1);
					Double tutar = (Double) tableModel.getValueAt(row, 2);

					if (isChecked == false) {
						if (tutar > 0) {
							isListening = false; // Dinlemeyi geçici olarak kapat
							tableModel.setValueAt(true, row, column); // Değeri eski haline getir
							isListening = true; // Dinlemeyi tekrar aç
							Helper.helper.showMessage("Servisi kapatmadan önce hesap almanız gerekiyor.");
						} else {
							isListening = false;
							tableHelper.updateCustomer(tableID, 0);
							isListening = true;
							Helper.helper.showMessage(tableNo + " numaralı masanın servisi kapatıldı.");
						}

					} else {
						isListening = false;
						tableHelper.updateCustomer(tableID, 1);
						isListening = true;
						Helper.helper.showMessage(tableNo + " numaralı masaya servis açıldı.");

					}
				}
			}
		});

		tableTables.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				settleBillTableNo(); // Call the method to update the field when a row is selected
			}
		});

		JLabel lblNewLabel = new JLabel("Masa No:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(457, 57, 82, 29);
		contentPane.add(lblNewLabel);

		fld_tableNumber = new JTextField();
		fld_tableNumber.setEnabled(false);
		fld_tableNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_tableNumber.setBounds(549, 57, 101, 29);
		contentPane.add(fld_tableNumber);
		fld_tableNumber.setColumns(10);

		JButton btn_addTable = new JButton("Masa Ekle");
		btn_addTable.setEnabled(false);
		btn_addTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableNumber = fld_tableNumber.getText().strip();
				if (tableNumber.length() == 0) {
					Helper.helper.showError("fill");
				} else {
					tableHelper.addTable(tableNumber);
					fld_tableNumber.setText("");
					updateTableModel();

				}

			}
		});

		if (user.getUserType().equals("admin")) {
			fld_tableNumber.setEnabled(true);
			btn_addTable.setEnabled(true);
		}

		btn_addTable.setBackground(Color.ORANGE);
		btn_addTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_addTable.setBounds(457, 97, 193, 29);
		contentPane.add(btn_addTable);

		JLabel lbl_user = new JLabel("Aktif Kullanıcı : " + user.getUserName());
		lbl_user.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_user.setBounds(423, 19, 251, 27);
		contentPane.add(lbl_user);

		JLabel lblNewLabel_1 = new JLabel("Masa No:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(457, 162, 82, 29);
		contentPane.add(lblNewLabel_1);

		fld_billTableNo = new JTextField();
		fld_billTableNo.setEditable(false);
		fld_billTableNo.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_billTableNo.setColumns(10);
		fld_billTableNo.setBounds(549, 162, 101, 29);
		contentPane.add(fld_billTableNo);

		JButton btn_settleBill = new JButton("Hesabı Kapat");
		btn_settleBill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = settleBillTableNo();
				String tableId = tableHelper.getTableNo(id);
				int totalPrice = (int) tableHelper.getTotalPrice(id);
				if (id > 0) {
					if (totalPrice <= 0) {
						helper.showError("HATA : Hesap 0.00 TL olamaz.");
					} else {
						isListening = false;
						tableHelper.settleBill(id, 0);
						tableHelper.updateCustomer(id, 0);
						isListening = true;
						helper.showMessage(tableId + " numaralı masanın hesabı oluşturuldu. " + totalPrice + " TL.");
						updateTableModel();
					}

				} else {
					helper.showError("HATA : Hesap oluşturmak istediğiniz masayı seçin.");
				}

			}
		});
		btn_settleBill.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_settleBill.setBackground(Color.GREEN);
		btn_settleBill.setBounds(457, 202, 193, 29);
		contentPane.add(btn_settleBill);

	}

	public void updateTableModel() {
		DefaultTableModel clearModel = (DefaultTableModel) tableTables.getModel();
		clearModel.setRowCount(0);
		tableHelper.getTables(tableModel);
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
				int tableId = (tableIdObj instanceof Integer) ? (Integer) tableIdObj : -1;
				String tableNumber = (tableNumberObj != null) ? tableNumberObj.toString() : "";

				// Veriyi metin alanına yaz
				fld_billTableNo.setText(tableNumber);

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
