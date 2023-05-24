package pcRentalScreenRestraint;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class mainSessionTime extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private static JLabel lblUser;
	private static Timer timer;
	private static Runtime rtm = Runtime.getRuntime();
	private static boolean isRunning; //ginagamit to promise diko alam sa ide bat hindi ginagamit daw???
	private static JLabel lblTimer;
	private static Connection con = optimizedConnectionTest.getConnection();
	ImageIcon icon;
	private JButton btnAboutUser;
	private screenLockWhileSession scrLock = null;
	
	/*
	 * 
	 *
	
	[from bear 12-31-2022]
	
	iimplement mo to pamalit sa timer, mas optimized to kumpara sa unang gawa mo,
	what this does is gumagamit lang siya ng isang connection lang.
	
	[[see optimizedConnectionTest.java for references]]
	
	tinatamad lang ako gawin to kasi bakasyon pero utang na loob tapusin mo to.
	eto yung sagot bakit naglalag yung timer doon sa laptop ni erickson nung demo.
	
	tanggalin mo na yung con saka class.forname sa bawat queries na gagawin mo instead ganto gawin mo.
	
	stmt = getConnection().createStatement(); basta ganyan madali nalang yan putanginamo.
	
	ang need mo lang malaman dito is kung iisang connection lang ba ang gamit sa lahat ng connections na gagawin mo
	since marami kang methods na nakahiwalay para lang jaan like sa get balance and doon sa timer update.
	
	pag dasal mo nalang rin na eto yung sagot sa delay doon sa laptop ni erickson ayun lang goodluck!
	
	don't forget na iimplement to sa ibang classes para kabog at optimized.
	wag mo ring kalimutan gawin yung remote shutdown for multiple pc's saka yung guest account configs. <3
	
	mahal ka ni aone <333
	
	 * 
	 * 
	 * 
	 */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*
					 this will run from mainScreenRestraint class
					 so wala kayong magagawa dahil nanonood lang kayo 
					 kami ang pabebe girls
					 */
					//mainSessionTime frame = new mainSessionTime();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainSessionTime(String user) {
		
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("screenRestraintIcon.png"));
		setIconImage(logo.getImage());
		
		setResizable(false);
		setTitle("User Session for " + user);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		getUserBalance(user);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 357, 223);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUser = new JLabel("Enjoy the session, " + user + "!");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUser.setForeground(Color.WHITE);
		lblUser.setBounds(10, 11, 310, 53);
		contentPane.add(lblUser);
		
		JButton btnNewButton = new JButton("End Session");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				timerStop();
			}
		});
		/*btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRunning) {
					timerStop();
				}
				timerRun();
			}
		});*/
		btnNewButton.setBounds(211, 75, 109, 23);
		contentPane.add(btnNewButton);
		
		lblTimer = new JLabel("00H:00M:00S");
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTimer.setForeground(Color.WHITE);
		lblTimer.setBounds(10, 75, 191, 53);
		contentPane.add(lblTimer);
		
		btnAboutUser = new JButton("About " + user);
		btnAboutUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutUser gotoabout = new aboutUser(user);
				gotoabout.setVisible(true);
			}
		});
		btnAboutUser.setBounds(211, 105, 109, 23);
		contentPane.add(btnAboutUser);
		
		JButton btnScreenLock = new JButton("Lock Screen");
		btnScreenLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scrLock = new screenLockWhileSession(user);
				scrLock.setVisible(true);
			}
		});
		btnScreenLock.setBounds(211, 135, 109, 23);
		contentPane.add(btnScreenLock);
	}
	
	public void getUserBalance(String user) {
		System.out.println(user);
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Statement stmt = con.createStatement();
			 String query = "select balance from user_accounts where username = '" + user +"';";
			 ResultSet rs = stmt.executeQuery(query);
			 
			 while(rs.next()) {
				 System.out.println("timer connection: " + con);
				 int userBalance = rs.getInt(1);
				 timerRun(userBalance, user);
			 }
			
		} catch (Exception e) {}
	}
	
	public void timerRun(int balance, String user) {
		try {
			timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println(seconds);

					System.out.println(getBalanceTimer(user));
					
					//updates the timer to the database

					int secondAsync = Integer.parseInt(getBalanceTimer(user));
					
					if(secondAsync <= 0) {
						//pag wala nang balance mag oout siya bigla yes
						timerStop();
					} else {
						//get the current balance
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Statement stmt = con.createStatement();

							//counts the timer
							int toSec = 0, toMin = 0, toHour = 0;
							lblTimer.setText(tellTime(Integer.parseInt(getBalanceTimer(user)), toHour, toMin, toSec));
							
							//refreshes the time balance left forda query
							secondAsync--;
							
							//updates the refreshed balance
							String query = "update user_accounts set balance = " + secondAsync + " where username = '" + user + "';";
							stmt.executeUpdate(query);
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					

					int seconds = Integer.parseInt(getBalanceTimer(user));
					
					lowBalance timerDialog;
					Thread runTimerDialog;
					
					if (seconds == 599) {
						timerDialog = new lowBalance(10);
						runTimerDialog = new Thread(timerDialog);
						runTimerDialog.start();
					} 
					else if (seconds == 299) {
						timerDialog = new lowBalance(5);
						runTimerDialog = new Thread(timerDialog);
						runTimerDialog.start();
					}
					else if (seconds == 59) {
						timerDialog = new lowBalance(1);
						runTimerDialog = new Thread(timerDialog);
						runTimerDialog.start();
					}
					else {
						//none
					}
					

					/*//counts down the timer
					seconds--;
					//pag wala nang oras end na ng timer
					if(seconds < 0) {
						timerStop();
					} */
										 
				}
			});
			timer.start();
			isRunning = true;
		} catch(Exception e){System.out.println(e);}
		
	}
	
	public void timerStop() {
		timer.stop();
		isRunning = false;
		closeExplorer();
		mainScreenRestraint backToLock = new mainScreenRestraint();
		mainScreenRestraint.timerCountdown(300);
		if(scrLock != null) {
			scrLock.dispose();
			scrLock = null;
		}
		dispose();
		backToLock.setVisible(true);
	}
	
	public String tellTime(int t, int h, int m, int s) {
		for(int i = 0; i < t; i++) {
			s += 1;
			if(s == 60) {
				m += 1;
				s = 0;
				if(m == 60) {
					h += 1;
					m = 0;
				}
			}
		}
		
		return h + "H:" + m + "M:" + s + "S";
	}
	
	public String getBalanceTimer(String user) {
		String finalBalance = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//System.out.println(con);
			/*
			 * turns out that this function is ran three times in a row bc of updates
			 * so eto yung pinakaresource heavy part.
			 */
			
			Statement stmt = con.createStatement();
			String query = "select balance from user_accounts where username = '" + user + "'" + ";";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				finalBalance = rs.getString(1);
			}
			//con.close();
		} catch (Exception e1){
			//disconnection errors comes from here tandaan mo to janber utang na loob ang hirap hanapin nito
			//ang error dito is pag nadisconnect yung mga client pc sa server.
			dispose();
			timerStop();
		}
		
		return finalBalance;
	}
	
	public static void closeExplorer() {
		try {
			rtm.exec("taskkill /F /IM explorer.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class lowBalance implements Runnable{
		int toCheck;
		lowBalance(int toCheck){
			this.toCheck = toCheck;
		}
		
		public void run() {
			toFront();
			setAlwaysOnTop(true);
			setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(null,"You only have under " + toCheck + " minute(s) left.", "Balance Notice.", JOptionPane.WARNING_MESSAGE);
		}
	}
}
