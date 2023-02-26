package com.sharedfolder.server;

public class UserData {
	int type;
	String username;
	String name;
	String email;
	String gender;
	String mobile;
	
	UserData(String username,
			String name,
			String email,
			String mobile,
			String gender,
			int type){
		
		this.username = username;
		this.name = name;
		this.email = email;
		this.gender= gender;
		this.type = type;
		this.mobile = mobile;
	}
	
	UserData(){
		
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
