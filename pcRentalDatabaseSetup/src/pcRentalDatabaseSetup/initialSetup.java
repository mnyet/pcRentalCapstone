package pcRentalDatabaseSetup;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Scanner;
import javax.swing.JPasswordField;

public class initialSetup extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIPAdress;
	private JTextField textFieldDbName;
	private JTextField textFieldDbUsername;
	private JPasswordField textFieldDbPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File userdata = new File("networkconfig.dat");
					
					if(userdata.createNewFile()) {
						FileWriter createData = new FileWriter("networkconfig.dat");
						createData.close();
						try {
							initialSetup frame = new initialSetup();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Yo!");
						skipCreateFile();
					}
					
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		});
	}
	
	private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
	
	//does the encryption
    public static String encrypt(String plainText, String encryptionKey) throws Exception {
    	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
	
	public static void skipCreateFile() {
		setupDatabase startDashboard = new setupDatabase();
		startDashboard.setVisible(true);
	}
	
	public void createFile(String configNetwork, String configDatabase, String configUsername, String configPassword) {
		try {
			FileWriter createData = new FileWriter("networkconfig.dat");
			//adds the key to the file
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	        
	        // Generate a random 128-bit key
	        keyGenerator.init(128);
	        SecretKey secretKey = keyGenerator.generateKey();
	        String encodedSecretGenKey = bytesToHex(secretKey.getEncoded());
	        createData.write(encodedSecretGenKey + "\n");
			createData.write(encrypt(configNetwork, encodedSecretGenKey) + "\n");
			createData.write(encrypt(configDatabase, encodedSecretGenKey) + "\n");
			createData.write(encrypt(configUsername, encodedSecretGenKey) + "\n");
			createData.write(encrypt(configPassword, encodedSecretGenKey));
			createData.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public initialSetup() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setResizable(false);
		setTitle("Initial Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 367, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNotice = new JLabel("<html>\r\n\tMake sure that your XAMPP/MySQL Server is Activated! <br>\r\n\tor else the program will not work as intended. <br><br>\r\n\tThe credentials of the databse is default<br>\r\n\twith the username root and no password.<br><br>\r\n\tEnjoy the program! - Bear\r\n</html>");
		lblNotice.setBounds(25, 34, 311, 110);
		contentPane.add(lblNotice);
		
		JLabel lblIPv4 = new JLabel("IPv4 Address:");
		lblIPv4.setBounds(25, 161, 121, 14);
		contentPane.add(lblIPv4);
		
		JLabel lblDbName = new JLabel("Database Name:");
		lblDbName.setBounds(25, 188, 121, 14);
		contentPane.add(lblDbName);
		
		textFieldIPAdress = new JTextField();
		textFieldIPAdress.setText("192.168.x.x:xxxx");
		textFieldIPAdress.setBounds(156, 159, 180, 20);
		contentPane.add(textFieldIPAdress);
		textFieldIPAdress.setColumns(10);
		
		textFieldDbName = new JTextField();
		textFieldDbName.setText("bear_john");
		textFieldDbName.setColumns(10);
		textFieldDbName.setBounds(156, 186, 180, 20);
		contentPane.add(textFieldDbName);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String savedNetwork = textFieldIPAdress.getText();
				String savedDatabaseName = textFieldDbName.getText();
				String savedDatabaseUsername = textFieldDbUsername.getText();
				String savedDatabasePassword = textFieldDbPassword.getText();
				createFile(savedNetwork, savedDatabaseName, savedDatabaseUsername, savedDatabasePassword);
				dispose();
				JOptionPane.showMessageDialog(null, "Configurations saved, Enjoy the program!");
				skipCreateFile();
			}
		});
		btnSave.setBounds(25, 277, 89, 23);
		contentPane.add(btnSave);
		
		JLabel lblDbUsername = new JLabel("Database Username:");
		lblDbUsername.setBounds(25, 215, 121, 14);
		contentPane.add(lblDbUsername);
		
		textFieldDbUsername = new JTextField();
		textFieldDbUsername.setText("root");
		textFieldDbUsername.setColumns(10);
		textFieldDbUsername.setBounds(156, 213, 180, 20);
		contentPane.add(textFieldDbUsername);
		
		JLabel lblDbPassword = new JLabel("Database Password:");
		lblDbPassword.setBounds(25, 242, 121, 14);
		contentPane.add(lblDbPassword);
		
		textFieldDbPassword = new JPasswordField();
		textFieldDbPassword.setText(" ");
		textFieldDbPassword.setColumns(10);
		textFieldDbPassword.setBounds(156, 240, 180, 20);
		contentPane.add(textFieldDbPassword);
	}
}
