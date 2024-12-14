package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Helper.ConnectionHelper;
import Helper.helper;

import Models.User;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_userName;
	private JPasswordField fld_pass;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginGUI() {
		setTitle("Restoran Yönetim Sistemi |  © Uğur Çakır");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("KULLANICI ADI :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 96, 118, 35);
		contentPane.add(lblNewLabel);

		JLabel lblifre = new JLabel("ŞİFRE :");
		lblifre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblifre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblifre.setBounds(10, 142, 118, 35);
		contentPane.add(lblifre);

		fld_userName = new JTextField();
		fld_userName.setFont(new Font("Tahoma", Font.BOLD, 12));
		fld_userName.setBounds(138, 96, 206, 35);
		contentPane.add(fld_userName);
		fld_userName.setColumns(10);

		fld_pass = new JPasswordField();
		fld_pass.setFont(new Font("Tahoma", Font.BOLD, 12));
		fld_pass.setBounds(138, 143, 206, 35);
		contentPane.add(fld_pass);

		JButton btn_login = new JButton("GİRİŞ YAP");
		btn_login.setBackground(Color.LIGHT_GRAY);
		btn_login.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_login.setBounds(108, 203, 167, 47);
		contentPane.add(btn_login);

		btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});

		ImageIcon img = new ImageIcon(getClass().getResource("/img/rmsLogo.png"));
		JLabel lblLogo = new JLabel(img);
		lblLogo.setBounds(147, 11, 90, 80);
		contentPane.add(lblLogo);
	}

	private void handleLogin() {
		String username = fld_userName.getText();
		String password = new String(fld_pass.getPassword());

		ConnectionHelper conHelper = new ConnectionHelper();

		try {
			String userType = conHelper.validateUser(username, password);
			if (userType.equals("admin") || userType.equals("user")) {
				User user = new User(username, password, userType);
				MainMenuGUI mainMenuGUI = new MainMenuGUI(user);
				mainMenuGUI.setVisible(true);
				this.dispose();
			} else if (userType.equals("wrong")) {

				helper.showError("wrong");

			}
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "Beklenmeyen bir hata oluştu: " + e.getMessage(), "HATA",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
