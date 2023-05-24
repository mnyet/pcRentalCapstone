package pcRentalScreenRestraint;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class mainScreenRestraint {

	private JFrame frame;
	private static Runtime rtm = Runtime.getRuntime();
	private static Timer timer;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	private JButton tempExit;
	private JPanel panel;
	private JLabel lblNewLabel;
	private static JButton btnLoginButton;
	private JLabel lblPasswordCheck;
	private static JPanel panel_1;
	private static JLabel lblCountDown;
	public static Connection con = optimizedConnectionTest.getConnection();
	private static String shopName = getShopName("");
	ImageIcon image;
	private static String hashKey = "";
	
	public static String getShopName(String name) {
		String shop = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("networkconfig.dat"));
			hashKey = br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			shop = decrypt(br.readLine(), hashKey);
			br.close();
			
		} catch(Exception e) {
			
		}
		
		return shop;
	}
	
	//does the decryption
    public static String decrypt(String encrypted, String encryptionKey) throws Exception {
    	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(decryptedBytes);
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		closeExplorer();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainScreenRestraint window = new mainScreenRestraint();
					window.frame.setVisible(true);
					timerCountdown(300);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainScreenRestraint() {
		initialize();
	}
	
	public void setVisible(boolean b) {
		mainScreenRestraint window = new mainScreenRestraint();
		window.frame.setVisible(true);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xsize = (int)tk.getScreenSize().getWidth();
		int ysize = (int)tk.getScreenSize().getHeight();
		
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(null);
		frame.setBounds(100, 100, 800, 600);
		frame.setSize(xsize, ysize);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true);
		frame.toFront();
		frame.requestFocus();
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 0.7f));
		//bear pag gusto mong iedit yung panel gawin mo munang integer yung ysize dito andali mo makalimot kaloka k
		panel.setBounds(0, 0, 400, ysize);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(82, 369, 241, 23);
		panel_1.setBackground(new Color(0, 0, 0, 0));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lblPasswordCheck = new JLabel("");
		lblPasswordCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordCheck.setBounds(0, 0, 241, 23);
		panel_1.add(lblPasswordCheck);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(82, 231, 52, 14);
		panel.add(lblUsername);
		lblUsername.setForeground(Color.WHITE);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(150, 263, 173, 20);
		panel.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(82, 266, 50, 14);
		panel.add(lblPassword);
		lblPassword.setForeground(Color.WHITE);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(150, 228, 173, 20);
		panel.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		btnLoginButton = new JButton("Login");
		btnLoginButton.setBounds(82, 301, 84, 23);
		panel.add(btnLoginButton);
		btnLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread login = new Thread(loginAtt);
				login.start();
			}
		});
		
		/*
		tempExit = new JButton("Exit ni bear");
		tempExit.setBounds(82, 417, 115, 23);
		panel.add(tempExit);
		tempExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openExplorer();
				System.exit(0);
			}
		});
		*/
		
		//kung gusto mong iedit to bear sa design tanggalin mo muna yung shopName kasi nagccrash yung ide.
		JLabel lblGreetings = new JLabel("Welcome to " + shopName + "!");
		lblGreetings.setForeground(Color.WHITE);
		lblGreetings.setBounds(82, 194, 241, 14);
		panel.add(lblGreetings);
		
		JButton btnAdminPass = new JButton("AdminPass");
		btnAdminPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeExplorer();
				adminExploit openAdmin = new adminExploit();
				openAdmin.setVisible(true);
			}
		});
		btnAdminPass.setBounds(176, 301, 84, 23);
		panel.add(btnAdminPass);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(82, 335, 241, 23);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		lblCountDown = new JLabel("");
		lblCountDown.setForeground(Color.YELLOW);
		lblCountDown.setBounds(0, 0, 241, 23);
		panel_2.add(lblCountDown);
		
		JButton btnPowerOptions = new JButton("P");
		btnPowerOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				powerOptions powOps = new powerOptions();
				powOps.setVisible(true);
			}
		});
		btnPowerOptions.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPowerOptions.setBounds(270, 301, 53, 23);
		panel.add(btnPowerOptions);
		
		lblNewLabel = new JLabel("New label");
		//ppull yung image dito galing sa res folder.
		lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bg.jpg")));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(0, 0, xsize, ysize);
		frame.getContentPane().add(lblNewLabel);
	}
	
	public static void closeExplorer() {
		try {
			rtm.exec("taskkill /F /IM explorer.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void openExplorer() {
		List<String> lst = new ArrayList<String>();
		lst.add("explorer.exe");
		ProcessBuilder bld = new ProcessBuilder(lst);
		try {
			bld.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Command: " + bld.command());
	}
	
	public static void timerCountdown(int seconds) {
		timer = new Timer();
		TimerTask tt = new TimerTask() {
			int timeSec = seconds;
			@Override
			public void run() {
				if(timeSec <= 0) {
					try {
						rtm.exec("shutdown -s -t 5");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					lblCountDown.setText("You only have " + timeSec + " seconds left before shutdown.");
					timeSec--;
				}			
			}	
		};
		timer.scheduleAtFixedRate(tt, 1000, 1000);
	}
	
	Runnable loginAtt = new Runnable() {
		@Override
		public void run() {
			try {
				 Class.forName("com.mysql.cj.jdbc.Driver");
				 Statement stmt = con.createStatement();
				 String query = "select username, pass from user_accounts where username = '" + textFieldUsername.getText() + "' and pass = md5('" + textFieldPassword.getText() +"'); ";
				 ResultSet rs = stmt.executeQuery(query);
				 String fetchUser = textFieldUsername.getText();
				 
				 if(rs.next()) {
					 timer.cancel();
					 System.out.println("screen restraint connection: " + con);
					 openExplorer();
					 frame.dispose();
					 mainSessionTime mainsesh = new mainSessionTime(fetchUser);
					 mainsesh.setVisible(true);
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
				btnLoginButton.setEnabled(false);
				lblPasswordCheck.setText("If you see this, go to the admin for assistance.");
			}
		}
	};
}

