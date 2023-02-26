package com.sharedfolder.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;



import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class FirstWindow extends JFrame {

	private JPanel contentPane;
	private DataStorage dst;
	private JTree jtree;
	private String username;
	

	public FirstWindow(String username,String serverPath) {
		setTitle("Client Manager");
		
		this.username = username;
		
		dst = new DataStorage();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 669, 470);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("Add Folder");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameOfFolder = JOptionPane.showInputDialog(contentPane,"Name of the Folder?",null);
				dst.setNameOfFolder(nameOfFolder);
				DefaultTreeModel dtm = new RequestController(serverPath).addNewFolder(dst);
				jtree.setModel(dtm);
			}
		});
		
		JButton btnNewButton_1 = new JButton("Delete Folder");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dst.setNameOfFolder("to be delete");
				DefaultTreeModel dtm = new RequestController(serverPath).deleteFolder(dst);
				jtree.setModel(dtm);
			}
		});
		
		JButton btnNewButton_2 = new JButton("Log Out");
		btnNewButton_2.setBackground(Color.RED);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton_3 = new JButton("Add File");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameOfFolder = JOptionPane.showInputDialog(contentPane,"Name of the file?",null);
				dst.setNameOfFolder(nameOfFolder);
				DefaultTreeModel dtm = new RequestController(serverPath).addFolder(dst);
				jtree.setModel(dtm);
			}
		});
		
		JButton btnNewButton_4 = new JButton("Delete File");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dst.setNameOfFolder("to be delete");
				DefaultTreeModel dtm = new RequestController(serverPath).deleteFolder(dst);
				jtree.setModel(dtm);
			}
		});
		
		JButton btnNewButton_5 = new JButton("Download");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte[] data = new RequestController(serverPath).downloadFile(dst);
				File dir = new File("D://download");
				dir.mkdir();
				LocalDateTime now = LocalDateTime.now();
				File newFile = new File("D://download/"+now.hashCode()+dst.getNameOfFolder());
				try {
					if(newFile.createNewFile()) {
						OutputStream os = new FileOutputStream(newFile);  
						os.write(data); 
						int check = JOptionPane.showConfirmDialog(contentPane, 
								                      "File Downloaded at : "+newFile.getAbsolutePath() ,
													  "Open File", JOptionPane.YES_NO_OPTION);
						if(check!=1) {
							try  
							{     
								if(!Desktop.isDesktopSupported())  
								{  
									return;  
								}  
								Desktop desktop = Desktop.getDesktop();  
								if(newFile.exists())           
									desktop.open(newFile);               
							}  
							catch(Exception e)  
							{ e.printStackTrace();}
						}
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(contentPane, "Error!");
					e.printStackTrace();
				}	
			}
		});
		
		JButton btnNewButton_6 = new JButton("Upload");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jfc = new JFileChooser();
				int i = jfc.showOpenDialog(contentPane);
				if(i==JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					String nameOfFile = file.getName();
					String path[] = dst.getCurrentPath();
					DefaultTreeModel dtm = new RequestController(serverPath).uploadFile(nameOfFile, path, file);
					jtree.setModel(dtm);
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnNewButton_6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_6)
					.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
					.addComponent(btnNewButton_2)
					.addContainerGap())
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
		);
		
		DefaultTreeModel dtm = new RequestController(serverPath).getTree();
		jtree = new JTree(dtm);
		jtree.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		jtree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				TreePath[] tp = arg0.getPaths();
				TreePath tempTreePath = tp[0];
				Object[] pathObj = tempTreePath.getPath();
				String path[] = new String[pathObj.length];
				int index=0;
				for(Object p : pathObj) {
					path[index++] = p.toString();
				}			
				dst.setNameOfFolder(path[index-1]);
				dst.setCurrentPath(path);
			}
		});
		scrollPane.setViewportView(jtree);
		
		JLabel lblNewLabel = new JLabel("Welcome "+username);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scrollPane.setColumnHeaderView(lblNewLabel);
		panel.setLayout(gl_panel);
	}
}
