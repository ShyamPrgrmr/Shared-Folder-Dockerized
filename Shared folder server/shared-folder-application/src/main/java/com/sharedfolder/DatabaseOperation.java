package com.sharedfolder;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class DatabaseOperation {
	
	private Connection connection ;
	
	
	DatabaseOperation(DatabaseConnection dc){
		this.connection = dc.getConnection();
	}
	
	public boolean updateUser(UserTableData userdata) {
		Connection con = this.getConnection();
		String username = userdata.getUsername();
		String name = userdata.getName();
		String email = userdata.getEmail();
		String mobile = userdata.getMobile();
		String gender = userdata.getGender();
		int type = userdata.getUserType();
		
		try {
			PreparedStatement pst = con.prepareStatement("update userinfo set name=?,email=?,usertype=?,gender=?,mobile=? where username=?");
			pst.setString(1,name);
			pst.setString(2,email);
			pst.setInt(3,type);
			pst.setString(4,gender);
			pst.setString(5,mobile);
			pst.setString(6,username);
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public UserLogin getLogin(UserLogin user) throws SQLException {
		Connection con = this.getConnection();
		String username = user.getUsername();
		String password = user.getPassword();
		PreparedStatement st = con.prepareStatement("select * from user where username='"+username+"' and password='"+password+"'");	
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			user.type = rs.getString("type");
			return user;
		}
		
		st.close();
		rs.close();
		return new UserLogin();	
	}
	
	public UserTableData[] getUserInfo() {
		UserTableData userData[]=null;
		int index = 0;
		Connection con = this.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select count(username) as count from userinfo");
			ResultSet rs = pst.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			UserTableData temp[] = new UserTableData[count];
			for(int i=0;i<count;i++)
				temp[i] = new UserTableData();
			
			pst = con.prepareStatement("select * from userinfo");
			rs = pst.executeQuery();
			
			while(rs.next()) {
				temp[index].setUsername(rs.getString("username"));
				temp[index].setEmail(rs.getString("email"));
				temp[index].setMobile(rs.getString("mobile"));
				temp[index].setGender(rs.getString("gender"));
				temp[index].setUserType(rs.getInt("usertype"));
				temp[index].setName(rs.getString("name"));
				index++;
			}
			
			pst.close();
			rs.close();
		
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return userData;
	}
	
	public boolean addUser(AddUserRequest userdata) {
		Connection con = this.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("insert into user(username,password,type) values(?,?,?)");
			pst.setString(1, userdata.getUsername());
			pst.setString(2, userdata.getPassword());
			pst.setInt(3, userdata.getType());	
			pst.execute();
			pst = con.prepareStatement("insert into userinfo(username,name,usertype,mobile,gender,email) values(?,?,?,?,?,?)");
			pst.setString(1, userdata.getUsername());
			pst.setString(2, userdata.getName());
			pst.setInt(3, userdata.getType());
			pst.setString(4, userdata.getMobile());
			pst.setString(5, userdata.getGender());
			pst.setString(6, userdata.getEmail());
			pst.execute();
			pst.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteUser(String username) {
		try {
			Connection con = this.getConnection();
			PreparedStatement pst = con.prepareStatement("delete from user where username=?");
			pst.setString(1, username);
			pst.execute();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Connection getConnection(){
		return this.connection;
	}
	
	
}
