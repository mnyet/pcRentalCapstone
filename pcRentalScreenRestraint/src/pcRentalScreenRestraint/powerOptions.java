package pcRentalScreenRestraint;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class powerOptions extends JFrame {

	private JPanel contentPane;
	private static Runtime rtm = Runtime.getRuntime();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					powerOptions frame = new powerOptions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public powerOptions() {
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 247, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnShutdown = new JButton("Shutdown");
		btnShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					rtm.exec("shutdown -s -t 5");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnShutdown.setBounds(10, 11, 100, 100);
		contentPane.add(btnShutdown);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rtm.exec("shutdown -r -t 5");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRestart.setBounds(120, 11, 100, 100);
		contentPane.add(btnRestart);
	}

}
