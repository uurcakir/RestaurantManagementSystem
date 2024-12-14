package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.UserHelper;
import Helper.helper;
import Models.User;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;

public class UserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_user;

	/**
	 * Launch the application.
	 */

	UserHelper userHelper = new UserHelper();
	DefaultTableModel tableUserModel = new DefaultTableModel(null,
			new Object[] { "ID", "Kullanıcı Adı", "Şifre", "Kullanıcı Türü" });
	private JLabel lblNewLabel;
	private JTextField fld_uName;
	private JLabel lblNewLabel_1;
	private JTextField fld_pass;
	private JLabel lblNewLabel_2;
	private JButton btn_addUser;
	private JLabel lblNewLabel_3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGUI frame = new UserGUI(null);
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
	public UserGUI(User user) {
		setTitle("Restoran Yönetim Sistemi |  © Uğur Çakır");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		userHelper.getUser(tableUserModel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 440, 499);
		contentPane.add(scrollPane);

		table_user = new JTable(tableUserModel);
		table_user.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table_user);

		lblNewLabel = new JLabel("Kullanıcı Adı");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(460, 98, 194, 33);
		contentPane.add(lblNewLabel);

		fld_uName = new JTextField();
		fld_uName.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_uName.setBounds(460, 142, 194, 33);
		contentPane.add(fld_uName);
		fld_uName.setColumns(10);

		lblNewLabel_1 = new JLabel("Şifre");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(460, 186, 194, 33);
		contentPane.add(lblNewLabel_1);

		fld_pass = new JTextField();
		fld_pass.setFont(new Font("Tahoma", Font.BOLD, 16));
		fld_pass.setColumns(10);
		fld_pass.setBounds(460, 230, 194, 33);
		contentPane.add(fld_pass);

		lblNewLabel_2 = new JLabel("Kullanıcı Türü");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(460, 274, 194, 33);
		contentPane.add(lblNewLabel_2);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboBox.setBounds(460, 318, 194, 33);
		contentPane.add(comboBox);
		comboBox.addItem("Kullanıcı");
		comboBox.addItem("Yönetici");

		btn_addUser = new JButton("Kullanıcı Ekle");
		btn_addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String getUserName = fld_uName.getText().strip();
				String getPassword = fld_pass.getText().strip();
				String getType = comboBox.getSelectedItem().toString().strip();

				if (getUserName.length() != 0 && getPassword.length() != 0 && getType.length() != 0) {
					if (getType.equals("Kullanıcı")) {
						userHelper.addUser(getUserName, getPassword, "user");

					} else if (getType.equals("Yönetici")) {
						userHelper.addUser(getUserName, getPassword, "admin");

					}
					updateTableModel();
					fld_uName.setText("");
					fld_pass.setText("");
					comboBox.setSelectedIndex(0);

				} else {
					helper.showError("fill");
				}

			}
		});
		btn_addUser.setBackground(Color.ORANGE);
		btn_addUser.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_addUser.setBounds(460, 362, 194, 33);
		contentPane.add(btn_addUser);
		
		lblNewLabel_3 = new JLabel("Aktif Kullanıcı : " + user.getUserName());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(460, 18, 194, 33);
		contentPane.add(lblNewLabel_3);

		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem deleteItem = new JMenuItem("Sil");
		JMenuItem updateItem = new JMenuItem("Güncelle");

		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (helper.showConfirm("sure")) {
					int selectedRow = table_user.getSelectedRow();
					if (selectedRow != -1) {
						int id = (Integer) table_user.getValueAt(selectedRow, 0);
						userHelper.deleteUser(id);
						updateTableModel();
					}
				} else {

				}

			}
		});

		updateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_user.getSelectedRow();
				if (selectedRow != -1) {

					int id = (int) table_user.getValueAt(selectedRow, 0);
					String currentName = (String) table_user.getValueAt(selectedRow, 1);
					String currentPassword = (String) table_user.getValueAt(selectedRow, 2);
					String currentType = (String) table_user.getValueAt(selectedRow, 3);

					JPanel updatePanel = new JPanel();
					updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));

					JTextField nameField = new JTextField(currentName);
					JTextField passwordField = new JTextField(String.valueOf(currentPassword));
					JComboBox<String> comboType = new JComboBox<>(new String[] { "Kullanıcı", "Yönetici" });

					comboType.setSelectedItem(currentType);

					updatePanel.add(new JLabel("Yeni Kullanıcı Adı:"));
					updatePanel.add(nameField);
					updatePanel.add(new JLabel("Yeni Şifre:"));
					updatePanel.add(passwordField);
					updatePanel.add(new JLabel("Kullanıcı Tipi:"));
					updatePanel.add(comboType);

					int option = JOptionPane.showConfirmDialog(null, updatePanel, "Kullanıcı Güncelleme",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if (option == JOptionPane.OK_OPTION) {
						String newName = nameField.getText().trim();
						String newPassword = passwordField.getText().trim();
						String newType = (String) comboType.getSelectedItem();

						if (newName.isEmpty() || newPassword.isEmpty()) {
							helper.showError("Kullanıcı adı ve şifresi boş bırakılamaz.");
						} else {
							try {
								if (newType.equals("Kullanıcı")) {
									userHelper.updateUser(id, newName, newPassword, "user");

								} else if (newType.equals("Yönetici")) {
									userHelper.updateUser(id, newName, newPassword, "admin");

								}

								updateTableModel();
							} catch (NumberFormatException ex) {
								helper.showError("Şifre geçerli bir sayı olmalıdır.");
							}
						}
					}
				}
			}
		});

		popupMenu.add(deleteItem);
		popupMenu.add(updateItem);

		table_user.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row = table_user.rowAtPoint(e.getPoint());
					int column = table_user.columnAtPoint(e.getPoint());

					if (row >= 0 && column >= 0) {
						table_user.setRowSelectionInterval(row, row);
						popupMenu.show(table_user, e.getX(), e.getY());
					}
				}
			}
		});
	}

	public void updateTableModel() {
		DefaultTableModel clearModel = (DefaultTableModel) table_user.getModel();
		clearModel.setRowCount(0);
		userHelper.getUser(tableUserModel);
	}
}
