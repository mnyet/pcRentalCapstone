package pcRentalScreenRestraint;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.*;

public class screenLockWhileSession extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JLabel lblPasswordCheck;
	private JButton btnUnlock;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private static Connection con = mainScreenRestraint.con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//screenLockWhileSession frame = new screenLockWhileSession();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// may kulang pa dito, kapag nawalan ng internet sa 

	/**
	 * Create the frame.
	 */
	public screenLockWhileSession(String user) {
		mainScreenRestraint.closeExplorer();
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xsize = (int)tk.getScreenSize().getWidth();
		int ysize = (int)tk.getScreenSize().getHeight();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true);
		toFront();
		requestFocus();
		//setLocationRelativeTo(null);
		getContentPane().setBackground(null);
		getContentPane().setLayout(null);
		setSize(xsize, ysize);
		setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		//bear pag gusto mong iedit yung panel gawin mo munang integer yung ysize dito andali mo makalimot kaloka k
		panel.setBounds(0, 0, 400, ysize);
		panel.setLayout(null);
		panel.setBackground(new Color(0, 0, 0, 179));
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 0, 0, 0));
		panel_1.setBounds(82, 335, 241, 23);
		panel.add(panel_1);
		
		lblPasswordCheck = new JLabel("");
		lblPasswordCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordCheck.setBounds(0, 0, 241, 23);
		panel_1.add(lblPasswordCheck);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(150, 263, 173, 20);
		panel.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(82, 266, 50, 14);
		panel.add(lblPassword);
		
		JLabel lblThisPcIs = new JLabel("<html>\r\n\t<p>This PC is blocked by " + user +"!</p>\r\n\t<br>\r\n\t<p>To unlock this PC, please enter your password.</p>\r\n</html>");
		lblThisPcIs.setForeground(Color.WHITE);
		lblThisPcIs.setBounds(82, 194, 241, 58);
		panel.add(lblThisPcIs);
		
		btnUnlock = new JButton("Unlock");
		btnUnlock.setBounds(82, 301, 241, 23);
		panel.add(btnUnlock);
		btnUnlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runnable r = new loginAtt(user);
			}
		});
		
		lblNewLabel = new JLabel("New label");
		//ppull yung image dito galing sa res folder.
		lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bg.jpg")));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(0, 0, xsize, ysize);
		getContentPane().add(lblNewLabel);
	}
	
	public class loginAtt implements Runnable {
		public loginAtt(String user) {
			try {
				 Class.forName("com.mysql.cj.jdbc.Driver");
				 Statement stmt = con.createStatement();
				 String query = "select username, pass from user_accounts where username = '" + user + "' and pass = md5('" + passwordField.getText() +"'); ";
				 ResultSet rs = stmt.executeQuery(query);
				 
				 if(rs.next()) {
					 mainScreenRestraint.openExplorer();
					 dispose();
				 }
				 else {
					 panel_1.setBackground(Color.BLACK);
					 lblPasswordCheck.setForeground(Color.RED);
					 lblPasswordCheck.setText("Incorrect Password.");
				 }
				 
				 
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print(e);
				panel_1.setBackground(Color.BLACK);
				lblPasswordCheck.setForeground(Color.RED);
				btnUnlock.setEnabled(false);
				lblPasswordCheck.setText("If you see this, go to the admin for assistance.");
			}
		}
		public void run() {
			
		}
	}
}
