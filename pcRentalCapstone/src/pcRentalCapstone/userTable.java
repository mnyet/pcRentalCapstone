package pcRentalCapstone;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import pcRentalCapstone.optimizedConnectionTest;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class userTable extends JFrame {
	
	
	private JPanel contentPane;
	private static JTable tblData;
	private static JTextField textFindUser;
	private static JTextField textTopupAmount;
	private JLabel lblTopup;
	private JLabel lblFindUser;
	private JLabel lblRate;
	private static Connection con = optimizedConnectionTest.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userTable frame = new userTable();
					frame.setVisible(true);
					showData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public userTable() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("adminPanelIcon.png"));
		setIconImage(logo.getImage());
		
		setTitle("Manage Users");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 576, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showData();
			}
		});
		btnRefresh.setBounds(10, 31, 155, 20);
		contentPane.add(btnRefresh);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(186, 31, 363, 225);
		contentPane.add(scrollPane);
		
		tblData = new JTable();
		scrollPane.setViewportView(tblData);
		
		textFindUser = new JTextField();
		textFindUser.setBounds(10, 87, 155, 20);
		contentPane.add(textFindUser);
		textFindUser.setColumns(10);
		
		String[] rates = {"20", "15", "10"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox comboBox = new JComboBox(rates);
		
		comboBox.setBounds(119, 204, 46, 21);
		contentPane.add(comboBox);
		
		JButton btnFindUser = new JButton("Find user");
		btnFindUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findUser();
			}
		});
		btnFindUser.setBounds(10, 118, 155, 20);
		contentPane.add(btnFindUser);
		
		JButton btnTopupUser = new JButton("Topup user");
		btnTopupUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String multiplierString = (String) comboBox.getSelectedItem();
					int multiplierInt = Integer.parseInt(multiplierString);
					int topupAmountInt = Integer.parseInt(textTopupAmount.getText());
					
					if(topupAmountInt > 1000) {
						JOptionPane.showMessageDialog(null, "Only Maximum of 1000!");
					}
					else if(topupAmountInt < 10) {
						JOptionPane.showMessageDialog(null, "Only Minimum of 10!");
					}
					else {
						topupUser(multiplierInt);
					}
				} catch(Exception e1){
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, "Please enter numbers only!");
				}
			}
		});
		btnTopupUser.setBounds(10, 236, 155, 20);
		contentPane.add(btnTopupUser);
		
		textTopupAmount = new JTextField();
		textTopupAmount.setColumns(10);
		textTopupAmount.setBounds(10, 205, 102, 20);
		contentPane.add(textTopupAmount);
		
		lblTopup = new JLabel("Add user credits:");
		lblTopup.setBounds(10, 180, 102, 14);
		contentPane.add(lblTopup);
		
		lblFindUser = new JLabel("Find user:");
		lblFindUser.setBounds(10, 62, 155, 14);
		contentPane.add(lblFindUser);
		
		lblRate = new JLabel("Rate:");
		lblRate.setBounds(122, 180, 43, 14);
		contentPane.add(lblRate);
		
		JButton btnResetPass = new JButton("Reset User Password");
		btnResetPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int selectedRow = tblData.getSelectedRow();
					DefaultTableModel model = (DefaultTableModel)tblData.getModel();
					String username = (String) model.getValueAt(selectedRow, 1);
					//System.out.println(username);
					resetPassword resPas = new resetPassword(username);
					resPas.setVisible(true);
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Please select a user first!");
				}
			}
		});
		btnResetPass.setBounds(10, 149, 155, 20);
		contentPane.add(btnResetPass);
	}
	
	
	//this method shows the details of the table
	public static void showData() {
		try {
			tblData.setModel(new DefaultTableModel());
			Class.forName("com.mysql.cj.jdbc.Driver");
			Statement st = con.createStatement();
			String query = "select uid, username, balance from user_accounts;";
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) tblData.getModel();
			
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for(int i=0; i<cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
			
			String uid, username, balance;
			
			while(rs.next()) {
				uid = rs.getString(1);
				username = rs.getString(2);
				balance = rs.getString(3);
				String[] row = {uid, username, balance};
				model.addRow(row);
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//System.out.println("No Connection");
			System.out.println(e1);
		}
	}
	
	//i just separated the topup method
	public void topupUser(int rate) throws NullPointerException{
		try {
			//this will select the user depending on the row you clicked them
			int selectedRow = tblData.getSelectedRow();
			
			DefaultTableModel model = (DefaultTableModel)tblData.getModel();
			int userAmount = Integer.parseInt((String) model.getValueAt(selectedRow, 2));
			int addAmountTextField =  Integer.parseInt((String) textTopupAmount.getText());
			int addAmount = Math.round(convertToSeconds(addAmountTextField, rate));
			int setTopup = userAmount + addAmount;
			int userId = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
			String username = (String) model.getValueAt(selectedRow, 1);
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Statement st = con.createStatement();
				String queryTopup = "update user_accounts set balance = " + setTopup + " where uid = " + userId + ";";
				String queryTopupLog = "insert into user_transactions values("
						+ "'" + username + "' ,"
						+ "	" + addAmountTextField + ","
						+ "	current_timestamp"
						+ "	);";
				//this'll execute the query
				st.executeUpdate(queryTopup);
				st.executeUpdate(queryTopupLog);
				JOptionPane.showMessageDialog(null, "Topup Successful!");
				showData();
				pcRentalDashboard.showData();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch(ArrayIndexOutOfBoundsException e1) {
			JOptionPane.showMessageDialog(null, "Please select a user first!");
		} catch(NullPointerException e1) {
			//wala kasi pagod nako
			//System.out.println("No Connection");
			//System.out.println(e1);
			//System.out.println("hi"); turns out wala namang ginagawa to gano hehe
		}
	}
	
	static float convertToSeconds(int amount, int convertRate) {
		float conversion = convertRate; //how much peso per hour
		float convertTime = amount / conversion;
		float amountToSeconds = convertTime * 3600;
		
		return amountToSeconds;
	}
	
	public static void findUser() {
		try {
			String findUsername = textFindUser.getText();
			tblData.setModel(new DefaultTableModel());
			Class.forName("com.mysql.cj.jdbc.Driver");
			Statement st = con.createStatement();
			String query = "select uid, username, balance from user_accounts where username like '%" + findUsername + "%' ;";
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) tblData.getModel();
			
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for(int i=0; i<cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
			
			String uid, username, balance;
			
			while(rs.next()) {
				uid = rs.getString(1);
				username = rs.getString(2);
				balance = rs.getString(3);
				String[] row = {uid, username, balance};
				model.addRow(row);
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//System.out.println("No Connection");
			System.out.println(e1);
		}
		
	}
}
