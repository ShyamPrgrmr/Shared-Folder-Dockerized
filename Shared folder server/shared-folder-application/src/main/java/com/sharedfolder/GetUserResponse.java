package com.sharedfolder;

public class GetUserResponse {
	int status;
	UserTableData data[];
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserTableData[] getData() {
		return data;
	}
	public void setData(UserTableData[] data) {
		this.data = data;
	}
	
}
