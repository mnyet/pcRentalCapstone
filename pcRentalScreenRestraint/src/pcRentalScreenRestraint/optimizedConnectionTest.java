package pcRentalScreenRestraint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class optimizedConnectionTest {
	static Connection con = null;
	private static String dbNetwork;
	private static String dbDatabaseName;
	private static String dbUsername;
	private static String dbPassword;
	private static String hashKey;
	
	public static void getDataFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("networkconfig.dat"));
			hashKey = br.readLine(); //line 1
			dbNetwork = decrypt(br.readLine(), hashKey); //line 2
			dbDatabaseName = decrypt(br.readLine(), hashKey); //line 3
			dbUsername = decrypt(br.readLine(), hashKey); //line 4
			dbPassword = decrypt(br.readLine(), hashKey); //line 5
			br.close();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Config File has been modified which can cause system instability.");
		}

	}
	
	public static Connection getConnection() {
		if (con != null) return con;
		
		return getConnection("", "", "");
	}
	
	private static Connection getConnection(String db_name, String user_name, String password) {
		try {
			getDataFromFile();
			Class.forName("com.mysql.cj.jdbc.Driver");
			//System.out.println(dbNetwork + dbDatabaseName + dbUsername + dbPassword );
			con = DriverManager.getConnection("jdbc:mysql://" + dbNetwork + "/" + dbDatabaseName +"", "" + dbUsername + "", "" + dbPassword + "");
		}
		catch(Exception e) {
			//e.printStackTrace();
			System.out.println("No Connection.");
		}
		
		return con;
	}
	
	//does the decryption
    public static String decrypt(String encrypted, String encryptionKey) throws Exception {
    	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(decryptedBytes);
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
