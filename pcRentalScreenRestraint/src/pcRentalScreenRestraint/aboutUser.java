package pcRentalScreenRestraint;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;

public class aboutUser extends JFrame {

	private JPanel contentPane;
	private static JLabel lblUsernamePlaceholder;
	private static JLabel lblEmailPlaceholder;
	private static JLabel lblNumberPlaceholder;
	private static JLabel lblBalancePlaceholder;
	private static Connection con = optimizedConnectionTest.getConnection();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//nothing to see here because the about user button from mainsessiontime will do the execution.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public aboutUser(String user) {
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("screenRestraintIcon.png"));
		setIconImage(logo.getImage());
		
		setResizable(false);
		setTitle("About " + user);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 347, 178);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(10, 13, 96, 14);
		contentPane.add(lblUsername);
		
		lblUsernamePlaceholder = new JLabel("");
		lblUsernamePlaceholder.setForeground(Color.WHITE);
		lblUsernamePlaceholder.setBounds(131, 11, 186, 19);
		contentPane.add(lblUsernamePlaceholder);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(10, 48, 96, 14);
		contentPane.add(lblEmail);
		
		lblEmailPlaceholder = new JLabel("");
		lblEmailPlaceholder.setForeground(Color.WHITE);
		lblEmailPlaceholder.setBounds(131, 46, 186, 19);
		contentPane.add(lblEmailPlaceholder);
		
		JLabel lblNumber = new JLabel("Phone Number:");
		lblNumber.setForeground(Color.WHITE);
		lblNumber.setBounds(10, 81, 96, 14);
		contentPane.add(lblNumber);
		
		lblNumberPlaceholder = new JLabel("");
		lblNumberPlaceholder.setForeground(Color.WHITE);
		lblNumberPlaceholder.setBounds(131, 79, 186, 19);
		contentPane.add(lblNumberPlaceholder);
		
		JLabel lblBalance = new JLabel("Current Balance:");
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setBounds(10, 114, 96, 14);
		contentPane.add(lblBalance);
		
		lblBalancePlaceholder = new JLabel("");
		lblBalancePlaceholder.setForeground(Color.WHITE);
		lblBalancePlaceholder.setBounds(131, 112, 186, 19);
		contentPane.add(lblBalancePlaceholder);
		
		try {
			System.out.println(user);
			Class.forName("com.mysql.cj.jdbc.Driver");
			Statement st = con.createStatement();
			String query = "select username, email, phonenum, balance from user_accounts where username = 'beary_johny'";
			ResultSet rs = st.executeQuery(query);
			
			String username, email, number, balance;
			
			if(rs.next()) {
				username = rs.getString(1);
				email = rs.getString(2);
				number = rs.getString(3);
				balance = rs.getString(4);
				
				lblUsernamePlaceholder.setText(username);
				lblEmailPlaceholder.setText(email);
				lblNumberPlaceholder.setText(number);
				lblBalancePlaceholder.setText(balance);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("No Connection");
		}
	}
}
