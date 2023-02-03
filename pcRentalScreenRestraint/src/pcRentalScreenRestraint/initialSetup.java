package pcRentalScreenRestraint;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class initialSetup extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIPAdress;
	private JTextField textFieldDbName;
	private JTextField textFieldAdminPin;
	private JTextField textFieldShopName;
	
	/**
	 * Launch the application.
	 * hi
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
						skipCreateFile();
					}
					
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		});
	}
	
	public static void skipCreateFile() {
		mainScreenRestraint startDashboard = new mainScreenRestraint();
		mainScreenRestraint.closeExplorer();
		mainScreenRestraint.timerCountdown(300);
		startDashboard.setVisible(true);
	}
	
	public void createFile(String configNetwork, String configDatabase, String configPin, String configName) {
		try {
			FileWriter createData = new FileWriter("networkconfig.dat");
			createData.write(configNetwork + "\n");
			createData.write(configDatabase + "\n");
			createData.write(configPin + "\n");
			createData.write(configName);
			createData.close();
		} catch (IOException e) {
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
		setBounds(100, 100, 334, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNotice = new JLabel("<html>\r\n\tMake sure that your XAMPP/MySQL Server is Activated! <br>\r\n\tor else the program will not work as intended. <br><br>\r\n\tThe credentials of the databse is default<br>\r\n\twith the username root and no password.<br><br>\r\n\tEnjoy the program! - Bear <br><br>\r\n\t<small>[MAKE SURE TO WRITE THE CORRECT CONFIGURATION AS THE SERVER]</small>\r\n</html>");
		lblNotice.setBounds(25, 23, 276, 133);
		contentPane.add(lblNotice);
		
		JLabel lblIPv4 = new JLabel("IPv4 Address:");
		lblIPv4.setBounds(25, 174, 86, 14);
		contentPane.add(lblIPv4);
		
		JLabel lblDbName = new JLabel("Database Name:");
		lblDbName.setBounds(25, 215, 86, 14);
		contentPane.add(lblDbName);
		
		textFieldIPAdress = new JTextField();
		textFieldIPAdress.setText("192.168.x.x:xxxx");
		textFieldIPAdress.setBounds(121, 171, 180, 20);
		contentPane.add(textFieldIPAdress);
		textFieldIPAdress.setColumns(10);
		
		textFieldDbName = new JTextField();
		textFieldDbName.setText("bear_john");
		textFieldDbName.setColumns(10);
		textFieldDbName.setBounds(121, 212, 180, 20);
		contentPane.add(textFieldDbName);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String savedNetwork = textFieldIPAdress.getText();
				String savedDatabaseName = textFieldDbName.getText();
				String savedAdminPin = textFieldAdminPin.getText();
				String savedShopName = textFieldShopName.getText();
				createFile(savedNetwork, savedDatabaseName, savedAdminPin, savedShopName);
				dispose();
				JOptionPane.showMessageDialog(null, "Configurations saved, Enjoy the program!");
				skipCreateFile();
			}
		});
		btnSave.setBounds(25, 322, 276, 23);
		contentPane.add(btnSave);
		
		JLabel lblDbExitPin = new JLabel("Exit Pin [ADMIN]:");
		lblDbExitPin.setBounds(25, 253, 86, 14);
		contentPane.add(lblDbExitPin);
		
		textFieldAdminPin = new JTextField();
		textFieldAdminPin.setText("0000");
		textFieldAdminPin.setColumns(10);
		textFieldAdminPin.setBounds(121, 250, 180, 20);
		contentPane.add(textFieldAdminPin);
		
		JLabel lblShopName = new JLabel("Name of Shop:");
		lblShopName.setBounds(25, 290, 86, 14);
		contentPane.add(lblShopName);
		
		textFieldShopName = new JTextField();
		textFieldShopName.setText("bear");
		textFieldShopName.setColumns(10);
		textFieldShopName.setBounds(121, 287, 180, 20);
		contentPane.add(textFieldShopName);
	}
}
