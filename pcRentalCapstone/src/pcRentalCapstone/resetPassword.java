package pcRentalCapstone;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class resetPassword extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldResetEmail;
	private JTextField textFieldResetPhone;
	private static Connection con = optimizedConnectionTest.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//this will be ran by userTable.java
					//resetPassword frame = new resetPassword();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public resetPassword(String user) {
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("adminPanelIcon.png"));
		setIconImage(logo.getImage());
		setTitle("Reset Password for " + user);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 338, 199);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblResetEmail = new JLabel("Reset through email: ");
		lblResetEmail.setBounds(10, 23, 115, 14);
		contentPane.add(lblResetEmail);
		
		textFieldResetEmail = new JTextField();
		textFieldResetEmail.setBounds(135, 20, 174, 20);
		contentPane.add(textFieldResetEmail);
		textFieldResetEmail.setColumns(10);
		
		JButton btnResetEmail = new JButton("Reset");
		btnResetEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Statement st = con.createStatement();
					String query = "SELECT username, email FROM user_accounts WHERE email = '" + textFieldResetEmail.getText() + "' and username = '" + user +"';";
					ResultSet rs = st.executeQuery(query);
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "Successfuly Verified!");
						dispose();
						changePassword changepass = new changePassword(user);
						changepass.setVisible(true);
					} 
					else if(textFieldResetEmail.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Please enter an Email!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Incorrect Email!");
					}
					
				} catch (Exception e1){
					System.out.println(e1);
				}
			}
		});
		btnResetEmail.setBounds(135, 48, 174, 20);
		contentPane.add(btnResetEmail);
		
		JLabel lblResetPhone = new JLabel("Reset through phone: ");
		lblResetPhone.setBounds(10, 98, 115, 14);
		contentPane.add(lblResetPhone);
		
		textFieldResetPhone = new JTextField();
		textFieldResetPhone.setColumns(10);
		textFieldResetPhone.setBounds(135, 95, 174, 20);
		contentPane.add(textFieldResetPhone);
		
		JButton btnResetNumber = new JButton("Reset");
		btnResetNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Statement st = con.createStatement();
					String query = "SELECT username, phonenum FROM user_accounts WHERE phonenum = '" + textFieldResetPhone.getText() + "' and username = '" + user +"';";
					ResultSet rs = st.executeQuery(query);
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "Successfuly Verified!");
						dispose();
						changePassword changepass = new changePassword(user);
						changepass.setVisible(true);
					} 
					else if(textFieldResetPhone.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Please enter a phone number!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Incorrect Number!");
					}
					
				} catch (Exception e1){
					System.out.println(e1);
				}
			}
		});
		btnResetNumber.setBounds(135, 123, 174, 20);
		contentPane.add(btnResetNumber);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 79, 254, 2);
		contentPane.add(separator);
		System.out.println(user);
	}
}
