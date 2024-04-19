package oopMTBS;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import java.awt.Window.Type;
import javax.swing.SwingConstants;

public class OpeningFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAdmin;
	private LogInAdmin logInAdminFrame;
	private CustomerFrame customerFrame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpeningFrame frame = new OpeningFrame();
					frame.setVisible(true);
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
	public OpeningFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 541);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setForeground(SystemColor.menuText);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 0, 0));
		panel.setBounds(0, 0, 843, 101);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(128, 128, 128)));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblMTBS = new JLabel("Movie Ticket Booking System");
		lblMTBS.setBounds(129, 36, 647, 30);
		panel.add(lblMTBS);
		lblMTBS.setForeground(SystemColor.window);
		lblMTBS.setFont(new Font("Wide Latin", Font.BOLD, 24));
		
		JLabel lblFilmIcon = new JLabel("");
		lblFilmIcon.setBounds(54, 11, 78, 78);
		lblFilmIcon.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/film (1).png")));
		panel.add(lblFilmIcon);
		
		JButton btnBookNow = new JButton("BOOK NOW");
		btnBookNow.setForeground(new Color(153, 0, 0));
		btnBookNow.setBackground(new Color(255, 255, 255));
		btnBookNow.setFont(new Font("Traditional Arabic", Font.BOLD, 24));
		btnBookNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customerFrame == null) {
                    customerFrame = new CustomerFrame();
				}
				customerFrame.setVisible(true);

                OpeningFrame.this.dispose();
            }
		});
		btnBookNow.setBounds(148, 158, 510, 197);
		contentPane.add(btnBookNow);
		
		btnAdmin = new JButton("");
		btnAdmin.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdmin.setBackground(new Color(0, 0, 0));
		btnAdmin.setForeground(new Color(0, 0, 0));
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logInAdminFrame == null) {
                    logInAdminFrame = new LogInAdmin();
				}
				logInAdminFrame.setVisible(true);

                OpeningFrame.this.dispose();
            }
		});
		btnAdmin.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/circle (1).png")));
		btnAdmin.setBounds(10, 419, 81, 73);
		contentPane.add(btnAdmin);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(OpeningFrame.class.getResource("/images/cinemaBg.png")));
		lblNewLabel.setBounds(0, 111, 820, 391);
		contentPane.add(lblNewLabel);
	}
}
