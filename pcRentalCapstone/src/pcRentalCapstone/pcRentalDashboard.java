package pcRentalCapstone;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import pcRentalCapstone.optimizedConnectionTest;

import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Panel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class pcRentalDashboard extends JFrame {

	private JPanel contentPane;
	private static JTable tblData;
	private static Runtime rtm = Runtime.getRuntime();
	private static Connection con = optimizedConnectionTest.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pcRentalDashboard frame = new pcRentalDashboard();
					frame.setVisible(true);
					showData();
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println("No Connection");
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public pcRentalDashboard() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("adminPanelIcon.png"));
		setIconImage(logo.getImage());
		
		setTitle("PC Rental Admin Dashboard");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 664, 22);
		contentPane.add(menuBar);
		
		JMenu mnDev = new JMenu("Bear 2022-2023");
		mnDev.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		menuBar.add(mnDev);
		
		JLabel lblLatestTransactions = new JLabel("Latest Transactions:");
		lblLatestTransactions.setBounds(191, 33, 144, 22);
		contentPane.add(lblLatestTransactions);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 22, 181, 349);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblGreetings = new JLabel("Welcome Admin!");
		lblGreetings.setHorizontalAlignment(SwingConstants.CENTER);
		lblGreetings.setForeground(Color.WHITE);
		lblGreetings.setBounds(10, 41, 161, 40);
		panel.add(lblGreetings);
		
		JLabel lblWhatDoYou = new JLabel("What do you want to do?");
		lblWhatDoYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWhatDoYou.setForeground(Color.WHITE);
		lblWhatDoYou.setBounds(10, 84, 161, 40);
		panel.add(lblWhatDoYou);
		
		JButton btnRefreshTable = new JButton("Refresh Table");
		btnRefreshTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showData();
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "No Connection to the Database!");
				}
			}
		});
		btnRefreshTable.setBounds(10, 135, 161, 35);
		panel.add(btnRefreshTable);
		
		JButton btnAddNewUser = new JButton("Add new user");
		btnAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showData(); //checks for connection, and if wala magpprompt lang siya
					userCreate createuser = new userCreate();
					createuser.setVisible(true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "No Connection to the Database!");
				}
			}
		});
		btnAddNewUser.setBounds(10, 181, 161, 35);
		panel.add(btnAddNewUser);
		
		JButton btnManageUsers = new JButton("Manage users");
		btnManageUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showData(); //checks for connection, and if wala magpprompt lang siya
					userTable opentopup = new userTable();
					userTable.showData();
					opentopup.setVisible(true);
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "No Connection to the Database!");
				}
			}
		});
		btnManageUsers.setBounds(10, 227, 161, 35);
		panel.add(btnManageUsers);
		
		JButton btnManagePC = new JButton("Manage PCs");
		btnManagePC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openRRX();
			}
		});
		btnManagePC.setBounds(10, 273, 161, 35);
		panel.add(btnManagePC);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(191, 66, 452, 282);
		contentPane.add(scrollPane);
		
		tblData = new JTable();
		scrollPane.setViewportView(tblData);
	}
	
	public static void openRRX() {
		String dir = System.getProperty("user.dir");
		try {
			rtm.exec(dir + "\\extra\\run-rrx.bat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//this method shows the details of the table
		public static void showData() {
			try {
				tblData.setModel(new DefaultTableModel());
				Class.forName("com.mysql.cj.jdbc.Driver");
				Statement st = con.createStatement();
				String query = "select * from user_transactions;";
				ResultSet rs = st.executeQuery(query);
				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				int cols = rsmd.getColumnCount();
				String[] colName = new String[cols];
				for(int i=0; i<cols; i++) {
					colName[i] = rsmd.getColumnName(i+1);
					model.setColumnIdentifiers(colName);
				}
				
				String topupdate, username, balance;
				
				while(rs.next()) {
					username = rs.getString(1);
					balance = rs.getString(2);
					topupdate = rs.getString(3);
					String[] row = {username, balance, topupdate};
					model.addRow(row);
				}
				
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
}
