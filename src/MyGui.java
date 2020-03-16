
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class MyGui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private Airport myAP;
	/**
	 * Launch the application.
	 * this program running airport system by gui  
	 * the user insert 2 parameters : number of technical crews and security work time
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGui frame = new MyGui();
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
	public MyGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numOfTechCrews = Integer.parseInt(textField.getText()); // given number of technical crews by user 
					Double securityWorkTime = Double.parseDouble(textField_1.getText()); // given work time of security by user
					Airport myAp=new Airport("FlightsData.txt",numOfTechCrews,securityWorkTime); //creating airport  
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null	, "please enter valid number"); //in case of wrong insertion
				}
			}
		});
		btnStart.setBounds(115, 158, 89, 23);
		contentPane.add(btnStart);
		
		JButton btnExit = new JButton("Exit");//exit button
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(273, 158, 89, 23);
		btnExit.addActionListener(new ActionListener() {	
			//exit button 
			public void actionPerformed(ActionEvent e) {
				System.exit(0);	
			}
		});
		contentPane.add(btnExit);
		
		textField = new JTextField(); //number of technical crews insertion field
		textField.setText("1");
		textField.setBounds(115, 70, 89, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(); //security work time insertion field 
		textField_1.setText("2");
		textField_1.setBounds(273, 70, 89, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("number of technical crews ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(75, 44, 165, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("work time for security");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(250, 48, 136, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Airport Management System");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(130, 11, 212, 26);
		contentPane.add(lblNewLabel_2);
	}
}
