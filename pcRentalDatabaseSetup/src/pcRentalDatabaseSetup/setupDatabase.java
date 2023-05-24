package pcRentalDatabaseSetup;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pcRentalDatabaseSetup.optimizedConnectionTest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.ActionEvent;

public class setupDatabase extends JFrame {

	private JPanel contentPane;
	private static Connection con = optimizedConnectionTest.getConnection();
	private static String dbNameRead, hashKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					if(con != null) {
						System.out.println("Connected to the database.");
						setupDatabase frame = new setupDatabase();
						frame.setVisible(true);
					} else {
						System.out.println("No connection.");
					}
					/*
					*/
					
				} catch (Exception e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "No connection.");
					System.exit(0);
				}
			}
		});
	}
	
	private static void readDatabaseName() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("networkconfig.dat"));
			hashKey = br.readLine(); //line 1
			br.readLine(); //line 2
			dbNameRead = optimizedConnectionTest.decrypt(br.readLine(), hashKey); //line 3
			//dbUsername = decrypt(br.readLine(), hashKey); //line 4
			//dbPassword = decrypt(br.readLine(), hashKey); //line 5
			br.close();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "You shouldn't be here.");
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Create the frame.
	 */
	public setupDatabase() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		readDatabaseName();
		setResizable(false);
		setTitle("Setup Database for pcrental");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JLabel lblCreateDatabase = new JLabel("Create Database");
		lblCreateDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateDatabase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCreateDatabase.setBounds(33, 26, 122, 26);
		contentPane.add(lblCreateDatabase);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println(dbNameRead);
					Statement stmt = con.createStatement();
					//String dbName = dbNameRead;
					String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbNameRead + "'";
					ResultSet rs = stmt.executeQuery(query);
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "Database Exists.");
					} else{
						String createDbQuery = "CREATE DATABASE " + dbNameRead;
						String selectDb = "USE " + dbNameRead;
						String createTblUserQuery = "create table user_accounts(\r\n"
								+ "	uid int not null auto_increment primary key,\r\n"
								+ "	email varchar(50),\r\n"
								+ "	username varchar(20),\r\n"
								+ "	pass varchar(100),\r\n"
								+ "	phonenum varchar(11),\r\n"
								+ "	balance int\r\n"
								+ "	);";
						String createTblTransactionQuery = "create table user_transactions(\r\n"
								+ "	username varchar(20),\r\n"
								+ "	balance int,\r\n"
								+ "	topupdate timestamp\r\n"
								+ "	);";
						stmt.executeUpdate(createDbQuery);
						stmt.executeUpdate(selectDb);
						stmt.executeUpdate(createTblUserQuery);
						stmt.executeUpdate(createTblTransactionQuery);
						JOptionPane.showMessageDialog(null, "Database Created Successfully.");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(33, 65, 122, 26);
		contentPane.add(btnNewButton);
		
		JLabel lblDeleteDatabase = new JLabel("Delete Database");
		lblDeleteDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteDatabase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDeleteDatabase.setBounds(186, 26, 122, 26);
		contentPane.add(lblDeleteDatabase);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(dbNameRead);
					Statement stmt = con.createStatement();
					String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbNameRead + "'";
					ResultSet rs = stmt.executeQuery(query);
					if(rs.next()) {
						String delDb = "DROP DATABASE " + dbNameRead;
						stmt.execute(delDb);
						JOptionPane.showMessageDialog(null, "Successfully deleted database.");
					} else {
						JOptionPane.showMessageDialog(null, "Database Doesn't Exist.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnDelete.setBounds(186, 65, 122, 26);
		contentPane.add(btnDelete);
		JLabel lblDatabaseDetails = new JLabel("Database Name: " + dbNameRead);
		lblDatabaseDetails.setHorizontalAlignment(SwingConstants.LEFT);
		lblDatabaseDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDatabaseDetails.setBounds(33, 107, 275, 26);
		contentPane.add(lblDatabaseDetails);
	}
}
