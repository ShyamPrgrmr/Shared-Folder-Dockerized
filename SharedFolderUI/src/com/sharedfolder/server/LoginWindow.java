package com.sharedfolder.server;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;


public class LoginWindow extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private static LoginWindow frame = new LoginWindow();
	private JTextField Server;
	
	@Override
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow framestart = new LoginWindow();
					framestart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void start() {
		run();
	}
	
	public LoginWindow() {
		setResizable(false);
		setTitle("Login Manager");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(12, 106, 92, 36);
		contentPane.add(lblNewLabel);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		username.setBounds(123, 105, 297, 38);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(12, 156, 92, 36);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		password.setForeground(Color.RED);
		password.setColumns(10);
		password.setBounds(123, 155, 297, 38);
		contentPane.add(password);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = username.getText();
				String pass = password.getText();
				
				String serverPath = Server.getText();
				if(serverPath.charAt(serverPath.length()-1)=='/') {
					serverPath = serverPath.substring(0, serverPath.length()-1);
				}
				
				LoginResponse result = new RequestController(serverPath).loginRequest(user, pass);
				
				if(result.isLogin()) {
					
					if(result.getType().equals("1")) {
						new FirstWindow(result.username,serverPath).setVisible(true);
						frame.dispose();
					}
					else {
						new com.sharedfolder.client.FirstWindow(result.username,serverPath).setVisible(true);
						frame.dispose();
					}
				}
				else {
					System.out.println("Login failed");
				}
			}
		});
		btnNewButton.setBounds(150, 206, 132, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Login Window");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setBounds(129, 13, 186, 27);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblServerAddress = new JLabel("Server");
		lblServerAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblServerAddress.setBounds(12, 57, 92, 36);
		contentPane.add(lblServerAddress);
		
		Server = new JTextField();
		Server.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Server.setColumns(10);
		Server.setBounds(123, 54, 297, 38);
		contentPane.add(Server);
	}

	
}
