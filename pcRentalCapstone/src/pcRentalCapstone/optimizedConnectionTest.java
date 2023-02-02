package pcRentalCapstone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class optimizedConnectionTest {
	static Connection con = null;
	private static String dbNetwork = getDataFromFile("", null);
	private static String dbDatabaseName = getDataFromFile(null, "");
	private static String dbUsername = "root";
	private static String dbPassword = "";
	
	public static String getDataFromFile(String net, String db) {
		String network = "";
		String database = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("networkconfig.dat"));
			network = br.readLine();
			database = br.readLine();
			br.close();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "You shouldn't be here.");
			System.exit(0);
		}
		
		if(net == null) {
			return database;
		} else {
			return network;
		}

	}
	
	public static Connection getConnection() {
		if (con != null) return con;
		
		return getConnection("pcrental", "root", "");
	}
	
	private static Connection getConnection(String db_name, String user_name, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + dbNetwork + "/" + dbDatabaseName +"", "" + dbUsername + "", "" + dbPassword + "");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	/*
	 * 
	 * public static void main(String[] Args) {
		String finalBalance = "";
		int num = 1000;
		
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost/pcrental", "root", "");
			Statement stmt = getConnection().createStatement();
			String query = "select balance from user_accounts where username = 'beary_john'" + ";";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				finalBalance = rs.getString(1);
			}
			System.out.println("hi");
			System.out.println(getConnection());
			num--;
			Thread.sleep(10);
		} catch (Exception e1){
			e1.printStackTrace();
		}
		
		while(num > 0) {
			System.out.println(num);
			try {
				//Class.forName("com.mysql.cj.jdbc.Driver");
				//Connection con = DriverManager.getConnection("jdbc:mysql://localhost/pcrental", "root", "");
				Statement stmt = getConnection().createStatement();
				String query = "select balance from user_accounts where username = 'beary_john'" + ";";
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					finalBalance = rs.getString(1);
				}
				System.out.println(finalBalance);
				System.out.println(getConnection());
				num--;
				Thread.sleep(10);
			} catch (Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
	*/
	
}
