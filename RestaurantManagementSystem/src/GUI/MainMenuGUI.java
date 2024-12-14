package GUI;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Helper.helper;
import Models.User;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;

public class MainMenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Image backgroundImage;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuGUI frame = new MainMenuGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenuGUI(User user) {
		setTitle("Restoran Yönetim Sistemi |  © Uğur Çakır");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);

		backgroundImage = new ImageIcon(getClass().getResource("/img/MainMenu.png")).getImage();

		contentPane = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btn_menu = new JButton("MENÜ");
		btn_menu.setEnabled(false);
		btn_menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuGUI menuGUI = new MenuGUI(user);
				menuGUI.setVisible(true);
			}
		});
		btn_menu.setForeground(Color.BLACK);
		btn_menu.setBackground(Color.LIGHT_GRAY);
		btn_menu.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_menu.setBounds(115, 305, 174, 50);
		contentPane.add(btn_menu);

		JButton btn_tables = new JButton("MASALAR");
		btn_tables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableGUI tableGUI = new TableGUI(user);
				tableGUI.setVisible(true);
			}
		});
		btn_tables.setForeground(Color.BLACK);
		btn_tables.setBackground(Color.LIGHT_GRAY);
		btn_tables.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_tables.setBounds(404, 305, 174, 50);
		contentPane.add(btn_tables);

		JButton btn_orders = new JButton("SİPARİŞLER");
		btn_orders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OrderGUI orderGUI = new OrderGUI(user);
				orderGUI.setVisible(true);

			}

		});
		btn_orders.setForeground(Color.BLACK);
		btn_orders.setBackground(Color.LIGHT_GRAY);
		btn_orders.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_orders.setBounds(693, 305, 174, 50);
		contentPane.add(btn_orders);

		JLabel lbl_user = new JLabel("Aktif Kullanıcı : " + user.getUserName());
		lbl_user.setForeground(Color.WHITE);
		lbl_user.setBackground(Color.BLACK);
		lbl_user.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_user.setBounds(334, 186, 365, 55);
		contentPane.add(lbl_user);

		JButton btn_exit = new JButton("ÇIKIŞ");
		btn_exit.setForeground(Color.BLACK);
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_exit.setBackground(Color.RED);
		btn_exit.setBounds(753, 68, 174, 50);
		contentPane.add(btn_exit);

		JButton btn_addUser = new JButton("KULLANICI EKLE");
		btn_addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserGUI userGUI = new UserGUI(user);
				userGUI.setVisible(true);

			}

		});
		btn_addUser.setEnabled(false);
		btn_addUser.setForeground(Color.BLACK);
		btn_addUser.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_addUser.setBackground(Color.LIGHT_GRAY);
		btn_addUser.setBounds(54, 68, 226, 50);
		contentPane.add(btn_addUser);

		if (user.getUserType().equals("admin")) {
			btn_addUser.setEnabled(true);
			btn_menu.setEnabled(true);
		}

		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (helper.showConfirm("sure")) {
					LoginGUI loginGUI = new LoginGUI();
					loginGUI.setVisible(true);
					dispose();
				}
			}
		});

	}
}
