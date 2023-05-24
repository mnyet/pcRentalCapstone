package pcRentalCapstone;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class changePassword extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordFieldChange;
	private static Connection con = optimizedConnectionTest.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//this will be ran by resetPassword.java
					//changePassword frame = new changePassword();
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
	public changePassword(String user) {
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("adminPanelIcon.png"));
		setIconImage(logo.getImage());
		setTitle("Change password for " + user);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 356, 125);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterNewPass = new JLabel("Enter new password:");
		lblEnterNewPass.setBounds(20, 23, 111, 14);
		contentPane.add(lblEnterNewPass);
		
		passwordFieldChange = new JPasswordField();
		passwordFieldChange.setBounds(136, 20, 182, 23);
		contentPane.add(passwordFieldChange);
		
		JButton btnChangePassword = new JButton("Submit");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Statement st = con.createStatement();
					String query = "update user_accounts set pass = md5('" + passwordFieldChange.getText() +"') where username = '" + user +"';";
					
					if(passwordFieldChange.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Please enter a password!");
					}
					else {
						st.executeUpdate(query);
						JOptionPane.showMessageDialog(null, "Password changed successfully!");
						dispose();
					}
					
				} catch (Exception e1){
					System.out.println(e1);
				}
			}
		});
		btnChangePassword.setBounds(136, 54, 89, 23);
		contentPane.add(btnChangePassword);
	}
}
