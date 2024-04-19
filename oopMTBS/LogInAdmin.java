package oopMTBS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LogInAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField adUsername;
	private AdminLandingFrame adminFrame;
	private OpeningFrame openingFrame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInAdmin frame = new LogInAdmin();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogInAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 537);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProfileIcon = new JLabel("");
		lblProfileIcon.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/circle (2).png")));
		lblProfileIcon.setBounds(340, 93, 133, 128);
		contentPane.add(lblProfileIcon);
		
		adUsername = new JTextField();
		adUsername.setBounds(295, 246, 299, 36);
		contentPane.add(adUsername);
		adUsername.setColumns(10);
		
		JPasswordField adPassword = new JPasswordField();
		adPassword.setBounds(295, 293, 299, 36);
		contentPane.add(adPassword);
		
		JButton btnLogin = new JButton("LOG IN");
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(255, 51, 51));
		btnLogin.setFont(new Font("Traditional Arabic", Font.PLAIN, 14));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=adUsername.getText();
				String password=adPassword.getText();

				if (username.equals("movieAdmin") && password.equals("movieAdmin")){
					adminFrame = new AdminLandingFrame();
					adminFrame.setVisible(true);
					LogInAdmin.this.dispose();
				}

				else{
					//JOptionPane.showConfirmDialog(adminloginFrame, "Invalid input. Please try again");
					JOptionPane.showMessageDialog(adminFrame, "Invalid input. Please try again");
				}

			}
		});
		btnLogin.setBounds(370, 343, 133, 36);
		contentPane.add(btnLogin);
		
		JButton btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/home-page.png")));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openingFrame = new OpeningFrame();
				openingFrame.setVisible(true);
				LogInAdmin.this.dispose();
				
			}
		});
		btnBack.setFont(new Font("Traditional Arabic", Font.PLAIN, 13));
		btnBack.setBackground(new Color(0, 0, 0));
		btnBack.setBounds(10, 11, 40, 40);
		contentPane.add(btnBack);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(204, 249, 81, 25);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(210, 304, 75, 25);
		contentPane.add(lblPassword);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/ticketBg.png")));
		lblNewLabel.setBounds(0, 0, 820, 499);
		contentPane.add(lblNewLabel);
	}
}
