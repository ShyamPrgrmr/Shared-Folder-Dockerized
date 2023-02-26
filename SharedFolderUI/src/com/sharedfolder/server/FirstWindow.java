package com.sharedfolder.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;

public class FirstWindow extends JFrame {
	private static JPanel contentPane;
	private String username;
	private TreeModel treeModel;
	private JTree tree_1;
	private JScrollPane scrollPane_1;
	
	public FirstWindow(String username,String serverPath) {
		setResizable(false);
		setTitle("Server Manager");
		this.username = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome,"+username);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(12, 12, 303, 20);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(455, 7, 84, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Users");
		btnNewButton_1.setBounds(12, 40, 117, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManager usermanager = new UserManager(serverPath);
				usermanager.setVisible(true);
			}
		});
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Report");
		btnNewButton_1_1.setBounds(141, 40, 117, 25);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File f = new File("D://shared/");
				String contents[] = f.list();
				FileData Folders[] = new FileData[contents.length];
				for(int i=0;i<contents.length;i++) {
					Folders[i] = new FileData();
				}
				
				int index=0;
				
				
				for(String filename : contents) {
					File tempFile = new File("D://shared/"+filename);
					
					BasicFileAttributes attrs;
					
					try {
					    attrs = Files.readAttributes(tempFile.toPath(), BasicFileAttributes.class);
					    FileTime creationTime = attrs.creationTime();
					    FileTime lastModifiedTime = attrs.lastModifiedTime();
					    
					    String pattern = "yyyy-MM-dd HH:mm:ss";
					    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);	
					    
					    String createdTime = simpleDateFormat.format( new Date(creationTime.toMillis()));
					    String lastModified = simpleDateFormat.format( new Date(lastModifiedTime.toMillis()));
					    
					    Folders[index].creationTime = createdTime;
					    Folders[index].lastmodified = lastModified;
					    Folders[index].path = "file://"+tempFile.getCanonicalPath();
					    Folders[index].numberOfFile = tempFile.list().length;
					    Folders[index].name = filename;
					    Folders[index].size = attrs.size();
					    Folders[index].isDirectory = attrs.isDirectory();
					    
					    //store here
					    
					    index++;
					} catch (Exception e1) {
					  e1.printStackTrace();
					}
				}
				
				exportToExcel(Folders);
				
			}
		});
		contentPane.add(btnNewButton_1_1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 68, 527, 223);
		contentPane.add(scrollPane_1);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Shared Folders");
        addChilds(root, "D://shared"); //server side knows shared folder contains.
        treeModel = new DefaultTreeModel(root);
		
		tree_1 = new JTree(treeModel);
		scrollPane_1.setViewportView(tree_1);
	}
	
	private File[] getListFiles(String Path) {
        File file = new File(Path);
        return file.listFiles();
    }
	
	
public static void exportToExcel(FileData[] filedata) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet spreadsheet = workbook.createSheet( " Folder Info ");
		Row row;
		row = spreadsheet.createRow(0);
		row.createCell(0).setCellValue("Name");
		row.createCell(1).setCellValue("Number of files");
		row.createCell(2).setCellValue("Creation time");
		row.createCell(3).setCellValue("Last Modified Time");
		row.createCell(4).setCellValue("isDirectory");
		row.createCell(5).setCellValue("size");
		row.createCell(6).setCellValue("path");
		
		
		try {
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());  
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
			int rownumber = 2;
			
			for(FileData files : filedata) {
				row = spreadsheet.createRow(rownumber++);
				if(files.numberOfFile >= 3) {
					
					Cell cell;
					cell = row.createCell(0);
					cell.setCellValue(files.name);
				    cell.setCellStyle(style);
					
					cell = row.createCell(1);
					cell.setCellValue(files.numberOfFile+"");
				    cell.setCellStyle(style);
					
				    
					cell = row.createCell(2);
					cell.setCellValue(files.creationTime);
				    cell.setCellStyle(style);
					
					cell = row.createCell(3);
					cell.setCellValue(files.lastmodified);
				    cell.setCellStyle(style);
					
					cell = row.createCell(4);
					cell.setCellValue(files.isDirectory+"");
				    cell.setCellStyle(style);
					
					cell = row.createCell(5);
					cell.setCellValue(files.size+"");
				    cell.setCellStyle(style);
					
					cell = row.createCell(6);
					cell.setCellValue(files.path);
				    cell.setCellStyle(style);
					
				}
				else {
					row.createCell(0).setCellValue(files.name);
					row.createCell(1).setCellValue(files.numberOfFile+"");
					row.createCell(2).setCellValue(new Date(files.creationTime).toString());
					row.createCell(3).setCellValue(new Date(files.lastmodified).toString());
					row.createCell(4).setCellValue(files.isDirectory+"");
					row.createCell(5).setCellValue(files.size+"");
					row.createCell(6).setCellValue(files.path);
				}
				
			}
			
			Random random  = new Random(1000);
			File f= new File("D://excelfile");
			f.mkdir();
			String filename = "D://excelfile/ExportedExcelFile"+random.nextInt()+".xls";
			f= new File(filename);
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f);
			workbook.write(out);
		    out.close();  
		    
		    int check = JOptionPane.showConfirmDialog(contentPane, 
                    "File Exported at : "+f.getAbsolutePath() ,
					  "Open File", JOptionPane.YES_NO_OPTION);
			if(check!=1) {
			try  
			{     
				if(!Desktop.isDesktopSupported())  
				{  
					return;  
				}  
				Desktop desktop = Desktop.getDesktop();  
				if(f.exists())           
					desktop.open(f);               
				}  
			catch(Exception e)  
			{ e.printStackTrace();}
			}
		    
		    
		} catch (Exception e) {
			System.out.println(e);
		} 
		
	}
	
	

	private class FileData{
		
		String name;
		int numberOfFile;
		String creationTime;
		String lastmodified;
		boolean isDirectory;
		long size;
		String path;
		
		FileData(){
			this.name="";
			this.numberOfFile=0;
			this.creationTime="";
			this.lastmodified="";
			this.size=0;
			this.path="";
		}
	}
	
	private void addChilds(DefaultMutableTreeNode rootNode, String path) {
        File[] files = this.getListFiles(path);
        for(File file:files) {
            if(file.isDirectory()) {
                DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getName());
                addChilds(subDirectory, file.getAbsolutePath());
                rootNode.add(subDirectory);
            } else {
                rootNode.add(new DefaultMutableTreeNode(file.getName()));
            }
        }
    }
}
