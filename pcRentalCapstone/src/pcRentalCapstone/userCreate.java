package pcRentalCapstone;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pcRentalCapstone.optimizedConnectionTest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class userCreate extends JFrame {

	private JPanel contentPane;
	private static JTextField textFieldUsername;
	private JLabel lblPassword;
	private static JTextField textFieldEmail;
	private JLabel lblEmail;
	private static JTextField textFieldPhoneNo;
	private JLabel lblPhoneNo;
	private static JTextField textFieldIntTopup;
	private JLabel lblIntTopup;
	private static JPasswordField passwordFieldUserPass;
	private JLabel lblRate;
	private static Connection con = optimizedConnectionTest.getConnection();
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userCreate frame = new userCreate();
					frame.setVisible(true);
					frame.setTitle("Add a new user");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public userCreate() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("adminPanelIcon.png"));
		setIconImage(logo.getImage());
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Create new user");
		setResizable(false);
		setBounds(100, 100, 374, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] rates = {"20", "15", "10"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox comboBox = new JComboBox(rates);
		
		lblRate = new JLabel("Rate:");
		lblRate.setBounds(245, 175, 43, 14);
		contentPane.add(lblRate);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 21, 75, 26);
		contentPane.add(lblUsername);
		
		comboBox.setBounds(284, 169, 61, 26);
		contentPane.add(comboBox);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(95, 21, 250, 26);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 58, 75, 26);
		contentPane.add(lblPassword);
		
		passwordFieldUserPass = new JPasswordField();
		passwordFieldUserPass.setBounds(95, 58, 250, 26);
		contentPane.add(passwordFieldUserPass);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(95, 95, 250, 26);
		contentPane.add(textFieldEmail);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 95, 75, 26);
		contentPane.add(lblEmail);
		
		textFieldPhoneNo = new JTextField();
		//this is why i love indian people.
		textFieldPhoneNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				String phoneNumber = textFieldPhoneNo.getText();
				int length = phoneNumber.length();
				
				
				if(evt.getKeyChar() >= '0' && evt.getKeyChar()<='9') {
					if(length < 11) {
						textFieldPhoneNo.setEditable(true);
					} else {
						textFieldPhoneNo.setEditable(false);
					}
				} else {
					if(evt.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textFieldPhoneNo.setEditable(true);
					} else {
						textFieldPhoneNo.setEditable(false);
					}
				}
				
			}
		});
		textFieldPhoneNo.setColumns(11);
		textFieldPhoneNo.setBounds(95, 132, 250, 26);
		contentPane.add(textFieldPhoneNo);
		
		lblPhoneNo = new JLabel("Phone No:");
		lblPhoneNo.setBounds(10, 132, 75, 26);
		contentPane.add(lblPhoneNo);
		
		textFieldIntTopup = new JTextField();
		textFieldIntTopup.setColumns(10);
		textFieldIntTopup.setBounds(95, 169, 140, 26);
		contentPane.add(textFieldIntTopup);
		
		lblIntTopup = new JLabel("Int. Topup:");
		lblIntTopup.setBounds(10, 169, 75, 26);
		contentPane.add(lblIntTopup);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(
					textFieldUsername.getText().isEmpty() 
						|| passwordFieldUserPass.getText().isEmpty() 
						|| textFieldEmail.getText().isEmpty()
						|| textFieldPhoneNo.getText().isEmpty()
						|| textFieldIntTopup.getText().isEmpty()
						){
					JOptionPane.showMessageDialog(null, "Please fill all the required fields!");
				} else {
					if(numberCheck(textFieldPhoneNo.getText()) != true || numberCheck(textFieldIntTopup.getText()) != true) {
						JOptionPane.showMessageDialog(null, "Please enter only numbers in Phone Number and Initial Topup!");
					} else {
						if(checkUsername(textFieldUsername.getText()) != true) {
							//proceeds to registering the user if there isn't an existing username.
							String multiplierString = (String) comboBox.getSelectedItem();
							int multiplierInt = Integer.parseInt(multiplierString);
							registerUser(multiplierInt);
						} else {
							JOptionPane.showMessageDialog(null, "Username already exists!");
						}
					}
				}
				
			}
		});
		btnRegister.setBounds(10, 206, 335, 44);
		contentPane.add(btnRegister);
	}
	
	public static boolean checkUsername(String entry) {
		boolean doesExist = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Statement st = con.createStatement();
			String query = "select username from user_accounts where username = '" + textFieldUsername.getText() + "';";
			//this'll execute the query
			ResultSet rs = st.executeQuery(query);

			if(rs.next()) {
				doesExist = true;
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return doesExist;
		
	}
	
	public static boolean numberCheck(String entry) {
		boolean isNumber = false;
		try {
		     Integer.parseInt(entry);
		     isNumber = true;
		}
		catch (NumberFormatException e) {
		     isNumber = false;
		}
		
		return isNumber;
	}
	
	public static void registerUser(int rate) {
		String registerUsername = textFieldUsername.getText();
		String registerPassword = passwordFieldUserPass.getText();
		String registerEmail = textFieldEmail.getText();
		String registerNumber = textFieldPhoneNo.getText();
		int topupInput = Integer.parseInt(textFieldIntTopup.getText());
		/*
		 *  yung convertToSeconds dito is hiram doon sa convertToSeconds sa userTable
		 *  gawin mo tong practice bear na gumawa ng class file para sa mga methods para hindi na paulit ulit yung code
		 *  bobo ka pa naman but still love u hehez
		 *  
		 */
		int registerTopup = Math.round(userTable.convertToSeconds(topupInput, rate));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Statement st = con.createStatement();
			String query = "insert into user_accounts values("
					+ "	0,"
					+ "	'" + registerEmail + "',"
					+ "	'" + registerUsername + "',"
					+ "	md5('" + registerPassword + "'),"
					+ "	'" + registerNumber +"',"
					+ "	" + registerTopup + " "
					+ "	);";
			//this'll execute the query
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "User Registered!");
			//userTable.showData();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
