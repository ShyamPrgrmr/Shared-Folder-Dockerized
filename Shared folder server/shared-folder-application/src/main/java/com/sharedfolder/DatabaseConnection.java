package com.sharedfolder;



import java.sql.*;

import org.springframework.stereotype.Component;

public class DatabaseConnection {
	
	private String databaseUrl;
	private String databaseUsername;
	private String databasePassword;
	 
	
	DatabaseConnection(String url,String username,String password){
		System.out.println("Connecting database at : "+ url);
		this.databaseUrl = url;
		this.databaseUsername=username;
		this.databasePassword=password;
	}
	
	
	public Connection getConnection(){
		
		Connection conn = null;
		try
		{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			conn=DriverManager.getConnection(this.databaseUrl,this.databaseUsername,this.databasePassword); 
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}  
		
		return conn;
	}
	
	public void closeConnection() throws SQLException {
		
	}
	
}
