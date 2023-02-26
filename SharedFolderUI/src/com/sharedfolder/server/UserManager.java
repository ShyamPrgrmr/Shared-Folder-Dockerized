package com.sharedfolder.server;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
import java.beans.PropertyChangeEvent;

public class UserManager extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	String serverPath;

	public UserManager(String serverPath) {
		this.serverPath = serverPath;
		setResizable(false);
		setTitle("User Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Add User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserData usee = new UserData();
				UserUpdateDialog uud=null;
				uud = new UserUpdateDialog(usee,false);
				uud.password.setVisible(true);
				uud.lblNewLabel_5_1.setVisible(true);
				uud.username.setEditable(true);
				uud.username.setVisible(true);
				uud.setVisible(true);
				
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Update User");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				DefaultTableModel tm = (DefaultTableModel) table.getModel();
				
				String username = tm.getValueAt(row, 0).toString();
				String name = tm.getValueAt(row, 1).toString();
				String mobile = tm.getValueAt(row, 2).toString();
				String gender = tm.getValueAt(row, 3).toString();
				String email = tm.getValueAt(row, 4).toString();
				String temp = tm.getValueAt(row, 5).toString();
				int type=0;
				if(temp.equals("Admin")) type=1;
				
				UserData usee = new UserData(username,
											name,
											email,
											mobile,
											gender,
											type
											);
				
				UserUpdateDialog uud=null;
				uud = new UserUpdateDialog(usee,true);
				uud.password.setVisible(false);
				uud.lblNewLabel_5_1.setVisible(false);
				uud.setVisible(true);	
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete User");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				TableModel tm = table.getModel();
				String username = tm.getValueAt(row, 0).toString();
				
				int m = JOptionPane.showConfirmDialog(contentPane, "Do you really want to delete : "+username+"?");
				if(m==0) {
					if(new RequestController(serverPath).deleteUser(username))
						table.setModel(new RequestController(serverPath).getTableData());
				}
			}
		});
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new RequestController(serverPath).getTableData());
	}


public class UserUpdateDialog extends JDialog {

	public UserData userdata;
	private final JPanel contentPanel = new JPanel();
	private JTextField username;
	private JTextField name;
	private JTextField email;
	private JTextField mobile;
	private JComboBox  type,gender;
	private JTextField password;
	JLabel lblNewLabel_5_1;
	boolean forUpdate;

	public UserData getData() {
		userdata.setUsername(username.getText());
		userdata.setGender(gender.getSelectedItem().toString());
		userdata.setName(name.getText());
		userdata.setMobile(mobile.getText());
		userdata.setEmail(email.getText());
		int temp = 1;
		if(type.getSelectedItem().toString().equals("User")) temp = 0;
		userdata.setType(temp);
		return userdata;
	}
	
	
	public UserUpdateDialog(UserData user,boolean forUpdate){
		this.forUpdate = forUpdate;
		userdata = new UserData();
		setResizable(false);
		setTitle("Update User Dialog");
		setBounds(100, 100, 533, 288);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(12, 15, 58, 16);
		contentPanel.add(lblNewLabel);
		username = new JTextField();
		username.setBounds(77, 12, 426, 22);
		username.setEditable(false);
		contentPanel.add(username);
		username.setColumns(10);
		username.setText(user.getUsername());
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(37, 44, 33, 16);
		contentPanel.add(lblNewLabel_1);
		name = new JTextField();
		name.setBounds(77, 41, 426, 22);
		contentPanel.add(name);
		name.setColumns(10);
		name.setText(user.getName());
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(39, 73, 31, 16);
		contentPanel.add(lblNewLabel_2);
		email = new JTextField();
		email.setBounds(77, 70, 426, 22);
		contentPanel.add(email);
		email.setColumns(10);
		email.setText(user.getEmail());
		JLabel lblNewLabel_3 = new JLabel("Mobile");
		lblNewLabel_3.setBounds(33, 102, 37, 16);
		contentPanel.add(lblNewLabel_3);
		mobile = new JTextField();
		mobile.setBounds(77, 99, 426, 22);
		contentPanel.add(mobile);
		mobile.setColumns(10);
		mobile.setText(user.getMobile());
		JLabel lblNewLabel_4 = new JLabel("gender");
		lblNewLabel_4.setBounds(30, 131, 40, 16);
		contentPanel.add(lblNewLabel_4);
		gender = new JComboBox();
		gender.setBounds(77, 128, 426, 22);
		contentPanel.add(gender);
		gender.addItem("male");
		gender.addItem("female");
		gender.setSelectedItem(user.getGender());
		JLabel lblNewLabel_5 = new JLabel("User Type");
		lblNewLabel_5.setBounds(12, 160, 58, 16);
		contentPanel.add(lblNewLabel_5);
		type = new JComboBox();
		type.setBounds(77, 157, 426, 22);
		contentPanel.add(type);
		type.addItem("Admin");
		type.addItem("User");
		int ty = user.getType();
		String t = "Admin";
		if(ty==0) t= "User";
		
		password = new JTextField();
		password.setText((String) null);
		password.setColumns(10);
		password.setBounds(77, 183, 426, 22);
		contentPanel.add(password);
		
		lblNewLabel_5_1 = new JLabel("Password");
		lblNewLabel_5_1.setBounds(12, 186, 58, 16);
		contentPanel.add(lblNewLabel_5_1);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("SAVE");
		
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(forUpdate) {
					UserData data = getData();
					DefaultTableModel dtemp = new RequestController(serverPath).updateUser(data, (DefaultTableModel) table.getModel());
					table.setModel(dtemp);
					dispose();
				}else {
					String uname = username.getText();
					String tname = name.getText();
					String tmobile = mobile.getText();
					String tgender = gender.getSelectedItem().toString();
					String ttype = type.getSelectedItem().toString();
					int temptype = 1;
					if(ttype.equals("User")) temptype = 0;
					String pass = password.getText();
					String em = email.getText();
					
					AddUserRequest aur = new AddUserRequest();
					aur.setUsername(uname);
					aur.setName(tname);
					aur.setGender(tgender);
					aur.setPassword(pass);
					aur.setEmail(em);
					aur.setType(temptype);
					aur.setMobile(tmobile);
					
					DefaultTableModel dtemp = new RequestController(serverPath).addUser(aur);
					table.setModel(dtemp);
					dispose();
					
				}
			}
		});
		getRootPane().setDefaultButton(okButton);
		
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);	
	}
	
}

}
