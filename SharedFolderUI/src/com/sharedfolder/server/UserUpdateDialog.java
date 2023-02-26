package com.sharedfolder.server;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class UserUpdateDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField username;
	private JTextField name;
	private JTextField email;
	private JTextField mobile;
	private JComboBox  type,gender;
	private JTextField password;

	public UserUpdateDialog(UserData user) {
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
	gender.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent arg0) {
			System.out.print(arg0.getItem());
		}
	});
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
	
	JLabel lblNewLabel_5_1 = new JLabel("Password");
	lblNewLabel_5_1.setBounds(12, 186, 58, 16);
	contentPanel.add(lblNewLabel_5_1);
	
	password = new JTextField();
	password.setText((String) null);
	password.setColumns(10);
	password.setBounds(77, 183, 426, 22);
	contentPanel.add(password);
	int ty = user.getType();
	String t = "Admin";
	if(ty==0) t= "User";
	JPanel buttonPane = new JPanel();
	buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	getContentPane().add(buttonPane, BorderLayout.SOUTH);
	JButton okButton = new JButton("SAVE");
	okButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
		}
	});
	okButton.setActionCommand("OK");
	buttonPane.add(okButton);
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


